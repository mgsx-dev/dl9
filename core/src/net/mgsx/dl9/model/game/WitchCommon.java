package net.mgsx.dl9.model.game;

import com.badlogic.gdx.utils.Array;

public class WitchCommon {

	public final MobEmitter emit_corridorFront;
	public final MobEmitter emit_corridorBack;
	public final MobEmitter emit_corridorCenter;
	public final MobEmitter emit_corridorLeft;
	public final MobEmitter emit_corridorRight;
	public final MobEmitter emit_InFront;
	public final MobEmitter emit_hostelRight;
	public final MobEmitter emit_hostelLeft;
	public final MobEmitter emit_hostelBack;

	public final Array<MobEmitter> emit_benches;
	
	public WitchCommon(GameLevel level){
		emit_corridorFront = level.mobManager.findEmitter("Empty.witch.corridorFront");
		emit_corridorBack = level.mobManager.findEmitter("Empty.witch.corridorBack");
		emit_corridorCenter = level.mobManager.findEmitter("Empty.witch.corridorCenter");
		emit_corridorLeft = level.mobManager.findEmitter("Empty.witch.corridorLeft");
		emit_corridorRight = level.mobManager.findEmitter("Empty.witch.corridorRight");
		emit_InFront = level.mobManager.findEmitter("Empty.witch.InFront");
		emit_hostelRight = level.mobManager.findEmitter("Empty.witch.hostelRight");
		emit_hostelLeft = level.mobManager.findEmitter("Empty.witch.hostelLeft");
		emit_hostelBack = level.mobManager.findEmitter("Empty.witchHostelBack");
		emit_benches = level.mobManager.findEmitters("Empty.witch.bench");
	}
}
