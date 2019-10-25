package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public abstract class BaseCinematicPhase extends BasePhase {

	public BaseCinematicPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematic2;
	}
	
	@Override
	public void started() {
		super.started();
		level.setCinematicPhase();
	}
	
}
