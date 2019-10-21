package net.mgsx.dl9.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.core.QueueSequence;
import net.mgsx.dl9.core.SceneSequencer;
import net.mgsx.dl9.core.collisions.ScreenRay;
import net.mgsx.dl9.core.collisions.ScreenRay.ScreenRayResult;
import net.mgsx.dl9.model.game.camera.CameraAnimator;
import net.mgsx.dl9.model.game.phases.BossIntroPhase;
import net.mgsx.dl9.model.game.phases.BossPhase;
import net.mgsx.dl9.model.game.phases.ChurchPhase;
import net.mgsx.dl9.model.game.phases.HousePhase;
import net.mgsx.dl9.model.game.phases.MainStreetPhase;
import net.mgsx.dl9.model.game.phases.PlacePhase;
import net.mgsx.dl9.model.game.phases.ToBossPhase;
import net.mgsx.dl9.model.game.phases.ToChurchPhase;
import net.mgsx.dl9.model.game.phases.ToHousePhase;
import net.mgsx.dl9.model.game.phases.ToMainStreetPhase;
import net.mgsx.dl9.model.game.phases.ToPlacePhase;
import net.mgsx.dl9.model.game.phases.ToTutoPhase;
import net.mgsx.dl9.model.game.phases.TutoEndPhase;
import net.mgsx.dl9.model.game.phases.TutoPhase;
import net.mgsx.dl9.model.game.phases.WinPhase;
import net.mgsx.dl9.vfx.CameraTrauma;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GameLevel {
	public final Array<GameListener> listeners = new Array<GameListener>();
	
	private ScreenRay screenRay;
	
	public SceneAsset asset;
	public final Array<MobEmitter> emitters = new Array<MobEmitter>();
	public final Array<GameMob> mobs = new Array<GameMob>();
	public Node mobNode;
	public Scene scene;
	
	/** real camera used for rendering (can be modified during animations) */
	public Camera camera;
	
	/** camera from blender (animated, can't be modified) */
	public Camera nativeCamera;
	public Node nativeCameraNode;
	
	public CameraAnimator cameraAnimator;
	
	public int bullets;
	
	public int heroLife = GameConfig.HERO_LIFE_MAX;
	
	public final InputLogic input;
	public boolean actionPhase = false;
	
	private final SceneSequencer sequencer = new SceneSequencer();

	private QueueSequence queue;

	public final MobsManager mobManager;
	
	private CameraTrauma trauma = new CameraTrauma();

	public GameLevel() {
		input = new InputLogic(this);
		mobManager = new MobsManager(this);
		screenRay = new ScreenRay(true, 4); // TODO max bones ?
	}
	
	public void start(){
		
		sequencer.clear();
		mobs.clear();
		
		// XXX 
		if(GameConfig.DEBUG_NATIVE_CAMERA){
			scene.animations.loopAll();
			return;
		}
		
		// build main sequences
		queue = new QueueSequence();
		
		// TODO queue.add(new IntroPhase(this));
		// TODO queue.add(new MenuPhase(this));
		queue.add(new ToTutoPhase(this));
		queue.add(new TutoPhase(this));
		queue.add(new TutoEndPhase(this));
		queue.add(new ToMainStreetPhase(this));
		queue.add(new MainStreetPhase(this));
		queue.add(new ToHousePhase(this));
		queue.add(new HousePhase(this));
		queue.add(new ToPlacePhase(this));
		queue.add(new PlacePhase(this));
		queue.add(new ToChurchPhase(this));
		queue.add(new ChurchPhase(this));
		queue.add(new ToBossPhase(this));
		queue.add(new BossIntroPhase(this));
		queue.add(new BossPhase(this));
		queue.add(new WinPhase(this));
		
		sequencer.add(queue);
	}
	
	public void setActionPhase() {
		actionPhase = true;
		for(GameListener l : listeners){
			l.onActionPhase();
		}
	}
	
	public void setCinematicPhase() {
		actionPhase = false;
		for(GameListener l : listeners){
			l.onCinematicPhase();
		}
	}

	public void update(float delta){
		
		if(GameConfig.DEBUG){
			if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
				queue.next();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
				start();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
				setDead();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
				setWin();
			}
		}
		
		sequencer.update(delta);
		
		input.update(delta);
		
		updateCamera(delta);
		
		mobManager.update(delta);
		
		for(GameMob mob : mobs){
			mob.update(this, delta);
		}
		
		// XXX required for windows when camera is not animated
		scene.modelInstance.calculateTransforms();
	}
	
	private void updateCamera(float delta) {
		
		if(cameraAnimator != null) cameraAnimator.update(delta);
		
		trauma.update(camera, delta);
		
		camera.update();
	}

	// TODO x/y not needed
	void shootAt(int x, int y) {
		if(bullets > 0){
			bullets--;
			for(GameListener l : listeners){
				l.onBulletChanged();
			}
			checkCollision();
		}
	}

	void reload() {
		bullets = GameConfig.MAX_BULLETS;
		for(GameListener l : listeners){
			l.onBulletChanged();
		}
	}
	
	private void checkCollision(){
		
		for(int i=0 ; i<mobs.size ; i++){
			GameMob mob = mobs.get(i);
			screenRay.set(i + 1, mob.node);
		}
		
		screenRay.begin(false);
		screenRay.renderBegin(camera, true);
		screenRay.render(scene);
		screenRay.renderEnd();
		ScreenRayResult r = screenRay.getPick(camera);
		screenRay.end(false);
		
		int mobID = r.id-1;
		if(mobID >= 0 && mobID < mobs.size){
			GameMob mob = mobs.get(mobID);
			mobShooted(mob, r.depth);
		}else{
			if(mobID < 0){
				bgFarShooted();
			}else{
				bgShooted(r.depth);
			}
		}
	}

	private void bgFarShooted() {
		// TODO ?
	}

	private void bgShooted(float depth) {
		// TODO ?
	}

	private void mobShooted(GameMob mob, float depth) {
		// TODO anim and such
		mob.shooted = true;
		mobManager.removeMob(mob);
		
		GameAssets.i.soundSetMobShooted.sounds.get(1).play();
	}

	public void mobShoot(GameMob mob) {
		// TODO ensure one shoot at a time
		Vector3 p = new Vector3(mob.position).add(0, .5f, 0); // TODO eye position
		
		trauma.traumatize();
		
		GameAssets.i.soundSetLaser.sounds.get(1).play();
		
		heroLife--;
		
		for(GameListener l : listeners){
			l.onMobShoot(p);
		}
		
		if(heroLife <= 0){
			setDead();
		}
	}
	
	private void setDead() {
		heroLife = 0;
		sequencer.clear();
		setCinematicPhase();
		for(GameListener l : listeners){
			l.onHeroDead();
		}
	}
	
	private void setWin() {
		sequencer.clear();
		setCinematicPhase();
		for(GameListener l : listeners){
			l.onHeroWin();
		}
	}

	public boolean isGameover(){
		return sequencer.isEmpty();
	}
	
}
