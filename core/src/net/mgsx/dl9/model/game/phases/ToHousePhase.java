package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public class ToHousePhase extends BaseCinematicPhase {

	public ToHousePhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to house";
	}
	
	@Override
	public void started() {
		super.started();
		
		disableMeshes("StreetMob", "StreetMob2", "decor-base.010", "main street window L", "main street window R",
				"mesh.intro.2", "mesh.street.1", "mesh.street.2", "mesh.street.3");
		
		disableLights("point.street.1", "point.street.2");

	}

}
