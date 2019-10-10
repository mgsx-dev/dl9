import sys
import bpy

output,*_ = sys.argv[sys.argv.index("--") + 1:]

bpy.ops.object.mode_set(mode='OBJECT')
bpy.ops.object.select_all(action='DESELECT')

for object in bpy.context.scene.objects:
	if object.type == 'MESH':
		object.select = True

# bpy.ops.object.convert(target='MESH')

bpy.ops.export_scene.fbx(
	filepath=output, 
	check_existing=False, 
	global_scale=0.01, 
	use_tspace=False, # 2.7 new
	use_custom_props=True, # 2.7 new
	add_leaf_bones=False,
	
	bake_anim_step=1,
	bake_anim_simplify_factor=1,
	use_anim_optimize=True, 
	anim_optimize_precision=6,
	
	path_mode='STRIP' # AUTO was not good (see https://wiki.blender.org/index.php/Extensions:2.6/Py/Scripts/Import-Export/Autodesk_FBX), strip remove all folder locations (good when used in atlas .. ?)
	)