package net.mgsx.dl9.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import net.mgsx.dl9.assets.GameAssets;

public class GUIUtils {
	public static Actor message(String text) {
		Table t = new Table(GameAssets.i.skin);
		t.setFillParent(true);
		Label label = t.add(text).getActor();
		label.setAlignment(Align.center);
		return t;
	}
	public static Actor message(String text, String style) {
		Table t = new Table(GameAssets.i.skin);
		t.setFillParent(true);
		Label label = t.add(new Label(text, GameAssets.i.skin, style)).getActor();
		label.setAlignment(Align.center);
		return t;
	}

	public static void queueFade(Stage stage, float preDelay, float fadeInDuration, float duration, float fadeOutDuration, float delayBetween, Actor ...actors) {
		
		float delay = preDelay;
		float durationPerActor = fadeInDuration + duration + fadeOutDuration + delayBetween;
		for(int i=0 ; i<actors.length ; i++){
			Actor actor = actors[i];
			actor.getColor().a = 0;
			stage.addActor(actor);
			actor.addAction(Actions.sequence(
					Actions.delay(delay),
					Actions.fadeIn(fadeInDuration),
					Actions.delay(duration),
					Actions.fadeOut(fadeOutDuration)));
			delay += durationPerActor;
		}
		
	}
}
