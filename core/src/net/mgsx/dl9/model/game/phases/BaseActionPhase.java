package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public abstract class BaseActionPhase extends BasePhase {

	public BaseActionPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicWitch;
	}

	@Override
	public void started() {
		super.started();
		level.setActionPhase();
		level.cameraAnimator.enable(); // XXX when debug phase...
	}

}
