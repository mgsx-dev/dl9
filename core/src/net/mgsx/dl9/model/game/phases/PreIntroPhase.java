package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.ui.GUIUtils;

public class PreIntroPhase extends BaseCinematicPhase {

	public PreIntroPhase(GameLevel level) {
		super(level);
		this.cameraAnimID = "Camera init";
		music = GameAssets.i.musicCinematic1;
	}
	
	@Override
	public void started() {
		super.started();
		
		enableMeshes("IntroFlag", "sign main", "mesh.intro.1", "mesh.intro.2");
		
		enableLights("light.intro.1", "light.intro.2", "light.intro.3");
		
		level.scene.animationController.animate(cameraAnimID, 0);
		level.cameraAnimator.disable();
		
		level.globalLightTarget = level.globalLight = GameConfig.INTRO_LIGHT_POWER;
		
		GUIUtils.queueFade(level.stage, 1.5f, 1.5f, 2.7f, .3f, 1f,
				GUIUtils.message("MGSX Network Production"),
				GUIUtils.message("sound design & music\nToonguila"),
				GUIUtils.message("programming & graphics\nMgsx"),
				GUIUtils.message(GameConfig.GAME_TITLE, "stylized")
				);
	}
	
	@Override
	public void update(float time, float delta) {
		super.update(time, delta);
	}
	
	@Override
	public void finished() {
		super.finished();
		
	}
	
	@Override
	public boolean isFinished(float time) {
		return false; // killed by game screen when fade is ok
	}
	
}
