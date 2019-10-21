package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;

public class StatsScreen extends BaseScreen {

	public StatsScreen() {
		root.add("TODO stats...").row();
		
		final TextButton btOK = new TextButton("OK", skin);
		
		root.add(btOK).row();
		
		btOK.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
					public void run() {
						DL9Game.i().gotoIntro();
					}
				});
			}
		});
		
		fadeIn(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
			@Override
			public void run() {
				// nothing to do
			}
		});
	}
}
