package net.mgsx.dl9.model.game;

import com.badlogic.gdx.Gdx;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.ui.CursorManager;

public class InputLogic {
	private final GameLevel level;
	private boolean reloading;
	private float timeout;
	
	public InputLogic(GameLevel level) {
		super();
		this.level = level;
		addListeners();
	}

	private void addListeners() {
		level.listeners.add(new GameListener(){
			@Override
			public void onActionPhase() {
				// restore previous state if it was cinematic
				if(!reloading){
					if(level.bullets <= 0){
						CursorManager.i.setReloadNeeded();
					}else{
						CursorManager.i.setLoaded();
					}
				}
			}
			@Override
			public void onCinematicPhase() {
				reloading = false;
				level.reload(); // XXX reload auto 
				CursorManager.i.setIdle();
			}
		});
	}

	void update(float delta) {
		if(reloading){
			timeout -= delta;
			if(timeout <= 0){
				reloading = false;
				level.reload();
				CursorManager.i.setLoaded();
			}
		}
	}
	
	public void reload(){
		if(!reloading && level.bullets < GameConfig.MAX_BULLETS && level.actionPhase){
			reloading = true;
			timeout = GameConfig.RELOAD_DURATION;
			CursorManager.i.setReloading();
			GameAudio.i.playGunReload();
		}
	}
	
	public void shoot(){
		if(!reloading && level.bullets > 0 && level.actionPhase){
			level.shootAt(Gdx.input.getX(), Gdx.input.getY());
			GameAudio.i.playGunShot();
			if(level.bullets <= 0){
				CursorManager.i.setReloadNeeded();
			}
		}
	}

}
