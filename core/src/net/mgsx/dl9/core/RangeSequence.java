package net.mgsx.dl9.core;

public abstract class RangeSequence extends SceneSequence {

	public float timeStart, timeEnd;

	public RangeSequence(float timeStart, float timeEnd) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	public boolean isStarted(float time){
		return time >= timeStart;
	}
	
	public boolean isFinished(float time){
		return time >= timeEnd;
	}
	
	@Override
	public void update(float time, float delta) {
		interpolate((time - timeStart) / (timeEnd - timeStart));
	}
	
	protected abstract void interpolate(float t);

}
