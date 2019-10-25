package net.mgsx.dl9.model.game;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameMob {

	public final Node node;
	public final Vector3 position = new Vector3();
	public final Vector3 direction = new Vector3();
	public final Vector3 up = new Vector3();
	
	public MobLogic logic;
	
	public final Array<MobLogic> logicQueue = new Array<MobLogic>();
	
	/** emitter from which mob as been spawn (may be null in case of free spawn) */
	public MobEmitter emitter;
	
	/** if dead (still on screen) */
	public boolean shooted;
	public boolean dead;
	
	public GameMob(Node node) {
		this.node = node;
	}
	
	public void update(GameLevel level, float delta){
		if(logic != null){
			logic.update(level, this, delta);
		}
	}

}
