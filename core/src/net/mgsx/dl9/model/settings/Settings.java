package net.mgsx.dl9.model.settings;

public class Settings {
	public final Setting difficulty = new Setting(0, "Easy", "Normal", "Hard");
	public final Setting quality = new Setting(2, "Lq", "Mq", "Hq");
	public final Setting luminosity = new Setting(2, "5", "4", "3", "2", "1");
	public final Setting spookiness = new Setting(0, "Spooky", "Cool");
}
