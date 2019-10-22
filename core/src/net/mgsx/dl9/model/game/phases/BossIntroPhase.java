package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class BossIntroPhase extends BaseCinematicPhase
{

	public BossIntroPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		level.setCinematicPhase();
		GameAudio.i.playMusic(GameAssets.i.musicAction);
	}

}
