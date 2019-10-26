package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class Boss1Phase extends BossActionPhase
{
	public Boss1Phase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicBoss1;
	}
	
	@Override
	public void started() {
		super.started();
		level.globalLightTarget = 1;
	}
	
	@Override
	protected void next()
	{
		mob.shooted = false;
		
		if(nbSpawnFront >= 1){
			nbSpawnFront = 0;
			spawnRun();
		}
		else if(nbShowHide >= 6){
			nbShowHide = 0;
			spawnFront();
		}
		else{
			if(MathUtils.randomBoolean()){
				spawnShowHide();
			}else{
				spawnShowHideShoot();
			}
		}
	}


	@Override
	public boolean isFinished(float time) {
		return nbShooted >= GameConfig.BOSS_PHASE_1_LIFE;
	}
	
}
