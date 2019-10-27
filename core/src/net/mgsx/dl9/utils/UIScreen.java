package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIScreen extends StageScreen
{
	final protected Table root;

	final protected Skin skin;
	
	final protected Color bgColor = new Color(Color.BLACK);
	
	public UIScreen(Viewport viewport, Skin skin) {
		super(viewport);
		this.skin = skin;
		root = new Table(skin);
		root.setFillParent(true);
		stage.addActor(root);
	}
	
	@Override
	public void render(float delta) {
		bgColor.clamp();
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		super.render(delta);
	}
}
