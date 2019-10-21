package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public class ToPlacePhase extends BaseCinematicPhase {

	public ToPlacePhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to place";
	}

}
