package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLErrorListener;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

public class Stats {
	private static final String TAG = "STATS";
	
	public static enum Mode{
		SILENT, LOG, RAISE
	}
	
	private final GLProfiler profiler;
	private float time;
	private int frames;
	private boolean enabled;
	private Mode mode;

	private static final GLErrorListener SILENT_LISTENER = new GLErrorListener() {
		@Override
		public void onError(int error) {
		}
	};
	
	public Stats() {
		profiler = new GLProfiler(Gdx.graphics);
		setMode(Mode.LOG);
	}
	
	public void setMode(Mode mode){
		this.mode = mode;
		switch (mode) {
		case LOG:
			profiler.setListener(GLErrorListener.LOGGING_LISTENER);
			break;
		case RAISE:
			profiler.setListener(GLErrorListener.THROWING_LISTENER);
			break;
		case SILENT:
			profiler.setListener(SILENT_LISTENER);
			break;
		default:
			break;
		}
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void enable(boolean enabled){
		this.enabled = enabled;
		if(enabled){
			profiler.enable();
			frames = 0;
			time = 0;
		}
		else{
			profiler.disable();
		}
	}
	
	public void update(){
		if(enabled){
			frames++;
			time += Gdx.graphics.getDeltaTime();
			if(time > 1){
				time = 0;
				log();
				profiler.reset();
				frames = 0;
			}
		}
	}

	private void log() {
		log("fps", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		logPerFrame("calls", profiler.getCalls());
		logPerFrame("draw calls", profiler.getDrawCalls());
		logPerFrame("shader switches", profiler.getShaderSwitches());
		logPerFrame("texture bindings", profiler.getTextureBindings());
		logPerFrame("vertex count", profiler.getVertexCount().count);
		logPerFrame("vertex count max", profiler.getVertexCount().max);
	}

	private void logPerFrame(String label, int value) {
		logPerFrame(label, (float)value);
	}
	private void logPerFrame(String label, float value) {
		log(label, String.valueOf(value / (float)frames));
	}

	private void log(String label, String value) {
		Gdx.app.log(TAG, label + ": " + value);
	}

	public boolean isEnabled() {
		return enabled;
	}
}
