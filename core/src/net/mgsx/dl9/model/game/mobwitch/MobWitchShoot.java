package net.mgsx.dl9.model.game.mobwitch;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobWitchShoot extends MobWitchBase {
	
	private float t;
	private float timeout;
	private boolean hasShoot = false;
	
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
	}
	
	@Override
	public boolean isOver() {
		return timeout >= 1f;
	}
}
