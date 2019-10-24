package net.mgsx.dl9.model.game;

import com.badlogic.gdx.math.Vector3;

public class GameListener {

	public void onBulletChanged(){}

	public void onShoot(){}
	
	public void onReload(){}
	
	public void onReloaded(){}
	
	public void onActionPhase(){}
	
	public void onCinematicPhase() {}
	
	public void onMobShoot(Vector3 position) {}

	public void onHeroDead() {}

	public void onHeroWin() {}

	public void onMenuShow() {}
}
