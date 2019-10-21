package net.mgsx.dl9.core;

public class RunSequence extends SceneSequence 
{
	private float absTime;
	private float time;
	private boolean relative = true;

	public RunSequence setAbsolute(float time){
		this.time = time;
		relative = false;
		return this;
	}
	
	public RunSequence setRelative(float time){
		this.time = time;
		relative = true;
		return this;
	}
	
	@Override
	public void init(float time) {
		if(relative){
			this.time += time;
		}
	}

	@Override
	public boolean isStarted(float time) {
		return time >= this.time;
	}

	@Override
	public boolean isFinished(float time) {
		return true;
	}
}
