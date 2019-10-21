package net.mgsx.dl9;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Collections;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.settings.Settings;
import net.mgsx.dl9.screens.GameOverScreen;
import net.mgsx.dl9.screens.GameScreen;
import net.mgsx.dl9.screens.IntroScreen;
import net.mgsx.dl9.screens.SettingsScreen;
import net.mgsx.dl9.screens.StatsScreen;
import net.mgsx.dl9.screens.WinScreen;
import net.mgsx.dl9.ui.CursorManager;
import net.mgsx.dl9.utils.Stats;
import net.mgsx.dl9.utils.Stats.Mode;

public class DL9Game extends Game {
	
	public final Settings settings = new Settings();
	
	private boolean paused = false;
	
	private Stats stats;

	public static DL9Game i() {
		return (DL9Game)Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create () {
		Collections.allocateIterators = true;
		stats = new Stats();
		
		GameAssets.i = new GameAssets();
		GameAudio.i = new GameAudio();
		CursorManager.i = new CursorManager();
		CursorManager.i.setIdle();
		
		// XXX gotoIntro();
		gotoGame();
	}
	
	@Override
	public void render() {
		float delta = paused ? 0 : Gdx.graphics.getDeltaTime();
		if(GameConfig.ALLOW_PAUSE){
			if(Gdx.input.isKeyJustPressed(Input.Keys.P)) paused = !paused;
		}
		if(GameConfig.DEBUG){
			if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
				stats.enable(!stats.isEnabled());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
				stats.setMode(Mode.SILENT);
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
				stats.setMode(Mode.LOG);
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
				stats.setMode(Mode.RAISE);
			}
		}
		CursorManager.i.update(delta);
		GameAudio.i.update();
		if (screen != null) screen.render(delta);
		stats.update();
	}

	public void gotoIntro() {
		setScreen(new IntroScreen());
	}

	public void gotoSettings() {
		setScreen(new SettingsScreen(GameAssets.i.skin));
	}

	public void gotoGame() {
		setScreen(new GameScreen());
	}

	public void gotoDieScreen() {
		setScreen(new GameOverScreen());
	}

	public void gotoWinScreen() {
		setScreen(new WinScreen());
	}

	public void gotoStats() {
		setScreen(new StatsScreen());
	}

}
