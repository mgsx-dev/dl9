package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobs.MobDefault;

public class TutoPhase extends BaseActionPhase {

	private Array<GameMob> mobs;

	public TutoPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		// TODO spawn some pumkins at emitters
		Array<MobEmitter> emitters = level.mobManager.findEmitters("Empty.tuto");
		mobs = level.mobManager.spawnMobs(emitters);
		for(GameMob mob : mobs){
			mob.logic = new MobDefault();
		}
	}
	
	@Override
	public boolean isFinished(float time) {
		// TODO check how many pumpins shooted
		int count = 0;
		for(GameMob mob : mobs){
			if(mob.shooted){
				count++;
			}
		}
		if(count >= 6) return true;
		return false;
	}

}
