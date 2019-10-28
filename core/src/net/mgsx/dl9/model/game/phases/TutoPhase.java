package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobs.MobPotiron;
import net.mgsx.dl9.ui.GUIUtils;

public class TutoPhase extends BaseActionPhase {

	private Array<GameMob> mobs;

	public TutoPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematic2;
	}
	
	@Override
	public void started() {
		super.started();
		
		disableMeshes("IntroFlag");
		
		disableLights("light.intro.1");

		
		GUIUtils.queueFade(level.stage, 0f, .5f, 3f, .5f, 1f,
				GUIUtils.message("Let's try with some Halloween heads")
				);

		
		// TODO spawn some pumkins at emitters
		Array<MobEmitter> emitters = level.mobManager.findEmitters("Empty.tuto");
		mobs = level.mobManager.spawnMobs(emitters, GameAssets.i.assetPotiron.scene.model.nodes.first());
		for(GameMob mob : mobs){
			mob.logic = new MobPotiron();
		}
	}
	
	@Override
	public void finished() {
		for(GameMob mob : mobs){
			level.mobManager.removeMob(mob);
		}
		super.finished();
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
