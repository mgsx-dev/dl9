package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public class MobPotiron extends MobLogic
{

	@Override
	public void update(GameLevel level, GameMob mob, float delta) {

		mob.node.translation.set(mob.position).mulAdd(mob.direction, 0);
		mob.node.rotation.idt();
		
		// orientation to camera
		float deltaLook = -0.5f;
		float angle = new Vector2(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.mul(new Quaternion().setFromAxis(Vector3.Y, angle - 90));
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}
	
	@Override
	public void onShooted() {
		// no sounds
	}
}
