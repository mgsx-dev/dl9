package net.mgsx.dl9.utils;

import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import net.mgsx.dl9.model.settings.Settings;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class SceneUtils {

	public static DirectionalLight convertLight(SceneManager sceneManager,  DirectionalLight light, boolean enableShadow){
		if(!(light instanceof DirectionalShadowLight)){
			if(enableShadow){
				int size = Settings.i().getShadowSize();
				
				DirectionalLight newLight = new DirectionalShadowLight(size, size).set(light);
				sceneManager.environment.remove(light);
				sceneManager.environment.add(newLight);
				return newLight;
			}
		}else if(light instanceof DirectionalShadowLight){
			if(!enableShadow){
				((DirectionalShadowLight)light).dispose();
				DirectionalLight newLight = new DirectionalLightEx().set(light);
				sceneManager.environment.remove(light);
				sceneManager.environment.add(newLight);
				return newLight;
			}else{
				int size = Settings.i().getShadowSize();
				((DirectionalShadowLight)light).setShadowMapSize(size, size);
			}
		}
		
		return light;
	}
}
