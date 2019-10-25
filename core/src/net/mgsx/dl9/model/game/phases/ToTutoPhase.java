package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.ui.GUIUtils;

public class ToTutoPhase extends BaseCinematicPhase {

	public ToTutoPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to tuto";
	}

	@Override
	public void started() {
		super.started();
		
		GUIUtils.queueFade(level.stage, 2f, .2f, 2f, .2f, .3f,
				GUIUtils.message("Don't forget..."),
				GUIUtils.message("Left click to shoot"),
				GUIUtils.message("Right click to reload")
				);
	}
}
