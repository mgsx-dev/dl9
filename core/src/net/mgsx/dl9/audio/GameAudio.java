package net.mgsx.dl9.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import net.mgsx.dl9.assets.GameAssets;

public class GameAudio {

	public static GameAudio i;
	
	public final SoundPlayer playerGunShots;
	public final SoundPlayer playerSteps;
	public final SoundPlayer playerHeartBeat;
	public final SoundPlayer playerGunReload;
	public final SoundPlayer playerLaser;
	public final SoundPlayer playerMobShooted;
	public final SoundPlayerQueue playerRandomThings;
	
	private Music currentMusic;

	public GameAudio() {
		playerGunShots = new SoundPlayer(GameAssets.i.soundSetGunShots);
		playerGunReload = new SoundPlayer(GameAssets.i.soundSetGunShotsReload);
		playerSteps = new SoundPlayer(GameAssets.i.soundSetSteps);
		playerHeartBeat = new SoundPlayer(GameAssets.i.soundSetHeartBeat);
		playerLaser = new SoundPlayer(GameAssets.i.soundSetLaser);
		playerMobShooted = new SoundPlayer(GameAssets.i.soundSetMobShooted);
		playerRandomThings = new SoundPlayerQueue(GameAssets.i.soundSetRandomThings);
		
		playerRandomThings.volume.set(.5f, 1f);
		playerRandomThings.pan.set(-1, 1);
		playerRandomThings.delay.set(-1, 0f); // XXX pre
		
	}
	
	public void update(){
		float delta = Gdx.graphics.getDeltaTime();
		
		playerHeartBeat.volume = .6f;
		playerHeartBeat.pwm(delta, 1.2f, .66f);
		
		playerSteps.volume = .5f;
		playerSteps.random(delta, 1f, .33f);
	}
	
	public void playGunShot(){
		playerGunShots.random();
	}

	public void playGunReload() {
		playerGunReload.random();
	}

	public void playMusic(Music music) 
	{
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
	
}