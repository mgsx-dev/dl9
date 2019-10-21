package net.mgsx.dl9.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import net.mgsx.dl9.GameConfig;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GameLoader {

	private Node loadMob(){
		SceneAsset asset = new GLTFLoader().load(Gdx.files.internal("models/mob/mob.gltf"), true);
		return asset.scene.model.nodes.first();
	}
	
	public GameLevel load(FileHandle file){
		
		GameLevel gameLevel = new GameLevel();
		
		SceneAsset asset = new GLTFLoader().load(file, true);
		
		gameLevel.asset = asset;
		
		Scene scene = gameLevel.scene = new Scene(asset.scene);
		
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

				
				gameLevel.emitters.add(emitter);
			}
		}
		
		gameLevel.mobNode = loadMob();
		
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
		
		return gameLevel;
	}
}
