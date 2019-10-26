package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobs.MobHorizontal;

public class MainStreetActionPhase extends BaseActionPhase {

	private Array<MobEmitter> leftEmit;
	private Array<MobEmitter> rightEmit;
	private Array<MobEmitter> allEmits;
	private Array<GameMob> mobs = new Array<GameMob>();

	public MainStreetActionPhase(GameLevel level) {
		super(level);
		cameraAnimID = "Camera main street action";
		music = GameAssets.i.musicCinematic2;
		
		// TODO preapre spaws
		
		leftEmit = level.mobManager.findEmitters("Empty.left.street");
		rightEmit = level.mobManager.findEmitters("Empty.right.street");
		allEmits = new Array<MobEmitter>();
		allEmits.addAll(leftEmit);
		allEmits.addAll(rightEmit);
	}
	
	@Override
	public void started() {
		super.started();
		level.animations.play("main street window L");
		level.animations.play("main street window R");
		level.animations.play("main street window battant");
	}
	
	@Override
	public void finished() {
		super.finished();
		level.mobManager.clear();
	}
	
	@Override
	public void update(float time, float delta) {
		super.update(time, delta);
		
		for(MobEmitter left : leftEmit){
			if(left.mob == null && isCloseEnough(left)){
				spawnLeft(left);
			}
		}
		
		for(MobEmitter right : rightEmit){
			if(right.mob == null && isCloseEnough(right)){
				spawnRight(right);
			}
		}
		
		for(MobEmitter left : leftEmit){
			if(left.mob != null) checkUnspawn(left);
		}
		
		for(MobEmitter right : rightEmit){
			if(right.mob != null) checkUnspawn(right);
		}
		
	}

	private void checkUnspawn(MobEmitter emit) {
		if(emit.mob.shooted){
			emit.mob.dead = true;
		}else if(isTooClose(emit)){
			((MobHorizontal)emit.mob.logic).goBack();
		}
	}

	private boolean isTooClose(MobEmitter emit) {
		float dst = level.camera.position.dst(emit.position);
		return dst < 10; // TODO checker si c good...
	}

	private boolean isCloseEnough(MobEmitter emit) {
		float dst = level.camera.position.dst(emit.position);
		return dst < 25; // TODO checker si c good...
	}
	
	private void spawnRight(MobEmitter emit) {
		GameMob mob = level.mobManager.spawn(emit);
		emit.mob = mob;
		emit.direction.set(0, 0, -1); // TODO not sure
		mob.logic = new MobHorizontal();
		mobs.add(mob);
	}

	private void spawnLeft(MobEmitter emit) {
		GameMob mob = level.mobManager.spawn(emit);
		emit.mob = mob;
		emit.direction.set(0, 0, 1); // TODO not sure
		mob.logic = new MobHorizontal();
		mobs.add(mob);
	}


}
