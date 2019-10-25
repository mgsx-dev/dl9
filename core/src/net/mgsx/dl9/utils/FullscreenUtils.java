package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;

import net.mgsx.dl9.GameConfig;

public class FullscreenUtils {

	public static void toggle(){
		if(Gdx.graphics.isFullscreen()){
			Gdx.graphics.setWindowedMode(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
		}else{
			DisplayMode displayMode = Gdx.graphics.getDisplayMode();
			Gdx.graphics.setFullscreenMode(displayMode);
		}
	}
	
	public static boolean isUserToggle(){
		if(GameConfig.ALLOW_FULLSCREEN){
			boolean alt = Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT);
			boolean altEnter = alt && Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
			boolean f11 = Gdx.input.isKeyJustPressed(Input.Keys.F11);
			boolean toggleFullscreen = altEnter || f11;
			return toggleFullscreen;
		}
		return false;
	}
}
