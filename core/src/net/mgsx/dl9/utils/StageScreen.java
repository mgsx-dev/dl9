package net.mgsx.dl9.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class StageScreen extends ScreenAdapter
{
	protected Stage stage;
	protected Viewport viewport;
	
	public StageScreen() 
	{
		this(new ScreenViewport());
	}
	public StageScreen(Viewport viewport) 
	{
		this.viewport = viewport;
		stage = new Stage(viewport);
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		stage.act();
		if(stage != null){ // prevent stage disposed during act (eg. screen changed when pressed on button)
			stage.draw();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		stage = null;
	}
}