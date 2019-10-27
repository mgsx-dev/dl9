package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.graphics.g3d.model.Node;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.ui.AnimUtils;
import net.mgsx.gltf.scene3d.model.NodePartPlus;
import net.mgsx.gltf.scene3d.model.WeightVector;

public class MenuPhase extends BaseCinematicPhase {

	private Node sheetNode;
	private WeightVector shapeKeys;
	private float time;

	public MenuPhase(GameLevel level) {
		super(level);
		music = GameAssets.i.musicCinematic1;
		sheetNode = level.scene.modelInstance.getNode("IntroFlag");
		shapeKeys = ((NodePartPlus) sheetNode.parts.first()).morphTargets;
	}
	
	@Override
	public void started() {
		super.started();
		level.triggerMenu();
		level.atmoSFXEnabled = true;
		
		level.globalLightTarget = level.globalLight = 1;
	}
	
	@Override
	public void finished() {
		super.finished();
		level.cameraAnimator.enable();
	}
	
	@Override
	public void update(float gtime, float delta) {
		super.update(gtime, delta);
		
		time += delta;
		shapeKeys.values[0] = AnimUtils.usin(time * .1f) * 1f;
		shapeKeys.values[1] = AnimUtils.usin(time * .21f) * 1f;
	}
}
