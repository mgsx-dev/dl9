package net.mgsx.dl9.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class EyeUI extends Group
{
	private TextureRegion white;
	private TextureRegion red;
	private float balance;

	public EyeUI(Skin skin) {
		setTransform(false);
		
		white = skin.getRegion("eye-white");
		red = skin.getRegion("eye-red");
	}
	
	public void setBalance(float balance){
		this.balance = MathUtils.clamp(balance, 0, 1);
	}
	
	@Override
	protected void drawChildren(Batch batch, float parentAlpha) {
		batch.setColor(1, 1, 1, parentAlpha);
		batch.draw(white, getX(), getY(), getWidth(), getHeight());
		TextureRegion region = red;
		batch.draw(region.getTexture(), 
				getX(), getY(), 
				getWidth(), getHeight() * balance,
				region.getRegionX(), region.getRegionY() + (int)(region.getRegionHeight() * (1 - balance)), 
				region.getRegionWidth(), (int)(region.getRegionHeight() * balance),
				false, false);
	}
}
