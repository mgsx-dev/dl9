package net.mgsx.dl9.vfx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraTrauma {

	private float time;
	private float traumaRate;
	private Vector3 cameraTan = new Vector3();

	public float recovery = 2f;
	public float frequency = 10f;
	public float pitch = 4f;
	public float yaw = 4f;
	public float roll = 4f;
	
	public void traumatize(){
		traumaRate += 1f;
	}
	
	public void update(Camera camera, float delta){
		time += delta;

		traumaRate = Math.min(1, MathUtils.lerp(traumaRate, 0, delta * recovery));
		float trauma = traumaRate * traumaRate;
		
		cameraTan.set(camera.direction).crs(camera.up).nor();
		camera.rotate(camera.up, MathUtils.sin(time * frequency) * yaw * trauma);
		camera.rotate(cameraTan, MathUtils.cos(time * frequency + 34.4f) * pitch * trauma);
		camera.rotate(camera.direction, MathUtils.cos(time * frequency + 12.4f) * roll * trauma);
	}
}
