package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.ui.SettingsPOC;
import net.mgsx.dl9.utils.FullscreenUtils;

// TODO maybe use it as a pre intro (intro played only once!
public class IntroScreen extends BaseScreen
{
	private TextButton btPlay;
	private TextButton btFullscreen;

	public IntroScreen() {
		
		float lum = 0f;
		bgColor.set(lum, lum, lum, 0);

		float sep = 30;
		
		

		
		root.add("For a better experience").row();
		root.add("- Switch to Fullscreen -").row();
		root.add("- Turn up audio volume -").row();
		root.add("- Turn off the lights -").row();
		root.add("- Lean back and enjoy -").row();
		root.add().height(sep).row();

		
		root.add(btFullscreen = new TextButton("Toggle Fullscreen (f11 or alt+enter)", skin)).row();;
		root.add().height(sep * 3).row();

		root.add("Warning, this game may kill you").row();
		root.add().height(sep).row();
		
		root.add(btPlay = new TextButton("I fully understand and I'm Ready to Die", skin)).row();
		
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
		
		btFullscreen.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				FullscreenUtils.toggle();
			}
		});
		
		// XXX for testing
		stage.clear();
		stage.addActor(new SettingsPOC(stage, skin));
	}
	
	@Override
	public void show() {
		super.show();
		GameAudio.i.playMusic(GameAssets.i.musicMenu2);
	}
	
	@Override
	public void render(float delta) {
		
		// simple sound check
		GameAudio.i.playerHeartBeat.volume = .6f;
		GameAudio.i.playerHeartBeat.pwm(delta, 1.2f, .66f);
		
		GameAudio.i.playerSteps.volume = .5f;
		GameAudio.i.playerSteps.random(delta, 1f, .33f);
		
		
		super.render(delta);
	}
}
