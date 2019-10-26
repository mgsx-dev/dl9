package net.mgsx.dl9.model.game.mobwitch;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobWitchShootInFront extends MobWitchBase {
	
	private float t;
	private float timeout;
	private boolean hasShoot = false;
	
	@Override
	public void begin(GameLevel level) {
		super.begin(level);
		GameAssets.i.sfxDeath.play();
	}
	@Override
	public void end(GameLevel level) {
		super.end(level);
		
		level.witchShapeKeys.values[0] = 0;
		level.witchShapeKeys.values[1] = 0;
		
	}
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		if(mob.shooted) timeout = 1;
		
		t += delta * .5f;
		if(t > 1){
			if(!mob.shooted && !hasShoot){
				level.mobShoot(mob);
				hasShoot = true;
			}
			
			timeout += delta;
		}
		
		level.witchShapeKeys.values[0] = (MathUtils.sin(t * 125) * .5f + .5f);
		level.witchShapeKeys.values[1] = (MathUtils.sin(t * 10) * .5f + .5f);

	}
	
	@Override
	public boolean isOver() {
		return timeout >= 1f;
	}
}
