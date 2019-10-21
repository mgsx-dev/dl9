package net.mgsx.dl9.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;

import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameListener;

public class GameHUD extends Table
{
	private Table bulletTable;
	private EyeUI eye1, eye2;
	private GameLevel level;
	private Table main;
	
	public GameHUD(GameLevel level, Skin skin) {
		super(skin);
		this.level = level;
		
		bulletTable = new Table(skin);
		
		eye1 = new EyeUI(skin);
		eye2 = new EyeUI(skin);
		
		float s = GameConfig.HUD_SCALE;
		
		for(int i=0 ; i<GameConfig.MAX_BULLETS ; i++){
			Image bullet = new Image(skin, "bullet");
			bullet.setVisible(false);
			bulletTable.add(bullet).size(50 * s, 128 * s).pad(6);
		}
		
		main = new Table(skin);
		
		main.add(bulletTable);
		main.add(eye1).size(128 * s, 128 * s);
		main.add(eye2).size(128 * s, 128 * s);
		main.pack();
		
		// XXX workaround : layout and detach from table layouting for move animations
		setFillParent(true);
		add(main).expand().right().bottom();
		addActor(main);
		
		
		if(!level.actionPhase){
			setY(-main.getHeight());
		}
		
		addListeners();
	}
	
	private void addListeners() {
		level.listeners.add(new GameListener(){
			@Override
			public void onBulletChanged() {
				setBulletCount(level.bullets);
			}
			@Override
			public void onActionPhase() {
				addAction(Actions.moveTo(getX(), 0, GameConfig.HUD_SHOW_HIDE_DURATION));
			}
			@Override
			public void onCinematicPhase() {
				addAction(Actions.moveTo(getX(), -main.getHeight(), GameConfig.HUD_SHOW_HIDE_DURATION));
			}
		});
	}

	public void setBulletCount(int n){
		SnapshotArray<Actor> bullets = bulletTable.getChildren();
		for(int i=0 ; i<bullets.size ; i++){
			bullets.get(i).setVisible(bullets.size-1 - i < n);
		}
	}
	
	public void setLife(float life){
		eye1.setBalance(1 - life);
		eye2.setBalance(1 - life);
	}
}
