package net.mgsx.dl9.audio;

import com.badlogic.gdx.math.MathUtils;

public class RandomRange {
	public float min, max;

	public RandomRange(float min, float max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public float get(){
		return MathUtils.random(min, max);
	}

	public RandomRange set(float min, float max) {
		this.min = min;
		this.max = max;
		return this;
	}
	
}
