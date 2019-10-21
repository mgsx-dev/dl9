package net.mgsx.dl9.core;

import com.badlogic.gdx.utils.Array;

public class SceneSequencer {

	private final Array<SceneSequence> pending = new Array<SceneSequence>();
	private final Array<SceneSequence> running = new Array<SceneSequence>();
	private final Array<SceneSequence> starting = new Array<SceneSequence>();
	private final Array<SceneSequence> finishing = new Array<SceneSequence>();
	
	private float time;
	
	public void add(SceneSequence sequence){
		pending.add(sequence);
		sequence.init(time);
	}
	
	public void clear() {
		pending.clear();
		running.clear();
	}

	public void update(float delta){
		time += delta;
		
		pendingToRunning();
		
		for(int i=0 ; i<running.size ; i++){
			running.get(i).update(time, delta);
		}
		
		runningToFinishing();
	}

	private void pendingToRunning() 
	{
		for(int i=0 ; i<pending.size ; ){
			SceneSequence s = pending.get(i);
			if(s.isStarted(time)){
				starting.add(s);
				pending.removeIndex(i);
			}else{
				i++;
			}
		}
		
		for(int i=0 ; i<starting.size ; i++){
			SceneSequence s = starting.get(i);
			running.add(s);
			s.started();
		}
		starting.clear();
	}

	private void runningToFinishing() 
	{
		for(int i=0 ; i<running.size ; ){
			SceneSequence s = running.get(i);
			if(s.isFinished(time)){
				finishing.add(s);
				running.removeIndex(i);
			}else{
				i++;
			}
		}
		for(int i=0 ; i<finishing.size ; i++){
			finishing.get(i).finished();
		}
		finishing.clear();
	}

	public boolean isEmpty() {
		return running.size == 0 && pending.size == 0;
	}
	
}
