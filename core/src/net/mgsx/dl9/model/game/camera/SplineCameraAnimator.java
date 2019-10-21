package net.mgsx.dl9.model.game.camera;

import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.model.game.GameLevel;

// XXX POC to remove ?
public class SplineCameraAnimator extends CameraAnimator {
	private GameLevel level;
	private final Vector3 camPos = new Vector3();
	private CatmullRomSpline<Vector3> spline;
	private final Vector3 derivative = new Vector3();
	private float t;
	private Array<NodeKeyframe<Quaternion>> rotations;
	private float duration;
	
	public SplineCameraAnimator(GameLevel level) {
		this.level = level;
		Vector3 [] points = null;
		Animation anim = level.scene.animationController.current.animation;
		duration = anim.duration;
		for(int i=0 ; i<anim.nodeAnimations.size ; i++){
			NodeAnimation nodeAnim = anim.nodeAnimations.get(i);
			points = new Vector3[nodeAnim.translation.size+2];
			if(nodeAnim.translation != null){
				for(int j=0 ; j<nodeAnim.translation.size ; j++){
					NodeKeyframe<Vector3> frame = nodeAnim.translation.get(j);
					Vector3 p = frame.value;
					points[j+1] = p;
				}
				points[0] = points[1];
				points[points.length-1] = points[points.length-2];
			}
			if(nodeAnim.rotation != null){
				rotations = nodeAnim.rotation;
			}
		}
		spline = new CatmullRomSpline<Vector3>(points, false);
	}
	
	private final static Quaternion q = new Quaternion();

	@Override
	public void update(float delta) {
		spline.derivativeAt(derivative, t);
		spline.valueAt(camPos, t);
		float d = derivative.len();
		if(d > 0) t += delta / derivative.len();
		else t += delta;
		if(t > 1) t -=1;
		level.camera.position.set(camPos);
		
		
		float rTime = t * rotations.size;
		int i = MathUtils.floor(rTime);
		float lTime = rTime % 1f;
		if(i > 0 && i < rotations.size){
			NodeKeyframe<Quaternion> r = rotations.get(i);
			q.set(r.value);
			if(i < rotations.size-1){
				NodeKeyframe<Quaternion> r2 = rotations.get(i+1);
				q.slerp(r2.value, lTime);
			}
		}
		//level.camera.direction.set(1,0,0).rotate(Vector3.Y, q.getYaw());
		level.camera.up.set(Vector3.Y);
		level.camera.update();
		
		
	}
}
