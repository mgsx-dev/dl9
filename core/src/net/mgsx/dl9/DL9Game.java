package net.mgsx.dl9;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.mgsx.dl9.model.settings.Settings;
import net.mgsx.dl9.screens.IntroScreen;
import net.mgsx.dl9.screens.SettingsScreen;

public class DL9Game extends Game {
	
	private Skin skin;
	
	public final Settings settings = new Settings();

	public static DL9Game i() {
		return (DL9Game)Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		gotoIntro();
	}

	public void gotoIntro() {
		setScreen(new IntroScreen(skin));
	}

	public void gotoSettings() {
		setScreen(new SettingsScreen(skin));
	}

	public void gotoGame() {
		// TODO set game screen
	}

}
