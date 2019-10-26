package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.mobwitch.MobWitchBase;
import net.mgsx.dl9.model.game.mobwitch.MobWitchHorizontalFly;
import net.mgsx.dl9.model.game.mobwitch.MobWitchHorizontalRun;
import net.mgsx.dl9.model.game.mobwitch.MobWitchShoot;
import net.mgsx.dl9.model.game.mobwitch.MobWitchShootInFront;
import net.mgsx.dl9.model.game.mobwitch.MobWitchVerticalHide;
import net.mgsx.dl9.model.game.mobwitch.MobWitchVerticalShow;
import net.mgsx.dl9.model.game.mobwitch.MobWitchWait;

public abstract class BossActionPhase extends BaseActionPhase
{
	protected GameMob mob;
	
	protected int nbShowHide, nbShooted, nbSpawnFront;

	public BossActionPhase(GameLevel level) {
		super(level);
	}
	
	@Override
	public void started() {
		super.started();
		
		mob = level.mobManager.spawnWitch(null);
		
		MobWitchBase.setIdle(level);
		
		next();
	}
	
	@Override
	public void finished() {
		super.finished();
		level.mobManager.removeWitchMob(mob);
	}
	
	abstract protected void next();
	
	protected void spawnRun() {
		mob.emitter = level.witchCommon.emit_corridorLeft;
		mob.emitter2 = level.witchCommon.emit_corridorRight;
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchHorizontalRun());
		mob.logicQueue.add(new MobWitchWait(2f));
		nextQueue();
	}
	
	protected void spawnFly() {
		mob.emitter = level.witchCommon.emit_corridorRight;
		mob.emitter2 = level.witchCommon.emit_corridorLeft;
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchHorizontalFly());
		mob.logicQueue.add(new MobWitchWait(2f));
		nextQueue();
	}

	protected void spawnFront() {
		mob.emitter = level.witchCommon.emit_InFront;
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchVerticalShow());
		mob.logicQueue.add(new MobWitchShootInFront());
		mob.logicQueue.add(new MobWitchVerticalHide());
		mob.logicQueue.add(new MobWitchWait(2f));
		nextQueue();
		
		nbSpawnFront++;
	}

	protected void spawnShowHide() {
		// random bench emiter
		mob.emitter = level.witchCommon.emit_benches.random();
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchVerticalShow());
		mob.logicQueue.add(new MobWitchWait(.3f));
		mob.logicQueue.add(new MobWitchVerticalHide());
		mob.logicQueue.add(new MobWitchWait(.5f));
		nextQueue();
		
		nbShowHide++;
	}
	
	protected void spawnShowHideShoot() {
		// random bench emiter
		mob.emitter = level.witchCommon.emit_benches.random();
		mob.position.set(mob.emitter.position);
		
		mob.logicQueue.add(new MobWitchVerticalShow());
		mob.logicQueue.add(new MobWitchShoot());
		mob.logicQueue.add(new MobWitchVerticalHide());
		mob.logicQueue.add(new MobWitchWait(.5f));
		
		nextQueue();
		
		nbShowHide++;
	}

	protected void nextQueue() {
		mob.logic = mob.logicQueue.removeIndex(0);
		((MobWitchBase)mob.logic).begin(level);
	}

	@Override
	public void update(float time, float delta) {
		if(mob.logic.isOver()){
			((MobWitchBase)mob.logic).end(level);
			if(mob.logicQueue.size > 0){
				nextQueue();
			}else{
				if(mob.shooted == false){
					GameAssets.i.soundSetWitchLaugh.sounds.random().play();
				}else{
					nbShooted++;
				}
				
				next();
			}
		}
		super.update(time, delta);
	}
	
}
