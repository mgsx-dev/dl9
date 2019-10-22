package net.mgsx.dl9;

import java.awt.SplashScreen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfig.WINDOW_WIDTH;
		config.height = GameConfig.WINDOW_HEIGHT;
		config.fullscreen = GameConfig.DEFAULT_FULLSCREEN && GameConfig.ALLOW_FULLSCREEN;
		config.vSyncEnabled = GameConfig.USE_VSYNC;
		config.foregroundFPS = GameConfig.FORCE_VSYNC ? 60 : 0;
		new LwjglApplication(new DL9Game(){
			@Override
			public void create() {
				SplashScreen splashScreen = SplashScreen.getSplashScreen();
				if(splashScreen != null){
					splashScreen.close();
				}
				super.create();
			}
		}, config);
	}
}
