package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;

public class WinScreen extends BaseScreen
{
	public WinScreen() {
		
		root.add("Congratulations!").row();
		root.add("You survived").row();
		root.add("You saved " + GameConfig.GAME_TITLE).row();
		
		fadeIn(Color.WHITE, 3f, new Runnable() {
			@Override
			public void run() {
				stage.addAction(Actions.sequence(Actions.delay(3f), Actions.run(new Runnable() {
					@Override
					public void run() {
						fadeOut(Color.BLACK, 3f, new Runnable() {
							public void run() {
								DL9Game.i().gotoStats();
							}
						});
					}
				})));
				
			}
		});
	}

}
