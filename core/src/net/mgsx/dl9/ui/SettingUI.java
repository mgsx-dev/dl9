package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.payne.games.piemenu.PieMenu;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SettingUI extends Table
{
	private PieMenu menu;

	public SettingUI(Batch batch, String title, Skin skin) {
		super(skin);
		add(title).expandX().row();
		
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
		
		menu = new PieMenu(new ShapeDrawer(batch, skin.getRegion("white")), style, 150);
		menu.setInfiniteSelectionRange(false);
		menu.setInnerRadius(30);
		
		add(menu).expandX().center().row();
	}

	public void setItems(String ...items ) {
		for(String item : items){
			menu.addActor(new Label(item, getSkin()));
		}
	}
	
}
