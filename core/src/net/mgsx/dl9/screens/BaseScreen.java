package net.mgsx.dl9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.utils.UIScreen;

public abstract class BaseScreen extends UIScreen {

	private Image overScreen;
	
	public BaseScreen() {
		super(new FitViewport(1920, 1080), GameAssets.i.skin);
		
		overScreen = new Image(skin, "white");
		overScreen.setFillParent(true);
		overScreen.setScaling(Scaling.fill);
	}

	protected void fadeIn(Color color, float duration, Runnable callback) {
		overScreen.clearActions();
		stage.addActor(overScreen);
		overScreen.setColor(color);
		overScreen.setSize(stage.getWidth(), stage.getHeight());
		overScreen.addAction(Actions.sequence(Actions.alpha(0, duration), Actions.run(callback), Actions.removeActor()));
	}
	
	protected void fadeOut(Color color, float duration, Runnable callback) {
		overScreen.clearActions();
		stage.addActor(overScreen);
		overScreen.setColor(color);
		overScreen.getColor().a = 0;
		overScreen.addAction(Actions.sequence(Actions.alpha(1, duration), Actions.run(callback), Actions.removeActor()));
	}
	
	protected void fade(Color color, float t){
		if(!overScreen.hasParent()){
			stage.addActor(overScreen);
		}
		overScreen.setColor(color);
		overScreen.getColor().a = t;
	}
	
}
