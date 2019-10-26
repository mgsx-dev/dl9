package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.utils.NodeUtils;

public class WinPhase extends BaseCinematicPhase
{
	public WinPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematicBoss;
	}
	
	@Override
	public void started() {
		super.started();
		level.witchScene.modelInstance.transform.setToTranslation(level.witchCommon.emit_corridorCenter.position);
		level.witchScene.modelInstance.transform.rotate(Vector3.Y, 180);
		
		// XXX level.witchScene.animations.playAll(false); // TODO play anim + lights et tout !
		NodeUtils.enable(level.witchNode, true);
		NodeUtils.enable(level.witchBalaiNode, false);
		
		level.witchScene.animationController.animate("Witch Die", 0);
		
		level.globalLight = 1;
	}
	
	@Override
	public boolean isFinished(float time) {
		return level.witchScene.animationController.current.loopCount == 0;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.setWin();
	}

}
