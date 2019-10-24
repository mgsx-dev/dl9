package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class MenuPhase extends BaseCinematicPhase {

	public MenuPhase(GameLevel level) {
		super(level);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void started() {
		level.scene.animationController.animate(cameraAnimID, 0);
		GameAudio.i.playMusic(GameAssets.i.musicCinematic1);
		level.triggerMenu();
	}
	
	@Override
	public void finished() {
		super.finished();
		level.cameraAnimator.enable();
	}
}
