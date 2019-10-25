package net.mgsx.dl9.model.game.phases;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;

public class Boss2Phase extends BaseActionPhase
{

	public Boss2Phase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicBoss2;
	}
	
}
