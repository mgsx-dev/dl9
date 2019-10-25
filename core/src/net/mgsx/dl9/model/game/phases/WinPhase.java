package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class WinPhase extends BaseCinematicPhase
{
	public WinPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematicBoss;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.setWin();
	}

}
