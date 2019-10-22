package net.mgsx.dl9.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class SoundPlayerQueue {
	
	private final SoundSet soundSet;
	private final Array<Sound> soundsToPlay = new Array<Sound>();
	
	private Sound lastSound = null;
	
	private float timeout;
	
	public final RandomRange volume = new RandomRange(.5f, 1f);
	public final RandomRange pan = new RandomRange(-1f, 1f);
	public final RandomRange delay = new RandomRange(0f, 1f);
	

	public SoundPlayerQueue(SoundSet soundSet) {
		super();
		this.soundSet = soundSet;
	}

	public void update(float delta){
		timeout -= delta;
		if(timeout < 0){
			if(soundsToPlay.size == 0){
				soundsToPlay.addAll(soundSet.sounds);
				if(lastSound != null){
					soundsToPlay.removeValue(lastSound, true);
				}
			}
			Sound soundToPlay = null;
			if(soundsToPlay.size > 0){
				soundToPlay = soundsToPlay.random();
				soundsToPlay.removeValue(soundToPlay, true);
			}else if(soundSet.sounds.size > 0){
				soundToPlay = soundSet.sounds.first();
			}
			if(soundToPlay != null){
				soundToPlay.play(volume.get(), 1f, pan.get());
				lastSound = soundToPlay;
				int sndIndex = soundSet.sounds.indexOf(soundToPlay, true);
				float duration = soundSet.durations.get(sndIndex);
				timeout = duration + delay.get();
			}
		}
	}
}
