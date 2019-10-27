package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.model.game.GameLevel;

public class StatsScreen extends BaseScreen {

	public StatsScreen() {
		
		// GameAudio.i.playMusic(GameAssets.i.musicAction);
		
		root.add("Game Over").row();
		
		GameLevel level = DL9Game.i().lastLevel;
		if(level != null){
		
			
			if(level.heroLife <= 0){
				root.add("You failed").row();
			}else{
				root.add("You saved " + GameConfig.GAME_TITLE).row();
			}
			
			root.add("- Your Stats -").padTop(50).row();
			
			root.add("Red eye contamination: " + level.nbLazerGet).row();
			root.add("Bullet used: " + level.nbBulletUsed).row();
			root.add("Zombi killed: " + level.nbZombiKilled).row();
			
		}
		
		final TextButton btOK = new TextButton("OK", skin);
		
		root.add(btOK).padTop(50).row();
		
		btOK.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
					public void run() {
						DL9Game.i().gotoGame();
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
