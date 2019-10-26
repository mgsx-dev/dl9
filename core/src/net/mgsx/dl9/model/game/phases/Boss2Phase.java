package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class Boss2Phase  extends BossActionPhase
{
	public Boss2Phase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicBoss2;
	}
	
	@Override
	public void started() {
		super.started();
	}
	
	@Override
	protected void next()
	{
		mob.shooted = false;
		
		if(nbSpawnFront >= 1){
			nbSpawnFront = 0;
			spawnFly();
			level.globalLightTarget = 1;
		}
		else if(nbShowHide >= 6){
			nbShowHide = 0;
			spawnFront();
			level.globalLightTarget = 1;
		}
		else{
			if(MathUtils.randomBoolean()){
				spawnShowHide();
			}else{
				spawnShowHideShoot();
			}
			level.globalLightTarget = 0;
		}
	}
	
	@Override
	public boolean isFinished(float time) {
		return nbShooted >= GameConfig.BOSS_PHASE_2_LIFE;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.globalLightTarget = 1;
	}

}