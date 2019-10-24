package net.mgsx.dl9.model.game.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.model.game.GameLevel;

// TODO remove this special controller hack...
public class SimpleCameraAnimator extends CameraAnimator {

	private GameLevel level;
	private final Vector3 camPos = new Vector3();
	private final Vector3 camPosPrev = new Vector3();
	private float time;
	private boolean enabled = false;
	private float smoothEnabled = 0;
	
	public SimpleCameraAnimator(GameLevel level) {
		this.level = level;
	}
	
	@Override
	public void enable() {
		enabled = true;
	}
	@Override
	public void disable() {
		enabled = false;
	}
	
	@Override
	public void update(float delta){
		
		if(enabled){
			smoothEnabled += delta * 1f;
			if(smoothEnabled > 1) smoothEnabled = 1;
		}else{
			smoothEnabled -= delta * 1f;
			if(smoothEnabled < 0) smoothEnabled = 0;
		}
		
		camPosPrev.set(camPos);
		float deltaPos = camPos.dst(camPosPrev);
		
		time +=  .1f * deltaPos / delta;
		
		Node node = level.nativeCameraNode;
		
		level.scene.getCamera("Camera_Orientation");
		
		node.globalTransform.getTranslation(camPos);
		
		float rotRange = 10 * smoothEnabled;
		
		float rotX = -(((float)Gdx.input.getX() / (float)Gdx.graphics.getWidth()) * 2 - 1) * rotRange;
		float rotY = -(((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight()) * 2 - 1) * rotRange;
		
		level.camera.direction.set(0,0,-1).rotate(Vector3.Y, rotX).rotate(Vector3.X, rotY).rot(node.globalTransform).nor();
		level.camera.up.set(Vector3.Y);
		
		level.camera.position.set(camPos);
		level.camera.position.mulAdd(Vector3.Y, MathUtils.sin(time) * .02f * smoothEnabled);
		
		level.camera.update();
	}
}
