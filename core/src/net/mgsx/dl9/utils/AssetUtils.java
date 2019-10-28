package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.Node;

import net.mgsx.dl9.GameConfig;
import net.mgsx.gltf.data.geometry.GLTFMesh;
import net.mgsx.gltf.data.geometry.GLTFPrimitive;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class AssetUtils {
	public static void checkAsset(String tag, SceneAsset asset){
		if(GameConfig.DEBUG_MODELS){
			// find mesh without tangent
			for(GLTFMesh mesh : asset.data.meshes){
				for(GLTFPrimitive prim : mesh.primitives){
					if(prim.attributes.get("TANGENT") == null){
						Gdx.app.error("CHECK ASSET", "mesh " + String.valueOf(mesh.name) + " doesn't have tangent attribute");
					}
				}
			}
			
			// some stats
			Log.log("CHECK ASSET '" + tag + "'");
			Log.log("Animations", asset.animations.size);
			Log.log("Max Bones", asset.maxBones);
			Log.log("Cameras", asset.scene.cameras.size);
			Log.log("Lights", asset.scene.lights.size);
			Log.log("Root nodes", asset.scene.model.nodes.size);
			Log.log("All nodes", nbNodes(asset.scene.model.nodes));
			Log.log("Materials", asset.scene.model.materials.size);
			
		}
	}

	private static int nbNodes(Iterable<Node> nodes) {
		int n = 0;
		for(Node node : nodes){
			n += 1 + nbNodes(node.getChildren());
		}
		return n;
	}
}
