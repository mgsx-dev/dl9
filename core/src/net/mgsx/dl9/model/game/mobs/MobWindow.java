package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
		
		mob.node.rotation.idt();
		
		// orientation to camera
		float deltaLook = .5f;
		float angle = new Vector2(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.mul(new Quaternion().setFromAxis(Vector3.Y, angle + 180));
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}

}
