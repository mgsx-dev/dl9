package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public class ToMainStreetPhase extends BaseCinematicPhase {

	public ToMainStreetPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to main street";
	}

	@Override
	public void started() {
		super.started();
		level.animations.play("to main street sign");
	}
}
