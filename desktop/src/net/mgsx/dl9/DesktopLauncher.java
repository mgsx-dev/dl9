package net.mgsx.dl9;

import java.awt.SplashScreen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.mgsx.dl9.DL9Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
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
