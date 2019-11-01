package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.payne.games.piemenu.PieMenu;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.model.settings.Setting;

public class SettingUI extends Table
{
	private PieMenu menu;

	public SettingUI(Batch batch, String title, final Setting setting, float fontScale, Skin skin, PieMenuStyle style, float radius) {
		super(skin);
		add(title).expandX().row();
		
		menu = new PieMenu(batch, skin.getRegion("white"), style, 150);
		menu.setInfiniteSelectionRange(false);
		menu.setMinRadius(radius);
		
		for(String name : setting.values){
			Label l = new Label(name, skin);
			menu.addActor(l);
			l.setFontScale(fontScale);
		}
		
		menu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setting.value = menu.getSelectedIndex();
				DL9Game.i().settings.apply();
				GameAudio.i.sfxButton();
			}
		});
		
		menu.setSelectedIndex(setting.value);
		
		add(menu).expandX().center().row();
	}

	public void setItems(String ...items ) {
		for(String item : items){
			menu.addActor(new Label(item, getSkin()));
		}
	}
	
}
