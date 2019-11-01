package net.mgsx.dl9.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.payne.games.piemenu.PieMenu;
import com.payne.games.piemenu.PieMenu.PieMenuStyle;

public class SettingsPOC extends Group
{
	public SettingsPOC(Stage stage, Skin skin) 
	{
		setTransform(false);
		setSize(stage.getWidth(), stage.getHeight());
		
		PieMenuStyle style = PieMenuUtils.getStyle();
		PieMenu menu;
		Table table;
		
		float spaceX = 50;
		float offsetX = spaceX;
		
		// floatting case (OK)
		menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 100, .2f);
		fillMenu(menu, skin, "A", "B", "C");
		menu.pack();
		menu.setPosition(offsetX, stage.getHeight() - menu.getHeight());
		addActor(menu);
		offsetX += menu.getWidth() + spaceX;
		
		// floatting table case. (KO)
		table = new Table(skin);
		table.add("Title very long").row();
		menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 100, .2f);
		fillMenu(menu, skin, "A", "B", "C");
		table.add(menu);
		table.pack();
		table.setPosition(offsetX, stage.getHeight() - table.getHeight());
		addActor(table);
		offsetX += table.getWidth() + spaceX;
		
		// floatting table case 2. (KO)
		table = new Table(skin);
		menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 100, .2f);
		fillMenu(menu, skin, "A", "B", "C");
		table.add(menu).row();
		table.add("Title").row();
		table.pack();
		table.setPosition(offsetX, stage.getHeight() - table.getHeight());
		addActor(table);
		offsetX += table.getWidth() + spaceX;
		
		// floatting table with multiple. (KO)
		table = new Table(skin);
		for(int i=0 ; i<3 ; i++){
			menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 100, .2f);
			fillMenu(menu, skin, "A", "B", "C");
			table.add(menu);
		}
		table.pack();
		table.setPosition(offsetX, stage.getHeight() - table.getHeight());
		addActor(table);
		offsetX += table.getWidth() + spaceX;

		// floatting table case with grow. (KO)
		table = new Table(skin);
		menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 10, .2f);
		fillMenu(menu, skin, "A", "B", "C");
		table.add(menu).grow().row();
		table.setSize(400, 300);
		table.setPosition(0, stage.getHeight() - table.getHeight() - 300);
		addActor(table);
		
		// table case with fill parent. (OK)
		table = new Table(skin);
		menu = new PieMenu(stage.getBatch(), skin.getRegion("white"), style, 100, .2f);
		fillMenu(menu, skin, "A", "B", "C");
		table.add(menu).expand().right().bottom().row();
		table.setFillParent(true);
		addActor(table);
				
		debugAll();
		
	}

	private void fillMenu(PieMenu menu, Skin skin, String ...labels) {
		for(String label : labels){
			menu.addActor(new Label(label, skin));
		}
	}
}
