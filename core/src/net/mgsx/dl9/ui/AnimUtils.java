package net.mgsx.dl9.ui;

import com.badlogic.gdx.math.MathUtils;

public class AnimUtils {

	public static float usin(float t){
		return MathUtils.sin(t * MathUtils.PI) * .5f + .5f;
	}
}
