package net.mgsx.dl9.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class MeshCulling {

	private static final Vector3 position = new Vector3();
	private static final Vector3 translation = new Vector3();
	
	public static void apply(SceneManager sceneManager){
		for(Scene scene : sceneManager.getScenes()){
			scene.modelInstance.transform.getTranslation(translation);
			apply(sceneManager.camera, scene.modelInstance.nodes);
		}
	}

	private static void apply(Camera camera, Iterable<Node> nodes) {
		for(Node node : nodes){
			apply(camera, node);
		}
	}

	private static void apply(Camera camera, Node node) {
		float d = camera.far * .4f; // TODO wrong camera.far
		boolean inBound = node.globalTransform.getTranslation(position).add(translation).dst2(camera.position) < d*d;
		//boolean inBound = node.translation.dst(camera.position) < camera.far; // TODO wrong camera.far
		NodeUtils.enable(node, inBound);
		apply(camera, node.getChildren());
	}

}
