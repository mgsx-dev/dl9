package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public abstract class MobBase extends MobLogic {

	@Override
	public void onShooted(GameLevel level, GameMob mob) {
		super.onShooted(level, mob);
		mob.dead = true;
	}
}
