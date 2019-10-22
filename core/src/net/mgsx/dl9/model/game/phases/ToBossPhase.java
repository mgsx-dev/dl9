package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class ToBossPhase extends BaseCinematicPhase {

	public ToBossPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera to boss";
	}
	
	@Override
	public void started() {
		super.started();
		GameAudio.i.playMusic(GameAssets.i.musicAction);
	}

}
