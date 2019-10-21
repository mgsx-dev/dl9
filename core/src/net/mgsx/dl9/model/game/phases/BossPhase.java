package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class BossPhase extends BaseActionPhase
{

	public BossPhase(GameLevel level) {
		super(level);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void started() {
		super.started();
		GameAudio.i.playMusic(GameAssets.i.musicWitch);
	}

}
