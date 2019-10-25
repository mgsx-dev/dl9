package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.MobEmitter;

public class BossIntroPhase extends BaseCinematicPhase
{

	public BossIntroPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematicBoss;
	}

	@Override
	public void started() {
		super.started();
		
		MobEmitter emitter = level.mobManager.findEmitter("Empty.witchHostelBack");
		
		level.witchScene.modelInstance.transform.setToTranslation(emitter.position);
		level.witchScene.modelInstance.transform.rotate(Vector3.Y, 180);
		
		// XXX level.witchScene.animations.playAll(false); // TODO play anim + lights et tout !
		
		level.witchScene.modelInstance.getNode("Balai").detach(); // TODO just disable
	}
	
	@Override
	public boolean isFinished(float time) {
		return false; // TODO when animation completed.
	}
}
