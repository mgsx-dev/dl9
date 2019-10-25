package net.mgsx.dl9;

public class GameConfig {
	
	// Const.
	public static final String GAME_TITLE = "LENDIGASTEL";

	public static final int MAX_BONES = 22;
	
	// Runtime
	
	public static boolean DEBUG = true;
	public static boolean ALLOW_PAUSE = DEBUG;
	public static boolean DEBUG_NATIVE_CAMERA = false;
	public static boolean DEBUG_INVINCIBLE = true;
	public static boolean DEBUG_MODELS = true;
	
	// Debug (should be always false)
	public static boolean DEBUG_BOSS = false;
	public static boolean DEBUG_SKIP_INTRO = true;
	
	// Audio options
	public static boolean AUDIO_ENABLED = true;
	
	// Gameplay
	public static int MAX_BULLETS = 6;
	public static float RELOAD_DURATION = 1f;
	public static int HERO_LIFE_MAX = 8;
	
	// Display
	public static float HUD_SCALE = 0.5f;
	public static float HUD_SHOW_HIDE_DURATION = .3f;
	public static float FULLFOG_SPEED = .5f;
	public static float DEFAULT_FADE_DURATION = .5f;
	
	
	// GPU options
	public static boolean GPU_FOG = true;
	public static boolean GPU_LIGHTS = true;
	
	// Fog settings
	public static float FOG_NEAR = .1f;
	public static float FOG_FAR = 20f;
	public static float FOG_EXP = 1f;
	
	// Options
	public static boolean ALLOW_FULLSCREEN = true;
	public static int WINDOW_WIDTH = 1024;
	public static int WINDOW_HEIGHT = 768;
	public static boolean DEFAULT_FULLSCREEN = false;
	public static boolean USE_VSYNC = false;
	public static boolean FORCE_VSYNC = true;
	
}
