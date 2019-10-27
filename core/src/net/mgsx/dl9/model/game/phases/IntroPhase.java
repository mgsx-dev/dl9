package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class IntroPhase extends BaseCinematicPhase {

	public float rtime;
	
	public IntroPhase(GameLevel level) {
		super(level);
		this.cameraAnimID = "Camera intro";
		music = GameAssets.i.musicCinematic1;
	}
	
	@Override
	public void started() {
		super.started();
	}
	
	@Override
	public void update(float time, float delta) {
		super.update(time, delta);
		rtime += delta;
		
		// level.globalLightTarget = level.globalLight = 0.05f;
		if(rtime > 400f / 30f){
			level.globalLightTarget = MathUtils.lerp(level.globalLightTarget, 1, delta * GameConfig.INTRO_LIGHT_SPEED);
			// level.globalLightTarget = 1f;
		}
	}
}
