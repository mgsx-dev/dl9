package net.mgsx.dl9.utils;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Array;

public class LightCulling {
	private final Array<PointLight> lights = new Array<PointLight>();
	private Camera camera;
	private Comparator<PointLight> comparator = new Comparator<PointLight>() {
		@Override
		public int compare(PointLight o1, PointLight o2) {
			return Float.compare(o1.position.dst2(camera.position), o2.position.dst2(camera.position));
		}
	};
	
	public void capture(Environment env){
		lights.clear();
		PointLightsAttribute pla = env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
		if(pla != null){
			lights.addAll(pla.lights);
		}
	}
	
	public void apply(Camera camera, Environment env, int maxLights){
		this.camera = camera;
		lights.sort(comparator);
		int count = 0;
		for(PointLight light : lights){
			PointLightsAttribute pla = env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
			if(pla != null){
				if(count >= maxLights){
					pla.lights.removeValue(light, true);
				}else if(!pla.lights.contains(light, true)){
					pla.lights.add(light);
					count++;
				}else{
					count++;
				}
			}
		}
		this.camera = null;
	}

	public boolean match(Environment environment, int maxLights) {
		PointLightsAttribute pla = environment.get(PointLightsAttribute.class, PointLightsAttribute.Type);
		if(pla == null){
			return maxLights == 0;
		}
		return pla.lights.size == maxLights;
	}
	
}
