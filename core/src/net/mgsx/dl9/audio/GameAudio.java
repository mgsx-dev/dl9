package net.mgsx.dl9.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;

public class GameAudio {

	public static GameAudio i;
	
	public final SoundPlayer playerGunShots;
	public final SoundPlayer playerSteps;
	public final SoundPlayer playerHeartBeat;
	public final SoundPlayer playerGunReload;
	public final SoundPlayer playerLaser;
	public final SoundPlayerQueue playerRandomThings;
	
	private Music currentMusic;
	
	public GameAudio() {
		playerGunShots = new SoundPlayer(GameAssets.i.soundSetGunShots);
		playerGunReload = new SoundPlayer(GameAssets.i.soundSetGunShotsReload);
		playerSteps = new SoundPlayer(GameAssets.i.soundSetSteps);
		playerHeartBeat = new SoundPlayer(GameAssets.i.soundSetHeartBeat);
		playerLaser = new SoundPlayer(GameAssets.i.soundSetLaser);
		playerRandomThings = new SoundPlayerQueue(GameAssets.i.soundSetRandomThings);
		
		playerRandomThings.volume.set(.5f, 1f);
		playerRandomThings.pan.set(-1, 1);
		playerRandomThings.delay.set(-1, 0f); // XXX pre
		
	}
	
	public void update(){
		if(!GameConfig.AUDIO_ENABLED) return;
		// TODO fade in fade out music if necessary ?
	}
	
	public void playGunShot(){
		if(!GameConfig.AUDIO_ENABLED) return;
		playerGunShots.random();
	}

	public void playGunReload() {
		if(!GameConfig.AUDIO_ENABLED) return;
		playerGunReload.random();
	}

	public void playMusic(Music music) 
	{
		if(!GameConfig.AUDIO_ENABLED) return;
		if(currentMusic != music){
			// TODO cross fade
			if(currentMusic != null){
				currentMusic.stop();
			}
			currentMusic = music;
			if(currentMusic != null){
				currentMusic.setLooping(true);
				currentMusic.play();
			}
		}
	}

	public void sfxButton() {
		GameAssets.i.sfxButton.play();
	}
	
	public void sfxZombiDead(float distance){
		float volume = MathUtils.map(1f, 20f, 1f, 0.8f, distance);
		volume = MathUtils.clamp(volume, 0, 1);
		if(distance < 10){
			GameAssets.i.soundSetZombieDie.sounds.get(0).play(volume);
		}else if(distance < 30){
			GameAssets.i.soundSetZombieDie.sounds.get(1).play(volume);
		}else{
			GameAssets.i.soundSetZombieDie.sounds.get(2).play(volume);
		}
		
		
	}
	
}
