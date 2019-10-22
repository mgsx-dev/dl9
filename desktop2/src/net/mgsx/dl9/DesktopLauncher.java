package net.mgsx.dl9;

import java.awt.SplashScreen;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	
	public static boolean isWindows(){
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	public static void closeSplash(){
		SplashScreen splashScreen = SplashScreen.getSplashScreen();
		if(splashScreen != null){
			splashScreen.close();
		}
	}
	
	public static void main (String[] arg) {
		
		if(!isWindows()){
			closeSplash();
		}
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		if(GameConfig.DEFAULT_FULLSCREEN && GameConfig.ALLOW_FULLSCREEN){
			DisplayMode mode = Lwjgl3ApplicationConfiguration.getDisplayMode();
			config.setFullscreenMode(mode);
		}else{
			config.setWindowedMode(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
		}
		config.useVsync(GameConfig.USE_VSYNC);
		new Lwjgl3Application(new DL9Game(){
			private Sync sync = new Sync();
			@Override
			public void create() {
				if(isWindows()){
					closeSplash();
				}
				super.create();
			}
			@Override
			public void render() {
				super.render();
				if(GameConfig.FORCE_VSYNC) sync.sync(60);
			}
		}, config);
	}
}
