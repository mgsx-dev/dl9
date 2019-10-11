package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SettingsUI extends Table{
	private SettingUI setOne;
	private SettingUI setTwo;
	private SettingUI setThree;

	public SettingsUI(Batch batch, Skin skin) {
		super(skin);
		
		setOne = new SettingUI(batch, "GPU", skin);
		setOne.setItems("Low", "Mid", "High");
		add(setOne);
		
		
		setTwo = new SettingUI(batch, "GPU", skin);
		setTwo.setItems("Low", "Mid", "High");
		add(setTwo);

		setThree = new SettingUI(batch, "GPU", skin);
		setThree.setItems("Low", "Mid", "High");
		add(setThree);

	}
	
}
