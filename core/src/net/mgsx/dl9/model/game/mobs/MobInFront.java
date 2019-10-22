package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public class MobInFront extends MobLogic
{
	private float time;
	private float t;
	
	public float attackTimeout = 2f;
	private float attackTime = 1f; // first attack happens sooner
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		time += delta;
		t += delta * 4f;
		if(t > 1){
			t = 1;
		}
		
		mob.node.translation.set(mob.position).mulAdd(mob.direction, t).add(0, -1f, 0).mulAdd(level.camera.direction, -1);
		
		mob.node.translation.set(level.camera.position).mulAdd(level.camera.direction, 1f).add(0, -1.65f, 0).mulAdd(mob.direction, -1 * (1 - t));
		
		mob.node.rotation.idt();
		
		boolean balancing = true;
		if(balancing){
			mob.node.rotation.setFromAxis(Vector3.Z, MathUtils.sin(time * 1f * MathUtils.PI2) * 3 );
		}
		
		// orientation to camera
		float deltaLook = .5f;
		float angle = new Vector2(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.mul(new Quaternion().setFromAxis(Vector3.Y, angle));
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
		
		attackTime += delta;
		if(attackTime > attackTimeout){
			level.mobShoot(mob);
			attackTime = 0;
		}
	}
	
	@Override
	public void shooting() {
		GameAssets.i.sfxZombiAttack.play();
	}
}
