package net.mgsx.dl9.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.events.GotoMenuEvent;
import net.mgsx.dl9.ui.SettingsStatic;
import net.mgsx.dl9.utils.UIScreen;

public class SettingsScreen extends UIScreen
{
	public SettingsScreen(Skin skin) {
		super(new FitViewport(1024, 768), skin);
		
		float lum = 0f;
		bgColor.set(lum, lum, lum, 0);
		
		// TODO use this when PieMenu is fixed
//		settings = new SettingsUI(stage.getBatch(), skin);
//		settings.setFillParent(true);
//		stage.addActor(settings);
		
		SettingsStatic.create(stage, skin, false);
		
		stage.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof GotoMenuEvent){
					DL9Game.i().gotoIntro();
				}
			}
		});
	}
}
