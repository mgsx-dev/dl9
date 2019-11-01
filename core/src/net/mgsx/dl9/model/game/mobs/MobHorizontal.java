package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobHorizontal extends MobBase
{
	public static final float DELTA_POS = 5; // TODO enough
	
	public float shootWait = 3f; // TODO config per enemy
	private float shootTimeout;
	private float t = 0;
	
	private boolean goBack = false;
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		if(goBack){
			t -= delta * 1f;
			if(t < 0) t = 0;
		}else{
			t += delta * 1f;
			if(t > 1) t = 1;
		}
		
		shootTimeout += delta;
		if(shootTimeout > shootWait && !goBack){ // TODO config
			level.mobShoot(mob);
			shootTimeout = 0;
		}
		
		mob.position.set(mob.emitter.position).mulAdd(mob.emitter.direction, (1 - t) * DELTA_POS);
		
		mob.node.translation.set(mob.position);
		
		lootAtCam(level, mob);
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}

	public void goBack() {
		goBack = true;
	}
	
	@Override
	public boolean isOver() {
		return t < 0;
	}

}