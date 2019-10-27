package net.mgsx.dl9.model.game.mobs;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobLogic;

public class MobPotiron extends MobLogic
{

	@Override
	public void update(GameLevel level, GameMob mob, float delta) {

		mob.node.translation.set(mob.position).mulAdd(mob.direction, 0);
		
		lootAtCamPumkin(level, mob);
		
		mob.node.calculateLocalTransform();
		mob.node.calculateWorldTransform();
	}
	
	@Override
	public void onShooted(GameLevel level, GameMob mob) {
		GameAssets.i.soundSetPumpkin.sounds.random().play();
		mob.dead = true;
		// no sounds
	}
}
