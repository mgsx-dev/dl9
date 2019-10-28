package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;

public class Log {
	public static void log(String text) {
		Gdx.app.log("Asset", text);
	}
	public static void log(String name, int value) {
		log(name + ": " + value);
	}
	public static void log(String name, long value) {
		log(name + ": " + value);
	}
	public static void log(String name, float value) {
		log(name + ": " + value);
	}
	public static void log(String name, String value) {
		log(name + ": " + value);
	}
}
