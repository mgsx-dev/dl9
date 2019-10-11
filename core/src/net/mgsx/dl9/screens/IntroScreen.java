package net.mgsx.dl9.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.utils.UIScreen;

public class IntroScreen extends UIScreen
{
	private TextButton btPlay;
	private TextButton btSettings;

	public IntroScreen(Skin skin) {
		super(new FitViewport(1024, 768), skin);
		
		float lum = 0f;
		bgColor.set(lum, lum, lum, 0);
		
		root.add("Helloween ;-)").expand().center();
		
		root.row();
		
		root.add(btPlay = new TextButton("Play", skin)).expand().center().row();
		root.add(btSettings = new TextButton("Settings", skin)).expand().center().row();
		
		btSettings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DL9Game.i().gotoSettings();
			}
		});
		
		btPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DL9Game.i().gotoGame();
			}
		});
	}
}
