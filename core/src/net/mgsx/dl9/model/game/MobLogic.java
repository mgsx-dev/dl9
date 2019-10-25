package net.mgsx.dl9.model.game;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;

public abstract class MobLogic {

	public abstract void update(GameLevel level, GameMob mob, float delta);

	public void onShooted(GameLevel level, GameMob mob) {
		GameAudio.i.sfxZombiDead(level.camera.position.dst(mob.position));
	}
	
	public void shooting(GameLevel level, GameMob mob){
		GameAssets.i.soundSetLaser.sounds.get(1).play();
	}

	public boolean isOver() {
		return false;
	}
}
