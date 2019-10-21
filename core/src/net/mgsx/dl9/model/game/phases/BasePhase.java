package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.core.SceneSequence;
import net.mgsx.dl9.model.game.GameLevel;

public abstract class BasePhase extends SceneSequence
{
	protected final GameLevel level;
	
	protected String cameraAnimID = null;
	
	public BasePhase(GameLevel level) {
		super();
		this.level = level;
	}

	@Override
	public void init(float time) {
		
	}
	
	@Override
	public void started() {
		if(cameraAnimID != null){
			level.scene.animationController.animate(cameraAnimID, 0);
		}
	}
	
	@Override
	public void finished() {
		// XXX for debug sequences
		if(level.scene.animationController.current != null){
			level.scene.animationController.current.time = level.scene.animationController.current.duration;
		}
	}
	
	@Override
	public boolean isStarted(float time) {
		return true;
	}
	
	@Override
	public boolean isFinished(float time) {
		if(cameraAnimID != null){
			return level.scene.animationController.current == null || level.scene.animationController.current.loopCount == 0;
		}
		return false;
	}
}
