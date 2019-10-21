package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.model.game.GameLevel;

public abstract class BaseCinematicPhase extends BasePhase {

	public BaseCinematicPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		level.setCinematicPhase();
	}
	
}
