package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class BossMidPhase extends BaseCinematicPhase
{

	public BossMidPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematicBoss;
	}

	@Override
	public void started() {
		super.started();
		
		level.globalLightTarget = 10; // XXX
		
		level.witchScene.modelInstance.transform.setToTranslation(level.witchCommon.emit_corridorCenter.position);
		level.witchScene.modelInstance.transform.rotate(Vector3.Y, 180);
		level.witchScene.animationController.animate("Witch Transfo", 0);
	}
	
	@Override
	public boolean isFinished(float time) {
		return level.witchScene.animationController.current.loopCount == 0;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.globalLightTarget = 0;
	}
}
