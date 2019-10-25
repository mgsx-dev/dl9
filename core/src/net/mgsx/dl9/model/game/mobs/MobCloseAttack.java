package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;

// TODO not sure about this..
public class MobCloseAttack extends MobBase
{

	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		level.mobShoot(mob);
		mob.logic = new MobInFront();
	}

}
