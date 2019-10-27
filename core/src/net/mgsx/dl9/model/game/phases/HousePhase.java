package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.mobs.MobWindow;
import net.mgsx.gltf.data.scene.GLTFNode;

public class HousePhase extends BaseActionPhase {


	private static class WindowSpawn {
		public Node left, right;
		private float time;
		private boolean closed = false;
		private boolean opening = false;
		private boolean closing = false;
		public GameMob mob;
		public final Vector3 spawnPos = new Vector3();
		
		public void open(){
			closing = false;
			opening = true;
			time = 0;
			if(MathUtils.randomBoolean())
				GameAssets.i.sfxWindowOpen2.play();
			else
				GameAssets.i.sfxWindowOpen1.play();
		}
		public void close(){
			closing = true;
			opening = false;
			time = 0;
			if(MathUtils.randomBoolean())
				GameAssets.i.sfxWindowClose2.play();
			else
				GameAssets.i.sfxWindowClose1.play();
		}
		public void update(float delta){
			
			if(opening){
				time += delta;
				if(time > 1){
					time = 0;
					setOpened();
					return;
				}
			}else if(closing){
				time += delta;
				if(time > 1){
					time = 0;
					setClosed();
					return;
				}
			}else{
				return;
			}
			
			
			
			
			float ctrl = time;
			
			if(closing){
				ctrl = Interpolation.bounceOut.apply(ctrl);
			}
			else{
				ctrl = Interpolation.bounceIn.apply(1 - ctrl);
			}
			
			set(ctrl);
			
//			left.rotation.setFromAxis(Vector3.Y, MathUtils.lerp(180, 360, ctrl) );
//			right.rotation.setFromAxis(Vector3.Y, MathUtils.lerp(0, 180, 1.f - ctrl) ).mul(new Quaternion().set(Vector3.X, 180));
		}
		public void toggle() {
			if(closed) open(); else close();
			
		}
		public void setClosed() {
			set(1);
			opening = closing = false;
			closed = true;
		}
		public void setOpened() {
			set(0);
			opening = closing = false;
			closed = false;
		}

		private void set(float ctrl){
			left.rotation.setFromAxis(Vector3.Y, MathUtils.lerp(180, 360, ctrl) );
			right.rotation.setFromAxis(Vector3.Y, MathUtils.lerp(0, 180, 1.f - ctrl) ).mul(new Quaternion().set(Vector3.X, 180));
		}
	}
	
	private final Array<WindowSpawn> windows = new Array<WindowSpawn>();

	private float time;

	private int nbMobSpawn;

	private boolean allClosed;
	
	public HousePhase(GameLevel level) {
		super(level);
		
		for(GLTFNode gnode : level.asset.data.nodes){
			if(gnode.extras != null){
				String type = gnode.extras.value.getString("type", null);
				if(type != null){
					if("window spawn".equals(type)){
						WindowSpawn ws = new WindowSpawn();
						Node base = level.scene.modelInstance.getNode(gnode.name, true);
						base.globalTransform.getTranslation(ws.spawnPos);
						
						// TODO use prop : type left or right
						// TODO utils : get child with extra name, value
						ws.left = base.getChild(0);
						ws.right = base.getChild(1);
						windows.add(ws);
						ws.setClosed();
					}
				}
			}
		}
	}
	
	@Override
	public boolean isFinished(float time) {
		return isEnding() && allClosed;
	}
	
	private boolean isEnding(){
		return nbMobSpawn >= GameConfig.HOUSE_SPAWNS && !level.mobManager.hasMobs();
	}
	
	@Override
	public void finished() {
		super.finished();
		// cleanup 
		for(WindowSpawn ws : windows){
			if(ws.mob != null){
				level.mobManager.removeMob(ws.mob);
				ws.mob = null;
			}
		}
	}
	
	@Override
	public void update(float utime, float delta) {
		this.time += delta;
		
		if(this.time > MathUtils.random(.1f, 1f) * 5 && nbMobSpawn < GameConfig.HOUSE_SPAWNS){
			this.time = 0;
			WindowSpawn ws = windows.random();
			
			if(!ws.opening && !ws.closing){
				if(ws.closed && ws.mob == null){
					// add a mob TODO or not
					if(MathUtils.randomBoolean(.8f)){
						
						ws.mob = level.mobManager.spawnMobAt(ws.spawnPos);
						ws.mob.logic = new MobWindow();
						
						nbMobSpawn++;
					}
				}
				ws.toggle();
			}else{
				// TODO ?
			}
		}
		
		if(isEnding() && this.time > .1f){
			this.time = 0;
			allClosed = true;
			for(WindowSpawn ws : windows){
				if(!ws.closed){
					allClosed = false;
				}
				if(!ws.closed && !ws.closing && !ws.opening){
					ws.close();
					break;
				}
			}
		}
		
		for(WindowSpawn ws : windows){
			if(ws.mob != null && ws.closed && !ws.closing && !ws.opening){
				level.mobManager.removeMob(ws.mob);
				ws.mob = null;
			}
			ws.update(delta);
		}
		
		level.scene.modelInstance.calculateTransforms();
	}

}
