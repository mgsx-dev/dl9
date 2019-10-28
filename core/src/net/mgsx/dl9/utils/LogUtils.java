package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;

import net.mgsx.gltf.scene3d.scene.Scene;

public class LogUtils {
	private static long lastTime = 0;
	private static boolean timeToLog = false;
	private static int nbTotal;
	private static int nbEnabled;
	
	public static void logLights(Environment env, int total){
		PointLightsAttribute pla = env.get(PointLightsAttribute.class, PointLightsAttribute.Type);
		int n = pla == null ? 0 : pla.lights.size;
		Gdx.app.log("CULLING", "Enabled point lights: " + n + " / " + total);
	}
	
	public static void logNodes(Scene scene){
		nbEnabled = nbTotal = 0;
		collect(scene.modelInstance.nodes);
		Gdx.app.log("CULLING", "Enabled node parts: " + nbEnabled + " / " + nbTotal);
	}
	
	private static void collect(Iterable<Node> nodes){
		for(Node node : nodes){
			for(NodePart part : node.parts){
				if(part.enabled) nbEnabled++;
				nbTotal++;
			}
			collect(node.getChildren());
		}
	}

	public static boolean isTimeToLog() 
	{
		return timeToLog;
	}

	public static void update() {
		timeToLog = false;
		long time = System.currentTimeMillis();
		if(time - lastTime > 1000L){
			timeToLog = true;
			lastTime = time;
		}
	}
}
