package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class MainStreetActionPhase extends BaseActionPhase {

	public MainStreetActionPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera main street action";
		music = GameAssets.i.musicCinematic2;
	}
	
	@Override
	public void started() {
		super.started();
		level.animations.play("main street window L");
		level.animations.play("main street window R");
		level.animations.play("main street window battant");
	}

}
