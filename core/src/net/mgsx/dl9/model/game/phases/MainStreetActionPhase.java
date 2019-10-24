package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;

public class MainStreetActionPhase extends BaseActionPhase {

	public MainStreetActionPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera main street action";
	}
	
	@Override
	public void started() {
		super.started();
		GameAudio.i.playMusic(GameAssets.i.musicCinematic2);
		
		level.animations.play("main street window L");
		level.animations.play("main street window R");
		level.animations.play("main street window battant");
		
	}

}
