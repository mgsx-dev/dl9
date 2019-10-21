package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public class MobDefault extends MobLogic
{
	private float time;
	private float rtime;
	private float freq = MathUtils.random(.8f, 1.2f);
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		time += delta * freq * .1f;
		if(time > 1f){
			time -= 1f;
			freq = MathUtils.random(.8f, 1.2f);
		}
		rtime += MathUtils.random(1, 10) * delta * .03f;
		if(rtime > 1) rtime -=1;
		
		float sig = MathUtils.cos(time * time * MathUtils.PI2) * 5f;
		sig = MathUtils.clamp(sig, 0, 1);
		
		mob.node.translation.set(mob.position).mulAdd(mob.direction, (sig - 1) * 1f);
		mob.node.rotation.idt();
		
		boolean balancing = true;
		if(balancing){
			mob.node.rotation.setFromAxis(Vector3.Z, MathUtils.sin(rtime * MathUtils.PI2) * 3 );
		}
		
		// orientation to camera
		float deltaLook = -0.5f;
		float angle = new Vector2(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.mul(new Quaternion().setFromAxis(Vector3.Y, angle));
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}
}
