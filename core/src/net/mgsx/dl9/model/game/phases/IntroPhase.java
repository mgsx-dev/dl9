package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class IntroPhase extends BaseCinematicPhase {

	public IntroPhase(GameLevel level) {
		super(level);
		this.cameraAnimID = "Camera intro";
		music = GameAssets.i.musicCinematic1;
	}
	
	@Override
	public void started() {
		super.started();
	}
	
}
