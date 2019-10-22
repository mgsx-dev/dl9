package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class MainStreetPhase extends BaseActionPhase {

	public MainStreetPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera main street";
	}
	
	@Override
	public void started() {
		super.started();
		GameAudio.i.playMusic(GameAssets.i.musicCinematic2);
	}

}
