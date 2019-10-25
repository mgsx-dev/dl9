package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobwitch.MobWitchShoot;
import net.mgsx.dl9.model.game.mobwitch.MobWitchVerticalHide;
import net.mgsx.dl9.model.game.mobwitch.MobWitchVerticalShow;
import net.mgsx.dl9.model.game.mobwitch.MobWitchWait;

public class Boss1Phase extends BaseActionPhase
{
	private GameMob mob;
	private Array<MobEmitter> emitBenches;

	public Boss1Phase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicBoss1;
		
		emitBenches = level.mobManager.findEmitters("Empty.witch.bench");
	}
	
	@Override
	public void started() {
		super.started();
		
		// TODO interpolate
		// Empty.witch.corridorFront
		// Empty.witch.corridorBack
		
		// TODO random
		// Empty.witch.bench.xxx
		
		// TODO make it running
		// Empty.witch.corridorCenter
		// Empty.witch.corridorLeft
		// Empty.witch.corridorRight

		// TODO for cam FX (blackout and appear)
		// Empty.witch.InFront
		
		// TODO interpolate
		// Empty.witch.hostelRight
		// Empty.witch.hostelLeft
		
		// TODO begin
		// Empty.witchHostelBack
		
		mob = level.mobManager.spawnWitch(null);
		
		spawnShowHide();
	}
	
	private void spawnShowHide() {
		// random bench emiter
		mob.emitter = emitBenches.random();
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchVerticalShow());
		mob.logicQueue.add(new MobWitchWait(.3f));
		mob.logicQueue.add(new MobWitchVerticalHide());
		mob.logicQueue.add(new MobWitchWait(.5f));
		mob.logic = mob.logicQueue.removeIndex(0);
	}
	
	private void spawnShowHideShoot() {
		// random bench emiter
		mob.emitter = emitBenches.random();
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchVerticalShow());
		mob.logicQueue.add(new MobWitchShoot());
		mob.logicQueue.add(new MobWitchVerticalHide());
		mob.logicQueue.add(new MobWitchWait(.5f));
		mob.logic = mob.logicQueue.removeIndex(0);
	}

	@Override
	public void update(float time, float delta) {
		if(mob.logic.isOver()){
			if(mob.logicQueue.size > 0){
				mob.logic = mob.logicQueue.removeIndex(0);
			}else{
				mob.shooted = false;
				if(MathUtils.randomBoolean()){
					spawnShowHide();
				}else{
					spawnShowHideShoot();
				}
			}
		}
		super.update(time, delta);
	}
	
}
