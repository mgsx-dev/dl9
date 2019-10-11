package net.mgsx.dl9.model.settings;

public class Settings {
	public final Setting quality = new Setting(2, "Lq", "Mq", "Hq");
	public final Setting luminosity = new Setting(3, "5", "4", "3", "2", "1");
	public final Setting spookiness = new Setting(0, "Spooky", "Cool");
}
