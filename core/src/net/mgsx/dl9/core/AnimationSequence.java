package net.mgsx.dl9.core;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public abstract class AnimationSequence extends RangeSequence
{
	private AnimationController animator;

	public AnimationSequence(AnimationController animator, float timeStart, float timeEnd) {
		super(timeStart, timeEnd);
		this.animator = animator;
	}

	@Override
	public boolean isStarted(float time) {
		return animator.current.time >= this.timeStart;
	}

	@Override
	public boolean isFinished(float time) {
		return animator.current.time >= this.timeEnd;
	}
	
	@Override
	public void update(float time, float delta) {
		interpolate((animator.current.time - timeStart) / (timeEnd - timeStart));
	}
	
	
}
