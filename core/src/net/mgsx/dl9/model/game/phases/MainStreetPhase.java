package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public class MainStreetPhase extends BaseCinematicPhase {

	public MainStreetPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera main street";
	}
	
	@Override
	public void started() {
		super.started();
		
		level.animations.play("StreetMobIntro");
		level.animations.play("StreetMob2");
		
		
	}

}
