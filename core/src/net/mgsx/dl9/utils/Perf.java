package net.mgsx.dl9.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import net.mgsx.dl9.GameConfig;

public class Perf {
	private static class Metric{
		public int index;
		public boolean started;
		public long startTime;
		public long duration;
		
	}
	private static ObjectMap<String, Metric> metrics = new ObjectMap<String, Metric>();
	private static Array<String> names = new Array<String>();
	
	
	public static int start(String name){
		if(GameConfig.DEBUG){
			Metric m = metrics.get(name);
			if(m != null){
				if(!m.started){
					m.started = true;
					m.startTime = getTime();
				}
				return m.index;
			}else{
				m = new Metric();
				m.index = names.size;
				names.add(name);
				metrics.put(name, m);
				m.started = true;
				m.startTime = getTime();
				return m.index;
			}
		}
		return -1;
	}

	public static void end(int index){
		if(GameConfig.DEBUG){
			Metric m = metrics.get(names.get(index));
			if(m.started){
				m.duration = getTime() - m.startTime;
				m.started = false;
			}
		}
	}
	public static void flush(int index) {
		if(GameConfig.DEBUG){
			String name = names.get(index);
			Metric m = metrics.get(name);
			m.duration = getTime() - m.startTime;
			log(name, m);
			m.started = false;
		}
	}

	private static void log(String name, Metric m) {
		if(m.duration < 2000L){
			Log.log(name, m.duration + "ms");
		}else{
			Log.log(name, (float)(m.duration / 1000L));
		}
	}

	public static void flush() {
		if(GameConfig.DEBUG){
			for(String name : names){
				Metric m = metrics.get(name);
				log(name, m);
			}
			names.clear();
			metrics.clear();
		}
	}
	
	private static long getTime() {
		return System.currentTimeMillis();
	}
	
}
