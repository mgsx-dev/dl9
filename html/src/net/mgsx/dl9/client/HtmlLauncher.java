package net.mgsx.dl9.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new DL9Game();
        }
        
        @Override
	    public void onModuleLoad () {
	        super.onModuleLoad();
	        com.google.gwt.user.client.Window.addResizeHandler(new ResizeHandler() {
	              public void onResize(ResizeEvent ev) {
	            	  final int width = ev.getWidth();
	            	  final int height = ev.getHeight();
	            	  getCanvasElement().setWidth(width);
	            	  getCanvasElement().setHeight(height);
	            	  
	            	  // TODO try with this to confirms there is no need for the post runnable trick
	            	  // Gdx.graphics.setWindowedMode(width, height);
	            	  // TODO see gwt fullscreen mode already implemented !
	            	  
	            	  Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							Gdx.app.getApplicationListener().resize(width, height);
						}
					});
	              }
	            });
	    }
}