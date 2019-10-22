package net.mgsx.dl9;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
		if(GameConfig.ALLOW_FULLSCREEN){
			boolean alt = Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT);
			boolean altEnter = alt && Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
			boolean f11 = Gdx.input.isKeyJustPressed(Input.Keys.F11);
			boolean toggleFullscreen = altEnter || f11;
			if(toggleFullscreen){
				if(Gdx.graphics.isFullscreen()){
					Gdx.graphics.setWindowedMode(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
				}else{
					DisplayMode displayMode = Gdx.graphics.getDisplayMode();
					Gdx.graphics.setFullscreenMode(displayMode);
				}
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
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
	
	@Override
	public void setScreen(Screen screen) {
		if (this.screen != null){
			this.screen.hide();
			this.screen.dispose();
		}
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
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
