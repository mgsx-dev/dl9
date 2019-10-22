package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public abstract class BaseActionPhase extends BasePhase {

	public BaseActionPhase(GameLevel level) {
		super(level);
	}

	@Override
	public void started() {
		super.started();
		level.setActionPhase();
		GameAudio.i.playMusic(GameAssets.i.musicWitch);
	}

}
