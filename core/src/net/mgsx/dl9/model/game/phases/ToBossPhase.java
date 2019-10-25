package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class ToBossPhase extends BaseCinematicPhase {

	public ToBossPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to boss";
		music = GameAssets.i.musicCinematicBoss;
	}
	
}
