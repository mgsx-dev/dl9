package net.mgsx.dl9.model.game.mobwitch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public abstract class MobWitchBase extends MobLogic {

	protected final static Vector3 witchPosition = new Vector3();
	private static Vector2 vCam = new Vector2();
	
	public boolean isOver() {
		return false;
	}
	
	protected void updateTransform(GameLevel level){
		level.witchScene.modelInstance.transform.setToTranslation(witchPosition);
		
		vCam.set(level.camera.position.x, level.camera.position.z).sub(witchPosition.x, witchPosition.z);
		float angle = vCam.angle();
		
		level.witchScene.modelInstance.transform.rotate(Vector3.Y, -angle + 90);
	}

	@Override
	public void onShooted(GameLevel level, GameMob mob) {
		GameAssets.i.soundSetWitchHit.sounds.random().play();
		// and not dead (never)
		System.out.println("witch shooted");
	}
}
