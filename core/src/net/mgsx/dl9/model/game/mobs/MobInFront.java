package net.mgsx.dl9.model.game.mobs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobInFront extends MobBase
{
	private float time;
	private float t;
	
	public float attackTimeout = 2f;
	private float attackTime = 1f; // first attack happens sooner
	
	private static final Quaternion rotateY90 = new Quaternion(Vector3.Y, 90);
	private static final Matrix4 m = new Matrix4();
	
	
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
		
		m.set(level.camera.view).inv().getRotation(q);
		
		mob.node.rotation.mul(q).mul(rotateY90);
		
		// TODO necessary ?
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
		
		attackTime += delta;
		if(attackTime > attackTimeout){
			level.mobShoot(mob);
			attackTime = 0;
		}
	}
	
	@Override
	public void shooting(GameLevel level, GameMob mob) {
		super.shooting(level, mob);
		GameAssets.i.sfxZombiAttack.play();
	}
}
