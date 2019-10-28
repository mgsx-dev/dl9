package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public class ToPlacePhase extends BaseCinematicPhase {

	public ToPlacePhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to place";
	}

	@Override
	public void started() {
		super.started();
		
		disableMeshes("mesh.street.4");
		
		enableMeshes("mesh.house.3", "mesh.place.1", "mesh.place.2", "mesh.place.3", "mesh.place.ground",
				"mesh.church.ext", "mesh.church.int");
		
		enableLights("light.house.1", "light.place.1", "light.church.1");
	}
	
	@Override
	public void finished() {
		super.finished();
		disableLights("light.house.1");
	}
}
