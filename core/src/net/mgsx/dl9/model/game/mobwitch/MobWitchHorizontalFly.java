package net.mgsx.dl9.model.game.mobwitch;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.utils.NodeUtils;

public class MobWitchHorizontalFly extends MobWitchBase {
	
	private float t;
	
	@Override
	public void begin(GameLevel level) {
		level.witchScene.animationController.animate("Witch Balai", -1, null, 0);
		NodeUtils.enable(level.witchBalaiNode, true);
	}
	
	@Override
	public void end(GameLevel level) {
		setIdle(level);
		NodeUtils.enable(level.witchBalaiNode, false);
	}
	
	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		
		t += delta * .5f;
		if(t > 1) t = 1;
		
		witchPosition.set(mob.emitter.position).lerp(mob.emitter2.position, t);
		
		updateTransform(level, -90);
	}
	
	@Override
	public boolean isOver() {
		return t >= 1f;
	}
}
