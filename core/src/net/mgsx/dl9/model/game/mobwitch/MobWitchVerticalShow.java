package net.mgsx.dl9.model.game.mobwitch;

import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

public class MobWitchVerticalShow extends MobWitchBase {
	
	private float t;
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		t += delta * 5f;
		if(t > 1) t = 1;
		
		witchPosition.set(mob.position).mulAdd(Vector3.Y,(1 - t) * -2f);
		
		updateTransform(level);
	}
	
	@Override
	public boolean isOver() {
		return t >= 1f;
	}
}
