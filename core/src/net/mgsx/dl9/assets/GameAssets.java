package net.mgsx.dl9.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.mgsx.dl9.audio.SoundSet;
import net.mgsx.dl9.utils.AssetUtils;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GameAssets {
	public static GameAssets i;
	
	public Skin skin;
	
	public Sound sfxWindowOpen1;
	public Sound sfxWindowOpen2;
	public Sound sfxWindowClose1;
	public Sound sfxWindowClose2;
	public Sound sfxDeath;
	public Sound sfxZombiAttack;
	public Sound sfxChurch;

	public Cursor cursorShoot;
	public Cursor cursorReload;
	public Cursor cursorWait;
	public Cursor cursorIdle;

	public SoundSet soundSetSteps;
	public SoundSet soundSetHeartBeat;
	public SoundSet soundSetGunShots;
	public SoundSet soundSetGunShotsReload;
	public SoundSet soundSetLaser;
	public SoundSet soundSetMobShooted;
	
	public Music musicWitchIntro, musicWitch, musicAction, musicAction2, musicCinematic1, musicCinematic2;
	
	public SceneAsset assetPotiron;

	public SoundSet soundSetRandomThings;

	public GameAssets(){
		
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		
		cursorShoot = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/cursor-shoot.png")), 32, 32);
		cursorReload = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/cursor-reload.png")), 32, 32);
		cursorWait = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/cursor-wait.png")), 32, 32);
		cursorIdle = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursors/cursor-idle.png")), 32, 32);
		
		sfxWindowOpen1 = Gdx.audio.newSound(Gdx.files.internal("sfx/windowopen1.wav"));
		sfxWindowOpen2 = Gdx.audio.newSound(Gdx.files.internal("sfx/windowopen2.wav"));
		sfxWindowClose1 = Gdx.audio.newSound(Gdx.files.internal("sfx/windowclose1.wav"));
		sfxWindowClose2 = Gdx.audio.newSound(Gdx.files.internal("sfx/windowclose2.wav"));
		
		sfxDeath = Gdx.audio.newSound(Gdx.files.internal("sfx/youdie.wav"));
		sfxZombiAttack = Gdx.audio.newSound(Gdx.files.internal("sfx/zombieattack.wav"));
		sfxChurch = Gdx.audio.newSound(Gdx.files.internal("sfx/church.wav"));
		
		soundSetSteps = loadSoundSet("foot-steps", 8);
		soundSetHeartBeat = loadSoundSet("heart-beat", 2);
		soundSetGunShots = loadSoundSet("gun-shot", 6);
		soundSetGunShotsReload = loadSoundSet("gun-reload");
		
		soundSetLaser = loadSoundSet("laser", 3);
		soundSetMobShooted = loadSoundSet("baby", 2); // XXX
		
		musicWitchIntro = Gdx.audio.newMusic(Gdx.files.internal("music/introwitchtheme.mp3"));
		musicWitch = Gdx.audio.newMusic(Gdx.files.internal("music/witchtheme.mp3"));
		
		musicAction = Gdx.audio.newMusic(Gdx.files.internal("music/themeaction-1.mp3"));
		musicAction2 = Gdx.audio.newMusic(Gdx.files.internal("music/themeaction-2.mp3"));
		musicCinematic1 = Gdx.audio.newMusic(Gdx.files.internal("music/themecinematic-1.mp3"));
		musicCinematic2 = Gdx.audio.newMusic(Gdx.files.internal("music/themecinematic-2.mp3"));

		
		assetPotiron = new GLTFLoader().load(Gdx.files.internal("models/potiron/potiron.gltf"), true);
		AssetUtils.checkAsset(assetPotiron);
		
		soundSetRandomThings = loadSoundSet("baby-1", "dog-1", "dog-2", "church-shot", "strangefx", "wolf-1", "wolf-2");
		soundSetRandomThings.durations.add(1f);
		soundSetRandomThings.durations.add(3f);
		soundSetRandomThings.durations.add(4f);
		soundSetRandomThings.durations.add(9f);
		soundSetRandomThings.durations.add(9f);
		soundSetRandomThings.durations.add(8f);
		soundSetRandomThings.durations.add(6f);
	}
	
	private SoundSet loadSoundSet(String ... names) {
		SoundSet set = new SoundSet();
		for(String name : names){
			Sound snd = Gdx.audio.newSound(Gdx.files.internal("sfx").child(name + ".wav"));
			set.sounds.add(snd);
		}
		return set;
	}

	private SoundSet loadSoundSet(String name, int n) {
		SoundSet set = new SoundSet();
		for(int i=1; i<=n ; i++){
			Sound snd = Gdx.audio.newSound(Gdx.files.internal("sfx").child(name + "-" + i + ".wav"));
			set.sounds.add(snd);
		}
		return set;
	}
}
