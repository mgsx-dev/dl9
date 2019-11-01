package net.mgsx.dl9.model.game;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;

public abstract class MobLogic {

	protected static final Quaternion q = new Quaternion();
	protected static final Vector2 vec2 = new Vector2();
	
	public abstract void update(GameLevel level, GameMob mob, float delta);

	public void onShooted(GameLevel level, GameMob mob) {
		GameAudio.i.sfxZombiDead(level.camera.position.dst(mob.position));
	}
	
	public void shooting(GameLevel level, GameMob mob){
		GameAssets.i.soundSetLaser.sounds.get(1).play();
	}

	public boolean isOver() {
		return false;
	}
	
	protected void lootAtCam(GameLevel level, GameMob mob){
		// orientation to camera
		float deltaLook = .5f;
		float angle = vec2.set(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.idt().mul(q.setFromAxis(Vector3.Y, -angle + 180));
	}
	protected void lootAtCamPumkin(GameLevel level, GameMob mob){
		// orientation to camera
		float deltaLook = .5f;
		float angle = vec2.set(
				level.camera.position.x + level.camera.direction.x * deltaLook - mob.position.x, 
				level.camera.position.z + level.camera.direction.z * deltaLook - mob.position.z).nor().angle();
		
		mob.node.rotation.idt().mul(q.setFromAxis(Vector3.Y, -angle + 90));
	}

}
