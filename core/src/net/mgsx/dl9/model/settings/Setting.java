package net.mgsx.dl9.model.settings;

public class Setting {
	public final String [] values;
	public int value;
	public Setting(int defaultValue, String...values) {
		super();
		this.values = values;
		this.value = defaultValue;
	}
}
