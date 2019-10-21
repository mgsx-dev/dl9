package net.mgsx.dl9.core;

public abstract class SceneSequence {
	
	abstract public boolean isStarted(float time);
	abstract public boolean isFinished(float time);

	public abstract void init(float time);
	
	public void started(){}
	public void update(float time, float delta){}
	public void finished(){}
}
