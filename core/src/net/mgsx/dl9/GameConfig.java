package net.mgsx.dl9;

public class GameConfig {
	
	// MASTER CONST
	public static final boolean DEV_MODE = false;
	
	// Const.
	public static final String GAME_TITLE = "LENDIGASTEL";

	public static final int MAX_BONES = 22;

	// Runtime
	
	public static boolean DEBUG = DEV_MODE;
	public static boolean ALLOW_PAUSE = DEV_MODE;
	public static boolean DEBUG_NATIVE_CAMERA = false;
	public static boolean DEBUG_INVINCIBLE = false;
	public static boolean DEBUG_MODELS = DEV_MODE;
	public static boolean DEBUG_SETTINGS = true && ALLOW_PAUSE;
	
	// Debug (should be always false)
	public static boolean DEBUG_BOSS = false;
	public static boolean DEBUG_SKIP_INTRO = false;
	
	// Audio options
	public static boolean AUDIO_ENABLED = true;
	
	// Gameplay
	public static int MAX_BULLETS = 6;
	public static float RELOAD_DURATION = 1f;

	// PHASES
	public static int HOUSE_SPAWNS = 13;
	public static int CHURCH_SPAWNS = 13;
	
	// BOSS
	public static int BOSS_PHASE_1_LIFE = 10;
	public static int BOSS_PHASE_2_LIFE = 20;

	
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
	
	public static boolean MESH_CULLING = false;
	public static boolean LIGHT_CULLING = false;
	
	// LOGS
	public static boolean LOG_SHADERS = true;

	public static boolean LOG_CULLING = DEV_MODE;
	
	// Startup options (configurable later)
	public static final boolean STARTUP_USE_VSYNC = !DEV_MODE;
	public static final boolean STARTUP_FORCE_VSYNC = DEV_MODE;

	public static final float INTRO_LIGHT_POWER = .2f;
	public static final float INTRO_LIGHT_SPEED = .5f;

	public static final float CAMERA_WALK_HEIGHT = .02f;
}
