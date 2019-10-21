package net.mgsx.dl9;

public class GameConfig {
	// Runtime
	
	public static final boolean DEBUG = true;
	public static final boolean ALLOW_PAUSE = DEBUG;
	public static final boolean DEBUG_NATIVE_CAMERA = false;
	
	// Gameplay
	public static final int MAX_BULLETS = 6;
	public static final float RELOAD_DURATION = 1f;
	public static final int HERO_LIFE_MAX = 8;
	
	// Display
	public static final float HUD_SCALE = 0.5f;
	public static final float HUD_SHOW_HIDE_DURATION = .3f;
	public static final float FULLFOG_SPEED = .5f;
	public static final float DEFAULT_FADE_DURATION = .5f;
	
	
	// GPU options
	public static final boolean GPU_FOG = true;
	public static final boolean GPU_NO_LIGHTS = true;
	
	// Fog settings
	public static final float FOG_NEAR = .1f;
	public static final float FOG_FAR = 20f;
	public static final float FOG_EXP = 1f;
	
}
