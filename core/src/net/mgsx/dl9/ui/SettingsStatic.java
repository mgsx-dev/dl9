package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.payne.games.piemenu.PieMenu;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.events.GotoMenuEvent;
import net.mgsx.dl9.model.settings.Setting;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SettingsStatic {

	private static final float RADIUS = 150;
	private static final float SPACE = 30;
	
	private static Array<Actor> actors = new Array<Actor>();
	private static Label lastLabel;
	private static float fontScale;
	
	public static boolean create(final Stage stage, Skin skin, final boolean inGame){
		
		if(actors.size > 0){
			for(Actor a : actors){
				stage.getRoot().removeActor(a);
			}
			actors.clear();
			return false;
		}
		
		actors.clear();
		
		PieMenu pieMenu;
		
		fontScale = 1f;
		pieMenu = createMenu(stage, skin, 0, -2, DL9Game.i().settings.difficulty, false, "Difficulty");
		pieMenu.setX(pieMenu.getX() +  pieMenu.getRadius() );
		pieMenu.setRadius(pieMenu.getRadius() * 1.2f);
		pieMenu.setTotalDegreesDrawn(360);
		pieMenu.setStartDegreesOffset(270);
		fontScale = 1;
		
		// createMenu(stage, skin, 0, -1, DL9Game.i().settings.quality, false, "Quality");
		createMenu(stage, skin, 0, 0, DL9Game.i().settings.luminosity, true, "Luminosity");
		
		
		
		
		pieMenu = createMenu(stage, skin, 0, 1, DL9Game.i().settings.spookiness, false, "Spookiness");
		pieMenu.setX(pieMenu.getX() +  pieMenu.getRadius() );
		lastLabel.setX(lastLabel.getX() +  pieMenu.getRadius() );
		pieMenu.setTotalDegreesDrawn(180);
		pieMenu.setStartDegreesOffset(0);

		
		
		
		pieMenu = createMenu(stage, skin, 0, 2, DL9Game.i().settings.shadows, false, "Shadows");

		lastLabel.setX(lastLabel.getX() - 1.5f * pieMenu.getRadius() );
		lastLabel.setY(lastLabel.getY() - 3f * pieMenu.getRadius() );
		pieMenu.setX(pieMenu.getX() - 2 * pieMenu.getRadius() );
		pieMenu.setY(pieMenu.getY() - 4 * pieMenu.getRadius());
		pieMenu.setRadius(pieMenu.getRadius() * 1.5f);
		pieMenu.setTotalDegreesDrawn(36 * 9);
		pieMenu.setStartDegreesOffset(0);
		
		pieMenu = createMenu(stage, skin, 1, -2, DL9Game.i().settings.vsync, false, "VSYNC");
		pieMenu.setRadius(pieMenu.getRadius() * .8f);
		pieMenu = createMenu(stage, skin, 1, -1, DL9Game.i().settings.vsyncForced, false, "60 FPS");
		pieMenu.setRadius(pieMenu.getRadius() * .8f);
		
		pieMenu = createMenu(stage, skin, 1, 0, DL9Game.i().settings.pointLights, true, "Lights");
		pieMenu.setTotalDegreesDrawn(180);
		pieMenu.setStartDegreesOffset(0);
		
		
		
		
		TextButton bt = new TextButton(inGame ? "Close" : "Back", skin);
		bt.pack();
		bt.setPosition(stage.getWidth()/2,10, Align.bottom);
		stage.addActor(bt);
		actors.add(bt);
		
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(inGame){
					for(Actor a : actors){
						stage.getRoot().removeActor(a);
					}
					actors.clear();
					DL9Game.i().setGamePaused(false);
				}else{
					event.getStage().getRoot().fire(new GotoMenuEvent());
				}
			}
		});
		
		return true;
	}
	
	private static PieMenu createMenu(Stage stage, Skin skin, int row, int id, final Setting setting, boolean halfCircle, String title){
		
		float distance = RADIUS * 2 + SPACE;
		
		Label label = lastLabel = new Label(title, skin);
		
		stage.addActor(label);
		actors.add(label);
		
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
			Label l = new Label(name, skin);
			menu.addActor(l);
			l.setFontScale(fontScale);
		}
		
		menu.setSelectedIndex(setting.value);
		
		float hDistance = 450;
		float hOffset = stage.getHeight()/2 - row * hDistance - 300;
		
		
		menu.setPosition(stage.getWidth()/2 + distance * id, stage.getHeight()/2 + hOffset, Align.center);
		label.setPosition(stage.getWidth()/2 + distance * id, stage.getHeight()/2 + hOffset + RADIUS + SPACE * 4 - 50, Align.center);
		
		menu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setting.value = menu.getSelectedIndex();
				DL9Game.i().settings.apply();
				GameAudio.i.sfxButton();
			}
		});
		
		stage.addActor(menu);
		actors.add(menu);
		return menu;
	}

}
