package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class PreIntroPhase extends BaseCinematicPhase {

	public PreIntroPhase(GameLevel level) {
		super(level);
		this.cameraAnimID = "Camera init";
	}
	
	@Override
	public void started() {
		level.scene.animationController.animate(cameraAnimID, 0);
		GameAudio.i.playMusic(GameAssets.i.musicCinematic1);
		level.cameraAnimator.disable();
	}
	
	
	@Override
	public boolean isFinished(float time) {
		return false; // killed by game screen when fade is ok
	}
	
}
