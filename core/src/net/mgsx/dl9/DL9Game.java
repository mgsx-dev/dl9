package net.mgsx.dl9;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.mgsx.dl9.screens.IntroScreen;

public class DL9Game extends Game {
	
	private Skin skin;

	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		setScreen(new IntroScreen(skin));
	}
	
}
