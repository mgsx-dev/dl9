package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

// TODO not sure about this..
public class MobCloseAttack extends MobLogic
{

	@Override
	public void update(GameLevel level, GameMob mob, float delta) {
		level.mobShoot(mob);
		mob.logic = new MobInFront();
	}

}
