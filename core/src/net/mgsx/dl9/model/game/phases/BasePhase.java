package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.Node;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.core.SceneSequence;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.utils.NodeUtils;

public abstract class BasePhase extends SceneSequence
{
	protected final GameLevel level;
	
	protected String cameraAnimID = null;
	
	protected Music music;
	
	public BasePhase(GameLevel level) {
		super();
		this.level = level;
	}
	
	protected void enableMeshes(String...names){
		if(GameConfig.MANUAL_MESH_CULLING){
			for(String name : names){
				Node node = level.scene.modelInstance.getNode(name);
				NodeUtils.enable(node, true);
			}
		}
	}
	protected void disableMeshes(String...names){
		if(GameConfig.MANUAL_MESH_CULLING){
			for(String name : names){
				Node node = level.scene.modelInstance.getNode(name);
				NodeUtils.enable(node, false);
			}
		}
	}
	protected void enableLights(String...names){
		if(GameConfig.MANUAL_LIGHT_CULLING){
			PointLightsAttribute pla = level.env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
			for(String name : names){
				PointLight light = level.lights.get(name);
				if(pla == null || !pla.lights.contains(light, true)){
					level.env.add(light);
				}
			}
		}
	}
	protected void disableLights(String...names){
		if(GameConfig.MANUAL_LIGHT_CULLING){
			PointLightsAttribute pla = level.env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
			if(pla != null){
				for(String name : names){
					PointLight light = level.lights.get(name);
					level.env.remove(light);
				}
			}
		}
	}

	@Override
	public void init(float time) {
		
	}
	
	@Override
	public void started() {
		if(cameraAnimID != null){
			level.scene.animationController.animate(cameraAnimID, 0);
		}
		if(music != null){
			GameAudio.i.playMusic(music);
		}
	}
	
	@Override
	public void finished() {
		// XXX for debug sequences
		if(level.scene.animationController.current != null){
			level.scene.animationController.current.time = level.scene.animationController.current.duration;
		}
	}
	
	@Override
	public boolean isStarted(float time) {
		return true;
	}
	
	@Override
	public boolean isFinished(float time) {
		if(cameraAnimID != null){
			return level.scene.animationController.current == null || level.scene.animationController.current.loopCount == 0;
		}
		return false;
	}
}
