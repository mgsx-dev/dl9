package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;

public class WinScreen extends BaseScreen
{
	public WinScreen() {
		
		root.add("You win!").expand().center();
		
		fadeIn(Color.WHITE, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
			@Override
			public void run() {
				fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
					public void run() {
						DL9Game.i().gotoStats();
					}
				});
			}
		});
	}

}
