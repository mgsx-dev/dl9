package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobWindow extends MobBase
{
	public float shootWait = 3f; // TODO config per enemy
	private float shootTimeout;
	
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		if(mob.shooted) return; // TODO move to another logic
		
		shootTimeout += delta;
		if(shootTimeout > shootWait){ // TODO config
			level.mobShoot(mob);
			shootTimeout = 0;
		}
		
		mob.node.translation.set(mob.position).add(.5f, -1, 0);
		
		lootAtCam(level, mob);
		
		// TODO ?
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}

}
