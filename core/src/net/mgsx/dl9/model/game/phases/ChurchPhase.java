package net.mgsx.dl9.model.game.phases;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameMob;
import net.mgsx.dl9.model.game.MobEmitter;
import net.mgsx.dl9.model.game.mobs.MobInFront;
import net.mgsx.dl9.model.game.mobs.MobVertical;

public class ChurchPhase extends BaseActionPhase {

	private Array<GameMob> mobs = new Array<GameMob>();
	private float time;
	private Array<MobEmitter> emittersUsed = new Array<MobEmitter>();
	private Array<MobEmitter> emittersUnused;
	private GameMob mobInFront;
	
	private int nbSpawn = 0;
	
	public ChurchPhase(GameLevel level) {
		super(level);
		emittersUnused = level.mobManager.findEmitters("Empty.church");
//		mobs = level.mobManager.spawnMobs(emitters, GameAssets.i.assetPotiron.scene.model.nodes.first());
//		for(GameMob mob : mobs){
//			mob.logic = new MobPotiron();
//		}
	}
	
	@Override
	public void finished() {
		super.finished();
		if(mobInFront != null) level.mobManager.removeMob(mobInFront);
		level.mobManager.removeMobs(mobs);
	}
	
	@Override
	public boolean isFinished(float time) {
		return noMoreSpawn() && mobInFront == null && mobs.size == 0;
	}
	
	private boolean noMoreSpawn(){
		return nbSpawn >= 10; // TODO more
	}
	
	@Override
	public void update(float gtime, float delta) {
		
		// from 1 to 3 seconds spawn
		time -= delta; // TODO maybe some batch ? spawn by group
		
		if(mobInFront != null && mobInFront.shooted){
			mobInFront = null;
		}
		
		if(time < 0 && emittersUnused.size > 0 && !noMoreSpawn()){
			
			if(nbSpawn > 3 && MathUtils.randomBoolean(.3f) && mobInFront == null){
				
				GameMob mob = level.mobManager.spawnMobInFront(Vector3.Y);
				mob.logic = new MobInFront();
				mobInFront = mob;
				
			}else{
				
				time = MathUtils.random(1f, 3f);
				
				MobEmitter emitter = emittersUnused.removeIndex(MathUtils.random(emittersUnused.size-1));
				GameMob mob = level.mobManager.spawn(emitter);
				emitter.mob = mob;
				mob.logic = new MobVertical();
				mobs.add(mob);
				emittersUsed.add(emitter);
			}
			
			nbSpawn++;
			
		}
		
		for(int i=0 ; i<mobs.size ; ){
			if(mobs.get(i).shooted){
				mobs.removeIndex(i);
			}else{
				i++;
			}
		}
		
		for(int i=0 ; i<emittersUsed.size ; ){
			MobEmitter emitter = emittersUsed.get(i);
			if(emitter.mob == null){
				emittersUnused.add(emitter);
				emittersUsed.removeIndex(i);
			}else{
				i++;
			}
		}
	}

}
