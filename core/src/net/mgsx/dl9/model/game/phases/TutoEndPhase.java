package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.mobs.MobInFront;

public class TutoEndPhase extends BaseActionPhase {

	private GameMob mob;

	public TutoEndPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		
		GameAudio.i.playMusic(GameAssets.i.musicWitch);
		
		mob = level.mobManager.spawnMobInFront(Vector3.Y);
		mob.logic = new MobInFront();
	}
	
	@Override
	public boolean isFinished(float time) {
		return mob.shooted;
	}

}
