package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import net.mgsx.dl9.GameConfig;

public class ShaderUtils {

	public static void log(String name, ShaderProgram program){
		if(!program.isCompiled()) throw new GdxRuntimeException(name + ": " + program.getLog());
		if(GameConfig.LOG_SHADERS){
			String log = program.getLog();
			if(!log.isEmpty()){
				Gdx.app.error(GameConfig.GAME_TITLE, name + ": " + log);
			}else{
				Gdx.app.log(GameConfig.GAME_TITLE, name + " compilation success");
			}
		}
	}
}
