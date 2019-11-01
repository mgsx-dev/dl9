package net.mgsx.dl9;

import java.awt.SplashScreen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfig.WINDOW_WIDTH;
		config.height = GameConfig.WINDOW_HEIGHT;
		config.fullscreen = GameConfig.DEFAULT_FULLSCREEN && GameConfig.ALLOW_FULLSCREEN;
		config.vSyncEnabled = GameConfig.STARTUP_USE_VSYNC;
		config.foregroundFPS = GameConfig.STARTUP_FORCE_VSYNC ? 60 : 0;
		config.pauseWhenBackground = config.pauseWhenMinimized = true;
		new LwjglApplication(new DL9Game(){
			@Override
			public void create() {
				SplashScreen splashScreen = SplashScreen.getSplashScreen();
				if(splashScreen != null){
					splashScreen.close();
				}
				super.create();
			}
			@Override
			public void render() {
				config.foregroundFPS = settings.isVSyncForced() ? 60 : 0;
				super.render();
			}
		}, config);
	}
}
