package net.mgsx.dl9.model.game;

import net.mgsx.dl9.assets.GameAssets;

public abstract class MobLogic {

	public abstract void update(GameLevel level, GameMob mob, float delta);

	public void onShooted(GameLevel level, GameMob mob) {
		GameAssets.i.soundSetMobShooted.sounds.get(1).play();
	}
	
	public void shooting(GameLevel level, GameMob mob){
		GameAssets.i.soundSetLaser.sounds.get(1).play();
	}

	public boolean isOver() {
		return false;
	}
}
