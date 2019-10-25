package net.mgsx.dl9.core;

import com.badlogic.gdx.utils.Array;

public class QueueSequence extends SceneSequence {
	private final Array<SceneSequence> sequences = new Array<SceneSequence>();

	@Override
	public boolean isStarted(float time) {
		return true;
	}

	@Override
	public boolean isFinished(float time) {
		return sequences.size == 0;
	}

	@Override
	public void init(float time) {
		
	}
	
	@Override
	public void started() {
		if(sequences.size > 0){
			SceneSequence s = sequences.first();
			s.started();
		}
	}
	
	@Override
	public void update(float time, float delta) {
		if(sequences.size > 0){
			SceneSequence s = sequences.first();
			if(s.isFinished(time)){
				next();
			}
			s.update(time, delta);
		}
	}

	public void add(SceneSequence sequence) {
		sequences.add(sequence);
	}

	public void next() {
		if(sequences.size > 0){
			SceneSequence s = sequences.first();
			s.finished();
			sequences.removeIndex(0);
			if(sequences.size > 0){
				s = sequences.first();
				s.started();
			}
		}
		if(sequences.size > 0){
			System.out.println("Sequence " + sequences.first().toString());
		}
	}

}
