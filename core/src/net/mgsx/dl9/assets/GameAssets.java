package net.mgsx.dl9.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.mgsx.dl9.audio.SoundSet;

public class GameAssets {
	public static GameAssets i;
	
	public Skin skin;
	
	public Sound sfxWindowOpen1;
	public Sound sfxWindowOpen2;
	public Sound sfxWindowClose1;
	public Sound sfxWindowClose2;

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
	
	public Music musicWitchIntro, musicWitch;

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
		
		soundSetSteps = loadSoundSet("foot-steps", 8);
		soundSetHeartBeat = loadSoundSet("heart-beat", 2);
		soundSetGunShots = loadSoundSet("gun-shot", 6);
		soundSetGunShotsReload = loadSoundSet("gun-reload");
		
		soundSetLaser = loadSoundSet("laser", 3);
		soundSetMobShooted = loadSoundSet("baby", 2); // XXX
		
		musicWitchIntro = Gdx.audio.newMusic(Gdx.files.internal("music/introwitchtheme.mp3"));
		musicWitch = Gdx.audio.newMusic(Gdx.files.internal("music/witchtheme.mp3"));
	}
	
	private SoundSet loadSoundSet(String name) {
		SoundSet set = new SoundSet();
		Sound snd = Gdx.audio.newSound(Gdx.files.internal("sfx").child(name + ".wav"));
		set.sounds.add(snd);
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
