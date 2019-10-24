package net.mgsx.dl9.utils;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.utils.Array;

import net.mgsx.gltf.scene3d.animation.AnimationControllerHack;
import net.mgsx.gltf.scene3d.scene.Scene;

public class CustomAnimationsPlayer {

	private Scene scene;
	
	private Array<AnimationController> controllers = new Array<AnimationController>();

	public CustomAnimationsPlayer(Scene scene) {
		this.scene = scene;
	}
	
	public void addAnimations(Array<AnimationDesc> animations){
		for(AnimationDesc animation : animations){
			addAnimation(animation);
		}
	}
	public void addAnimation(AnimationDesc animation){
		AnimationControllerHack c = new AnimationControllerHack(scene.modelInstance);
		c.calculateTransforms = false;
		c.setAnimationDesc(animation);
		controllers.add(c);
	}
	public void clearAnimations(){
		controllers.clear();
	}
	
	public void playAll(){
		playAll(false);
	}
	public void loopAll(){
		playAll(true);
	}
	public void playAll(boolean loop){
		clearAnimations();
		for(int i=0, n=scene.modelInstance.animations.size ; i<n ; i++){
			AnimationControllerHack c = new AnimationControllerHack(scene.modelInstance);
			c.calculateTransforms = false;
			c.setAnimation(scene.modelInstance.animations.get(i), loop ? -1 : 1);
			controllers.add(c);
		}
	}
	
	public void stopAll(){
		clearAnimations();
	}
	
	public void update(float delta){
		if(controllers.size > 0){
			for(int i=0 ; i<controllers.size ; ){
				AnimationController c = controllers.get(i);
				c.update(delta);
				if(c.current.loopCount == 0){
					controllers.removeIndex(i);
				}else{
					i++;
				}
			}
			scene.modelInstance.calculateTransforms();
		}
	}

	public void play(String id) {
		AnimationControllerHack c = new AnimationControllerHack(scene.modelInstance);
		c.calculateTransforms = false;
		c.animate(id, 0);
		controllers.add(c);
	}

}
