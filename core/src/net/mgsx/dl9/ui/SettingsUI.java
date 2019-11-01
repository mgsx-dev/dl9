package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.events.GotoMenuEvent;
import net.mgsx.dl9.model.settings.Setting;

public class SettingsUI extends Group {
	private Skin skin;
	private PieMenuStyle style;

	public SettingsUI(Stage stage, Skin skin, final boolean inGame) {
		super();
		this.skin = skin;
		setTransform(false);
		
		style = new PieMenuStyle();
		style.backgroundColor = Color.BROWN;
		style.selectedColor = Color.ORANGE;
		style.hoverColor = style.backgroundColor.cpy().lerp(style.selectedColor, .5f);
		style.hoverSelectedColor = style.hoverColor.cpy().lerp(style.selectedColor, .5f);
		// XXX style.highlightedChildRegionColor = style.selectedChildRegionColor.cpy().lerp(Color.WHITE, .5f);
		style.circumferenceColor = Color.BROWN.cpy().lerp(Color.BLACK, .5f);
		style.circumferenceWidth = 5f;
		style.separatorColor = style.circumferenceColor;
		style.separatorWidth = style.circumferenceWidth;
		
		
		addItem(stage, DL9Game.i().settings.difficulty, "Difficulty", 1f, 100, 10, 20);
		addItem(stage, DL9Game.i().settings.luminosity, "Luminosity", 1f, 100, 250, 20);
		
		
		TextButton bt = new TextButton(inGame ? "Close" : "Back", skin);
		bt.pack();
		bt.setPosition(stage.getWidth()/2,10, Align.bottom);
		addActor(bt);
		
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(inGame){
					DL9Game.i().setGamePaused(false);
				}else{
					event.getStage().getRoot().fire(new GotoMenuEvent());
				}
			}
		});
		
	}
	
	private void addItem(Stage stage, Setting setting, String title, float fontScale, float radius, float x, float y){
		SettingUI ui = new SettingUI(stage.getBatch(), title, setting, fontScale, skin, style, radius);
		ui.pack();
		ui.setPosition(x, stage.getHeight() - y - ui.getHeight());
		addActor(ui);
	}
	
}
