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
		level.scene.animationController.animate(cameraAnimID, 0);
		level.cameraAnimator.disable();
		
		GUIUtils.queueFade(level.stage, 1.5f, 1.5f, 2.7f, .3f, 1f,
				GUIUtils.message("MGSX Network Production"),
				GUIUtils.message("sound design & musics\nToonguila"),
				GUIUtils.message("graphics & programming\nMgsx"),
				GUIUtils.message(GameConfig.GAME_TITLE, "stylized")
				);
	}
	
	
	@Override
	public boolean isFinished(float time) {
		return false; // killed by game screen when fade is ok
	}
	
}
