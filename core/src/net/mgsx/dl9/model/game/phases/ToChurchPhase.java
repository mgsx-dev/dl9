package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class ToChurchPhase extends BaseCinematicPhase {

	public ToChurchPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to church";
	}
	
	@Override
	public void started() {
		super.started();
		GameAssets.i.sfxChurch.play();
	}

}
