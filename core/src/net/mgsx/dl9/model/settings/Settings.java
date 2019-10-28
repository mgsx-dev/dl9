package net.mgsx.dl9.model.settings;

import com.badlogic.gdx.Gdx;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;

public class Settings {
	
	public static Settings i(){
		 return DL9Game.i().settings;
	}
	
	public final Setting difficulty = new Setting(1, "easy", "mid", "hard");
	public final Setting luminosity = new Setting(2, "5", "4", "3", "2", "1");
	public final Setting spookiness = new Setting(0, "spooky");
	
	public final Setting vsync = new Setting(GameConfig.STARTUP_USE_VSYNC ? 0 : 1, "on", "off");
	public final Setting vsyncForced = new Setting(GameConfig.STARTUP_FORCE_VSYNC ? 0 : 1, "on", "off");
	
	
	public final Setting shadows = new Setting(GameConfig.DEV_MODE ? 0 : 6, "off", "6b", "7b", "8b", "9b", "1k", "2k", "4k", "8k");
	public final Setting pointLights = new Setting(GameConfig.DEV_MODE ? 2 : 10, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	
	
	
	public final float [] fogExps = {.1f, 0.07f, .05f, .03f, 0.02f};
	
	public void apply() {
		Gdx.graphics.setVSync(vsync.value == 0);
	}

	public boolean isVSyncForced() {
		return vsyncForced.value == 0;
	}

	public boolean isShadowEnabled(){
		return shadows.value != 0; 
	}

	public int getShadowSize() {
		return 32 * (1 << shadows.value);
	}
	
	
	public boolean checkQualty(PBRShaderConfig config){
		/*
		if(config.manualSRGB != SRGB.NONE && quality.value == 0){
			config.manualSRGB = SRGB.NONE;
			return true;
		}
		if(config.manualSRGB != SRGB.FAST && quality.value == 1){
			config.manualSRGB = SRGB.FAST;
			return true;
		}
		if(config.manualSRGB != SRGB.ACCURATE && quality.value == 2){
			config.manualSRGB = SRGB.ACCURATE;
			return true;
		}
		*/
		return false;
	}
	
}
