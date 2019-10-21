package net.mgsx.dl9.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;

import net.mgsx.dl9.assets.GameAssets;

public class CursorManager {

	public static CursorManager i;
	
	private float time;
	private boolean blinking;
	private float blinkSpeed;
	private Cursor currentCursor;
	
	public void update(float delta){
		time += delta;
		if(blinking){
			if(((time * blinkSpeed) % 1f) > .6f){
				Gdx.graphics.setCursor(GameAssets.i.cursorWait);
			}else{
				Gdx.graphics.setCursor(currentCursor);
			}
		}
	}
	
	public void setIdle(){
		blinking = false;
		currentCursor = GameAssets.i.cursorIdle;
		Gdx.graphics.setCursor(currentCursor);
	}
	
	public void setReloadNeeded(){
		blinking = true;
		blinkSpeed = 3f;
		time = 0;
		currentCursor = GameAssets.i.cursorReload;
		Gdx.graphics.setCursor(currentCursor);
	}
	
	public void setReloading(){
		blinking = true;
		blinkSpeed = 6;
		time = 0;
		currentCursor = GameAssets.i.cursorShoot;
		Gdx.graphics.setCursor(currentCursor);
	}
	
	public void setLoaded(){
		blinking = false;
		currentCursor = GameAssets.i.cursorShoot;
		Gdx.graphics.setCursor(currentCursor);
	}
	
}
