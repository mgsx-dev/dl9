package net.mgsx.dl9.model.game;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;

public class MobEmitter {

	final Node node;
	public final Vector3 position = new Vector3();
	public final Vector3 direction = new Vector3();
	public final Vector3 up = new Vector3();
	
	public GameMob mob;

	public MobEmitter(Node node) {
		this.node = node;
	}

}
