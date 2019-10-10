package net.mgsx.dl9.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl9.utils.UIScreen;

public class IntroScreen extends UIScreen
{
	public IntroScreen(Skin skin) {
		super(new FitViewport(1024, 768), skin);
		
		float lum = 0f;
		bgColor.set(lum, lum, lum, 0);
		
		root.add("Helloween ;-)").expand().center();
		
		root.row();
		
		root.add(new TextButton("Play", skin)).expand().center();
	}
}
