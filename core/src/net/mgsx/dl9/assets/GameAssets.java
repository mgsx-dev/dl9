package net.mgsx.dl9.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
	
	public Music musicWitchIntro, musicWitch, musicAction, musicAction2, musicCinematic1, musicCinematic2;
	
	public SceneAsset assetPotiron;

	public SoundSet soundSetRandomThings;

	public Music musicCinematicBoss;

	public Music musicBoss1;
	public Music musicBoss2;
	public Music musicMenu1;
	public Music musicMenu2;

	public SceneAsset mainModel;

	public SceneAsset mobModel;

	public SceneAsset assetEndGame;

	public SceneAsset witchModel;

	public Sound sfxButton;

	public SoundSet soundSetZombieDie;

	public SoundSet soundSetWitchLaugh;

	public SoundSet soundSetPumpkin;

	public SoundSet soundSetWitchHit;

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
		
		sfxButton = Gdx.audio.newSound(Gdx.files.internal("sfx/button.wav"));
		
		soundSetSteps = loadSoundSet("foot-steps", 8);
		soundSetHeartBeat = loadSoundSet("heart-beat", 2);
		soundSetGunShots = loadSoundSet("gun-shot", 6);
		soundSetGunShotsReload = loadSoundSet("gun-reload");
		
		soundSetZombieDie = loadSoundSet("zombie", 3);
		soundSetWitchLaugh = loadSoundSet("witchlaught", 4);
		soundSetPumpkin = loadSoundSet("pumpkin", 2);
		
		soundSetLaser = loadSoundSet("laser", 3);
		
		musicWitchIntro = Gdx.audio.newMusic(Gdx.files.internal("music/introwitchtheme.mp3"));
		musicWitch = Gdx.audio.newMusic(Gdx.files.internal("music/witchtheme.mp3"));
		
		musicAction = Gdx.audio.newMusic(Gdx.files.internal("music/themeaction-1.mp3"));
		musicAction2 = Gdx.audio.newMusic(Gdx.files.internal("music/themeaction-2.mp3"));
		musicCinematic1 = Gdx.audio.newMusic(Gdx.files.internal("music/themecinematic-1.mp3"));
		musicCinematic2 = Gdx.audio.newMusic(Gdx.files.internal("music/themecinematic-2.mp3"));

		musicCinematicBoss = Gdx.audio.newMusic(Gdx.files.internal("music/cinematicboss.mp3"));
		musicBoss1 = Gdx.audio.newMusic(Gdx.files.internal("music/boss-1.mp3"));
		musicBoss2 = Gdx.audio.newMusic(Gdx.files.internal("music/boss-2.mp3"));
		musicMenu1 = Gdx.audio.newMusic(Gdx.files.internal("music/menu-1.mp3"));
		musicMenu2 = Gdx.audio.newMusic(Gdx.files.internal("music/menu-2.mp3"));
		
		assetPotiron = loadGLTF(Gdx.files.internal("models/potiron/potiron.gltf"));
		
		soundSetRandomThings = loadSoundSet("baby-1", "dog-1", "dog-2", "church-shot", "strangefx", "wolf-1", "wolf-2", "dog-3", "wolf-3");
		soundSetRandomThings.durations.add(1f);
		soundSetRandomThings.durations.add(3f);
		soundSetRandomThings.durations.add(4f);
		soundSetRandomThings.durations.add(9f);
		soundSetRandomThings.durations.add(9f);
		soundSetRandomThings.durations.add(8f);
		soundSetRandomThings.durations.add(6f);
		soundSetRandomThings.durations.add(3f);
		soundSetRandomThings.durations.add(10f);
		
		soundSetWitchHit = loadSoundSet("hitthewitch", 3);
		
		witchModel = loadGLTF(Gdx.files.internal("models/witch/witch.gltf"));
		assetEndGame = loadGLTF(Gdx.files.internal("models/ending-poc/ending-poc.gltf"));
		mobModel = loadGLTF(Gdx.files.internal("models/mob/mob.gltf"));
		mainModel = loadGLTF(Gdx.files.internal("models/main-scene/main-scene.gltf"));
	}
	
	private SceneAsset loadGLTF(FileHandle file) {
		SceneAsset asset = new GLTFLoader().load(file, true);
		AssetUtils.checkAsset(asset);
		return asset;
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
