package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.Color;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

public class PieMenuUtils {
	public static PieMenuStyle getStyle(){
		PieMenuStyle style = new PieMenuStyle();
		style.backgroundColor = Color.BROWN;
		style.selectedColor = Color.ORANGE;
		style.hoverColor = style.backgroundColor.cpy().lerp(style.selectedColor, .5f);
		style.hoverSelectedColor = style.hoverColor.cpy().lerp(style.selectedColor, .5f);
		// XXX style.highlightedChildRegionColor = style.selectedChildRegionColor.cpy().lerp(Color.WHITE, .5f);
		style.circumferenceColor = Color.BROWN.cpy().lerp(Color.BLACK, .5f);
		style.circumferenceWidth = 5f;
		style.separatorColor = style.circumferenceColor;
		style.separatorWidth = style.circumferenceWidth;
		return style;
	}
}
