package net.mgsx.dl9.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.core.QueueSequence;
import net.mgsx.dl9.core.SceneSequencer;
import net.mgsx.dl9.core.collisions.ScreenRay;
import net.mgsx.dl9.core.collisions.ScreenRay.ScreenRayResult;
import net.mgsx.dl9.model.game.camera.CameraAnimator;
import net.mgsx.dl9.model.game.phases.Boss1Phase;
import net.mgsx.dl9.model.game.phases.Boss2Phase;
import net.mgsx.dl9.model.game.phases.BossIntroPhase;
import net.mgsx.dl9.model.game.phases.BossMidPhase;
import net.mgsx.dl9.model.game.phases.ChurchPhase;
import net.mgsx.dl9.model.game.phases.HousePhase;
import net.mgsx.dl9.model.game.phases.IntroPhase;
import net.mgsx.dl9.model.game.phases.MainStreetActionPhase;
import net.mgsx.dl9.model.game.phases.MainStreetPhase;
import net.mgsx.dl9.model.game.phases.MainStreetSurprisePhase;
import net.mgsx.dl9.model.game.phases.MenuPhase;
import net.mgsx.dl9.model.game.phases.PlacePhase;
import net.mgsx.dl9.model.game.phases.PreIntroPhase;
import net.mgsx.dl9.model.game.phases.ToBossPhase;
import net.mgsx.dl9.model.game.phases.ToChurchPhase;
import net.mgsx.dl9.model.game.phases.ToHousePhase;
import net.mgsx.dl9.model.game.phases.ToMainStreetPhase;
import net.mgsx.dl9.model.game.phases.ToPlacePhase;
import net.mgsx.dl9.model.game.phases.ToTutoPhase;
import net.mgsx.dl9.model.game.phases.TutoEndPhase;
import net.mgsx.dl9.model.game.phases.TutoPhase;
import net.mgsx.dl9.model.game.phases.WinPhase;
import net.mgsx.dl9.utils.CustomAnimationsPlayer;
import net.mgsx.dl9.utils.LogUtils;
import net.mgsx.dl9.utils.NodeUtils;
import net.mgsx.dl9.utils.Perf;
import net.mgsx.dl9.vfx.CameraTrauma;
import net.mgsx.gltf.scene3d.model.WeightVector;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GameLevel implements Disposable {
	public final Array<GameListener> listeners = new Array<GameListener>();
	
	private ScreenRay screenRay;
	
	public SceneAsset asset;
	public final Array<MobEmitter> emitters = new Array<MobEmitter>();
	public final Array<GameMob> mobs = new Array<GameMob>();
	public Node mobNode;
	public Scene scene;
	public Scene witchScene;
	public CustomAnimationsPlayer animations;
	
	public Stage stage;
	
	/** real camera used for rendering (can be modified during animations) */
	public Camera camera;
	
	/** camera from blender (animated, can't be modified) */
	public Camera nativeCamera;
	public Node nativeCameraNode;
	
	public CameraAnimator cameraAnimator;
	
	public int bullets;
	
	public int heroLife;
	
	public final InputLogic input;
	public boolean actionPhase = false;
	
	private final SceneSequencer sequencer = new SceneSequencer();

	private QueueSequence queue;

	public final MobsManager mobManager;
	
	private CameraTrauma trauma = new CameraTrauma();

	private boolean firstSequence = true;
	
	public boolean atmoSFXEnabled = false;
	
	public float globalLight = 1;
	
	public float globalLightTarget = 1;

	public Node witchNode;

	public Node witchBalaiNode;

	public WitchCommon witchCommon;

	public float globalLightFX = 0;

	public WeightVector witchShapeKeys;

	public int heroLifeMax;

	public int nbBulletUsed;

	public int nbZombiKilled;

	public int nbLazerGet;
	
	public final ObjectMap<String, PointLight> lights = new ObjectMap<String, PointLight>();
	
	public Environment env;
	
	public GameLevel() {
		input = new InputLogic(this);
		mobManager = new MobsManager(this);
		screenRay = new ScreenRay(false, GameConfig.MAX_BONES);
	}
	
	public boolean isFirstSequence() {
		return firstSequence;
	}
	
	public void start(){
		
		// disable all nodes
		if(GameConfig.MANUAL_MESH_CULLING){
			NodeUtils.enable(scene.modelInstance.nodes, false);
		}
		
		if(GameConfig.MANUAL_LIGHT_CULLING){
			PointLightsAttribute pla = env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
			if(pla != null){
				for(Entry<String, PointLight> e : lights){
					env.remove(e.value);
				}
			}
			// XXX blue back light is not really necessary
			// env.add(lights.get("light.camera.blue"));
			env.add(lights.get("light.camera.orange"));
		}
		
		
		sequencer.clear();
		mobs.clear();
		
		// XXX 
		if(GameConfig.DEBUG_NATIVE_CAMERA){
			scene.animations.loopAll();
			return;
		}
		
		// build main sequences
		queue = new QueueSequence();
		
		if(!GameConfig.DEBUG_BOSS){
			queue.add(new PreIntroPhase(this));
			queue.add(new IntroPhase(this));
			queue.add(new MenuPhase(this));
			
			queue.add(new ToTutoPhase(this));
			queue.add(new TutoPhase(this));
			queue.add(new TutoEndPhase(this));
			
			queue.add(new ToMainStreetPhase(this));
			queue.add(new MainStreetPhase(this));
			queue.add(new MainStreetSurprisePhase(this));
			queue.add(new MainStreetActionPhase(this));
			
			queue.add(new ToHousePhase(this));
			queue.add(new HousePhase(this));
			
			queue.add(new ToPlacePhase(this));
			queue.add(new PlacePhase(this));
			queue.add(new ToChurchPhase(this));
		}
		queue.add(new ChurchPhase(this));
		
		
		queue.add(new ToBossPhase(this));
		queue.add(new BossIntroPhase(this));
		queue.add(new Boss1Phase(this));
		queue.add(new BossMidPhase(this));
		queue.add(new Boss2Phase(this));
		
		queue.add(new WinPhase(this));
		
		sequencer.add(queue);
	}
	
	public void initStats() {
		int difficulty = DL9Game.i().settings.difficulty.value;
		int life;
		if(difficulty == 0){
			life = 30;
		}else if(difficulty == 1){
			life = 10;
		}else{
			life = 3;
		}
		
		heroLifeMax = heroLife = life;
		
	}
	
	@Override
	public void dispose() {
		screenRay.dispose();
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
	
	public void next() {
		firstSequence = false;
		if(queue != null){
			queue.next();
		}
	}

	public void update(float delta){
		
		if(GameConfig.DEBUG){
			if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
				next();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
				setDead();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
				setWin();
			}
		}
		
		globalLight = MathUtils.lerp(globalLight, globalLightTarget, delta);
		
		// audio
		if(atmoSFXEnabled){
			GameAudio.i.playerRandomThings.update(delta);
			
			// TODO hearth beat based on life
			GameAudio.i.playerHeartBeat.volume = heroLifeMax <= 0 ? 0 : 1f - (float)heroLife / (float)heroLifeMax;
			GameAudio.i.playerHeartBeat.pwm(delta, 1.2f, .66f);
			
		}

		
		sequencer.update(delta);
		
		if(GameConfig.LOGGING && LogUtils.isTimeToLog()){
			LogUtils.logNodes(scene);
			LogUtils.logLights(env, lights.size);
		}
		
		input.update(delta);
		
		updateCamera(delta);
		
		mobManager.update(delta);
		
		for(GameMob mob : mobs){
			mob.update(this, delta);
		}
		
		animations.update(delta);
		
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
			
			nbBulletUsed++;
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
		
		int perf = Perf.start("check collosion");
		screenRay.begin(false);
		screenRay.renderBegin(camera, true);
		screenRay.render(scene);
		screenRay.render(witchScene);
		screenRay.renderEnd();
		ScreenRayResult r = screenRay.getPick(camera);
		screenRay.end(false);
		Perf.flush(perf);
		
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
		mob.logic.onShooted(this, mob);
		if(mob.dead){
			mobManager.removeMob(mob);
			nbZombiKilled++;
		}
	}

	public void mobShoot(GameMob mob) {
		// TODO ensure one shoot at a time
		Vector3 p = new Vector3(mob.position).add(0, .5f, 0); // TODO eye position
		
		mob.logic.shooting(this, mob);
		
		nbLazerGet++;
		
		trauma.traumatize();
		
		if(heroLife > 0){
			heroLife--;
			if(GameConfig.DEBUG_INVINCIBLE){
				heroLife = Math.max(1, heroLife);
			}
		}
		
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
	
	public void setWin() {
		sequencer.clear();
		setCinematicPhase();
		for(GameListener l : listeners){
			l.onHeroWin();
		}
	}

	public boolean isGameover(){
		return sequencer.isEmpty();
	}

	public void triggerMenu() {
		for(GameListener l : listeners){
			l.onMenuShow();
		}
	}

}
