package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobVertical extends MobBase
{
	public float shootWait = 3f; // TODO config per enemy
	private float shootTimeout;
	private float t;
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		t += delta * 5f;
		if(t > 1) t = 1;
		
		if(mob.shooted) return; // TODO move to another logic
		
		shootTimeout += delta;
		if(shootTimeout > shootWait){ // TODO config
			level.mobShoot(mob);
			shootTimeout = 0;
		}
		
		mob.node.translation.set(mob.position).add(0, (1-t) * -2f, 0);
		
		lootAtCam(level, mob);
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}

}