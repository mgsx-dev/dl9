package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.payne.games.piemenu.PieMenu;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.events.GotoMenuEvent;
import net.mgsx.dl9.model.settings.Setting;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SettingsStatic {

	private static final float RADIUS = 150;
	private static final float SPACE = 30;
	
	public static void create(Stage stage, Skin skin){
		
		createMenu(stage, skin, -1, DL9Game.i().settings.quality, false, "Quality");
		createMenu(stage, skin, 0, DL9Game.i().settings.luminosity, true, "Luminosity");
		createMenu(stage, skin, 1, DL9Game.i().settings.spookiness, false, "Spookiness");
		
		TextButton bt = new TextButton("Back", skin);
		bt.pack();
		bt.setPosition(stage.getWidth()/2,bt.getHeight() + 10, Align.center);
		stage.addActor(bt);
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				event.getStage().getRoot().fire(new GotoMenuEvent());
			}
		});
	}
	
	private static PieMenu createMenu(Stage stage, Skin skin, int id, final Setting setting, boolean halfCircle, String title){
		
		float distance = RADIUS * 2 + SPACE;
		
		Label label = new Label(title, skin);
		stage.addActor(label);
		
		PieMenuStyle style = new PieMenuStyle();
		style.backgroundColor = Color.BROWN;
		style.selectedChildRegionColor = Color.ORANGE;
		style.hoveredChildRegionColor = style.backgroundColor.cpy().lerp(style.selectedChildRegionColor, .5f);
		style.hoveredAndSelectedChildRegionColor = style.hoveredChildRegionColor.cpy().lerp(style.selectedChildRegionColor, .5f);
		style.highlightedChildRegionColor = style.selectedChildRegionColor.cpy().lerp(Color.WHITE, .5f);
		style.circumferenceColor = Color.BROWN.cpy().lerp(Color.BLACK, .5f);
		style.circumferenceWidth = 5f;
		style.separatorColor = style.circumferenceColor;
		style.separatorWidth = style.circumferenceWidth;
		
		final PieMenu menu = new PieMenu(new ShapeDrawer(stage.getBatch(), skin.getRegion("white")), style, RADIUS);
		menu.setInfiniteSelectionRange(false);
		menu.setInnerRadius(30);
		menu.setTotalDegreesDrawn(halfCircle ? 300 : 360);
		menu.setStartDegreesOffset(halfCircle ? 300 : 0);
		
		for(String name : setting.values){
			menu.addActor(new Label(name, skin));
		}
		
		menu.setSelectedIndex(setting.value);
		
		menu.setPosition(stage.getWidth()/2 + distance * id, stage.getHeight()/2, Align.center);
		label.setPosition(stage.getWidth()/2 + distance * id, stage.getHeight()/2 + RADIUS + SPACE * 4, Align.center);
		
		menu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setting.value = menu.getSelectedIndex();
			}
		});
		
		stage.addActor(menu);
		return menu;
	}

}
