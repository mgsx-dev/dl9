package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;

import net.mgsx.dl9.GameConfig;
import net.mgsx.gltf.data.geometry.GLTFMesh;
import net.mgsx.gltf.data.geometry.GLTFPrimitive;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class AssetUtils {
	public static void checkAsset(SceneAsset asset){
		if(GameConfig.DEBUG_MODELS){
			// find mesh without tangent
			for(GLTFMesh mesh : asset.data.meshes){
				for(GLTFPrimitive prim : mesh.primitives){
					if(prim.attributes.get("TANGENT") == null){
						Gdx.app.error("CHECK ASSET", "mesh " + String.valueOf(mesh.name) + " doesn't have tangent attribute");
					}
				}
			}
		}
	}
}
