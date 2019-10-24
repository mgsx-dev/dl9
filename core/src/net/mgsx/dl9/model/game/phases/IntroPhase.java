package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class IntroPhase extends BaseCinematicPhase {

	public IntroPhase(GameLevel level) {
		super(level);
		this.cameraAnimID = "Camera intro";
	}
	
	@Override
	public void started() {
		level.scene.animationController.animate(cameraAnimID, 0);
		GameAudio.i.playMusic(GameAssets.i.musicCinematic1);
	}
	
}
