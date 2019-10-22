package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobs.MobPotiron;

public class TutoPhase extends BaseActionPhase {

	private Array<GameMob> mobs;

	public TutoPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		
		GameAudio.i.playMusic(GameAssets.i.musicCinematic2);
		
		// TODO spawn some pumkins at emitters
		Array<MobEmitter> emitters = level.mobManager.findEmitters("Empty.tuto");
		mobs = level.mobManager.spawnMobs(emitters, GameAssets.i.assetPotiron.scene.model.nodes.first());
		for(GameMob mob : mobs){
			mob.logic = new MobPotiron();
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
