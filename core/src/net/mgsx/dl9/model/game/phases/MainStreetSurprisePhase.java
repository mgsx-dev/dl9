package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.mobs.MobInFront;

public class MainStreetSurprisePhase extends BaseActionPhase {

	private GameMob mob;

	public MainStreetSurprisePhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		
		mob = level.mobManager.spawnMobInFront(Vector3.Y);
		mob.logic = new MobInFront();
	}
	
	@Override
	public boolean isFinished(float time) {
		return mob.shooted;
	}

}
