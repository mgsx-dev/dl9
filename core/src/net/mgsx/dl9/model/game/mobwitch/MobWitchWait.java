package net.mgsx.dl9.model.game.mobwitch;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobWitchWait extends MobWitchBase {
	
	private float t;
	
	public MobWitchWait(float duration) {
		this.t = duration;
	}
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		t -= delta;
	}
	
	@Override
	public boolean isOver() {
		return t <= 0;
	}
}
