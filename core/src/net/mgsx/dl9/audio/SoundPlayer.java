package net.mgsx.dl9.audio;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.GameConfig;

public class SoundPlayer {
	private SoundSet soundSet;
	
	private float time;
	private int index;
	
	public float volume = 1f;

	public SoundPlayer(SoundSet soundSet) {
		super();
		this.soundSet = soundSet;
	}

	public void sequence(float delta, float freq) {
		if(!GameConfig.AUDIO_ENABLED) return;
		time += delta * freq;
		int i = MathUtils.floor(time) % soundSet.sounds.size;
		if(i != index){
			index = i;
			soundSet.sounds.get(index).play(volume);
		}
	}

	public void pwm(float delta, float freq, float pwm) {
		if(!GameConfig.AUDIO_ENABLED) return;
		time += delta * freq;
		if(time > 1 && index == 1){
			time = time % 1f;
			index = 0;
			soundSet.sounds.get(index).play(volume);
		}else if(time > pwm && index == 0){
			index = 1;
			soundSet.sounds.get(index).play(volume);
		}
	}

	public void random(float delta, float freq, float randomness) {
		if(!GameConfig.AUDIO_ENABLED) return;
		time += delta * (1 + randomness * (MathUtils.random() * 2 - 1)) * freq;
		int i = MathUtils.floor(time) % soundSet.sounds.size;
		if(i != index){
			index = i;
			soundSet.sounds.get(MathUtils.random(soundSet.sounds.size-1)).play(volume * MathUtils.lerp(.5f, 1f, MathUtils.random()));
		}
	}

	public void random() {
		if(!GameConfig.AUDIO_ENABLED) return;
		soundSet.sounds.get(MathUtils.random(soundSet.sounds.size-1)).play(volume);
	}
	
}
