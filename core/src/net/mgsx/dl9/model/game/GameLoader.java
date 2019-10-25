package net.mgsx.dl9.model.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.utils.CustomAnimationsPlayer;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GameLoader {

	public GameLevel load(SceneAsset asset){
		
		GameLevel gameLevel = new GameLevel();
		
		gameLevel.asset = asset;
		
		Scene scene = gameLevel.scene = new Scene(asset.scene);
		
		Array<Node> toRemove = new Array<Node>();
		
		for(Node node : scene.modelInstance.nodes){
			if(node.id.equals("mob full")){
				// XXX gameLevel.mobNode = node;
			}
			
			if(node.id.startsWith("Empty")){
				System.out.println("Empty: " + node.id);
				
				node.calculateTransforms(true);
				
				MobEmitter emitter = new MobEmitter(node);
				emitter.node.globalTransform.getTranslation(emitter.position);
				emitter.direction.set(Vector3.Y).rot(emitter.node.globalTransform).nor();
				emitter.up.set(Vector3.X).rot(emitter.node.globalTransform).nor();

				toRemove.add(node);
				
				gameLevel.emitters.add(emitter);
			}
		}
		
		// remove empties from scene
		scene.modelInstance.nodes.removeAll(toRemove, true);
		
		gameLevel.mobNode = GameAssets.i.mobModel.scene.model.nodes.first();
		
		gameLevel.witchScene = new Scene(GameAssets.i.witchModel.scene);
		
		scene.modelInstance.nodes.removeValue(gameLevel.mobNode, true);
		
		gameLevel.nativeCamera = scene.getCamera("Camera_Orientation");
		
		Node node = null;
		for(Entry<Node, Camera> e : gameLevel.scene.cameras){
			if(e.value == gameLevel.nativeCamera){
				node = e.key;
			}
		}
		gameLevel.nativeCameraNode = node;
		
		if(GameConfig.DEBUG_NATIVE_CAMERA){
			gameLevel.camera = gameLevel.nativeCamera;
		}else{
			gameLevel.camera = scene.createCamera(gameLevel.nativeCamera);
		}
		
		gameLevel.animations = new CustomAnimationsPlayer(scene);
		
		return gameLevel;
	}
}
