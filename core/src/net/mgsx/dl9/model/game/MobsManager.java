package net.mgsx.dl9.model.game;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MobsManager {
	private final GameLevel level;

	private static final Vector3 deltaCam = new Vector3();
	private final Array<MobEmitter> emitterInView = new Array<MobEmitter>();
	
	public boolean autoSpawn = false;
	
	public MobsManager(GameLevel level) {
		super();
		this.level = level;
	}
	
	public void update(float delta){
		
		if(autoSpawn){
			
			makeEmitters();
			
			// TODO remove mobs out of sight...
			
			for(int i=0 ; i<emitterInView.size ; i++){
				MobEmitter emitter = emitterInView.get(i);
				if(emitter.mob == null){
					emitter.mob = spawn(emitter);
				}
			}
		}
	}
	
	public Array<GameMob> spawnMobs(Array<MobEmitter> emitters){
		return spawnMobs(emitters, level.mobNode);
	}
	
	public Array<GameMob> spawnMobs(Array<MobEmitter> emitters, Node node){
		Array<GameMob> mobs = new Array<GameMob>();
		
		for(int i=0 ; i<emitters.size ; i++){
			MobEmitter emitter = emitters.get(i);
			if(emitter.mob == null){
				GameMob mob = emitter.mob = spawn(emitter, node);
				mobs.add(mob);
			}
		}
		
		return mobs;
	}
	
	public Array<MobEmitter> findEmitters(String startWith){
		Array<MobEmitter> emitters = new Array<MobEmitter>();
		for(int i=0 ; i<level.emitters.size ; i++){
			MobEmitter emitter = level.emitters.get(i);
			if(emitter.node.id.startsWith(startWith)){
				emitters.add(emitter);
			}
		}
		return emitters;
	}
	
	private void makeEmitters(){
		// select emitters near to camera
		emitterInView.clear();
		for(int i=0 ; i<level.emitters.size ; i++){
			MobEmitter emitter = level.emitters.get(i);
			deltaCam.set(emitter.position).sub(level.camera.position);
			if(deltaCam.dot(level.camera.direction) < .5f) continue; // XXX not behind
			
			float dst = deltaCam.len();
			if(dst > level.camera.far * .5f) continue; // XXX not too far
			
			emitterInView.add(emitter);
		}
	}
	
	public GameMob spawn(MobEmitter emitter){
		return spawn(emitter, level.mobNode);
	}
	public GameMob spawn(MobEmitter emitter, Node node){
		GameMob mob = new GameMob(node.copy());
		mob.position.set(emitter.position);
		mob.direction.set(emitter.direction);
		mob.up.set(emitter.up);
		level.scene.modelInstance.nodes.add(mob.node);
		level.mobs.add(mob);
		mob.emitter = emitter;
		return mob;
	}

	public GameMob spawnMobInFront(Vector3 direction) {
		GameMob mob = new GameMob(level.mobNode.copy());
		mob.position.set(level.camera.position)
		.mulAdd(level.camera.direction, 1.5f) // XXX config meters
		.mulAdd(direction, -1.75f); // TODO config ?
		mob.direction.set(direction);
		mob.up.set(direction);
		level.scene.modelInstance.nodes.add(mob.node);
		level.mobs.add(mob);
		return mob;
	}
	
	public void removeMob(GameMob mob){
		level.mobs.removeValue(mob, true);
		level.scene.modelInstance.nodes.removeValue(mob.node, true);
		if(mob.emitter != null){
			// XXX mob.emitter.mob = null;
		}
	}
	public void removeMobs(Array<GameMob> mobs) {
		for(GameMob mob : mobs) removeMob(mob);
	}

	public GameMob spawnMobAt(Vector3 position) {
		GameMob mob = new GameMob(level.mobNode.copy());
		mob.position.set(position);
		level.scene.modelInstance.nodes.add(mob.node);
		level.mobs.add(mob);
		return mob;
	}

	
	
}
