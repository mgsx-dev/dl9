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
		
		enableMeshes("StreetMob", "StreetMob2", "decor-base.010", "main street window L", "main street window R",
				"mesh.street.1", "mesh.street.2", "mesh.street.3", "mesh.street.4",
				"mesh.house.1");
		
		level.animations.play("to main street sign");
		level.animations.play("StreetMobIntro");
	}
}
