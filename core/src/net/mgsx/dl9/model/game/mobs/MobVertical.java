package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
		
		mob.node.rotation.idt();
		
		// orientation to camera
		float deltaLook = .5f;
		float angle = new Vector2(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.mul(new Quaternion().setFromAxis(Vector3.Y, angle));
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}

}