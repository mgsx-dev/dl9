package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.utils.NodeUtils;

public class BossIntroPhase extends BaseCinematicPhase
{
	private float time;

	public BossIntroPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematicBoss;
	}

	@Override
	public void started() {
		super.started();
		
		level.globalLightTarget = 0;
		
		level.witchScene.modelInstance.transform.setToTranslation(level.witchCommon.emit_hostelBack.position);
		level.witchScene.modelInstance.transform.rotate(Vector3.Y, 180);
		
		// XXX level.witchScene.animations.playAll(false); // TODO play anim + lights et tout !
		NodeUtils.enable(level.witchNode, true);
		NodeUtils.enable(level.witchBalaiNode, false);
		
		level.witchScene.animationController.animate("Witch Intro", 0);
	}
	
	@Override
	public void update(float gtime, float delta) {
		super.update(gtime, delta);
		time += delta;
		if(time > 5){
			level.globalLightTarget = 1;
			level.globalLightFX = 1;
		}
	}
	
	@Override
	public boolean isFinished(float time) {
		return level.witchScene.animationController.current.loopCount == 0;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.globalLightFX = 0;
	}
}
