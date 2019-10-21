package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;

public class IntroScreen extends BaseScreen
{
	private TextButton btPlay;
	private TextButton btSettings;

	public IntroScreen() {
		
		float lum = 0f;
		bgColor.set(lum, lum, lum, 0);
		
		root.add("Helloween ;-)").expand().center();
		
		root.row();
		
		root.add(btPlay = new TextButton("Play", skin)).expand().center().row();
		root.add(btSettings = new TextButton("Settings", skin)).expand().center().row();
		
		btSettings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
					@Override
					public void run() {
						DL9Game.i().gotoSettings();
					}
				});
			}
		});
		
		btPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
					@Override
					public void run() {
						DL9Game.i().gotoGame();
					}
				});
			}
		});
	}
}
