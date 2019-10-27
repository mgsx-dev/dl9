package net.mgsx.dl9.core.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

import net.mgsx.dl9.utils.ShaderUtils;
import net.mgsx.gltf.scene3d.shaders.PBRShader;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;

public class ScreenRayShaderProvider extends BaseShaderProvider
{
	private PBRShaderConfig config;
	private boolean outDepth;
	
	/**
	 * only numBones is necessary
	 * @param config
	 */
	public ScreenRayShaderProvider(PBRShaderConfig config, boolean outDepth) {
		super();
		this.config = config;
		this.outDepth = outDepth;
	}

	public ScreenRayShaderProvider(int maxBones, boolean outDepth) {
		this.config = new PBRShaderConfig();
		this.config.numBones = maxBones;
		this.outDepth = outDepth;
	}

	// TODO copied from upcoming GLTF PBRShaderProvider
	public static String prefixMorphTargets(String prefix, Renderable renderable){
		for(VertexAttribute att : renderable.meshPart.mesh.getVertexAttributes()){
			for(int i=0 ; i<8 ; i++){
				if(att.alias.equals(ShaderProgram.POSITION_ATTRIBUTE + i)){
					prefix += "#define " + "position" + i + "Flag\n";
				}else if(att.alias.equals(ShaderProgram.NORMAL_ATTRIBUTE + i)){
					prefix += "#define " + "normal" + i + "Flag\n";
				}else if(att.alias.equals(ShaderProgram.TANGENT_ATTRIBUTE + i)){
					prefix += "#define " + "tangent" + i + "Flag\n";
				}
			}
		}
		return prefix;
	}
	public static String prefixBones(String prefix, Renderable renderable, int maxBones){
		final int n = renderable.meshPart.mesh.getVertexAttributes().size();
		for (int i = 0; i < n; i++) {
			final VertexAttribute attr = renderable.meshPart.mesh.getVertexAttributes().get(i);
			if (attr.usage == Usage.BoneWeight)
				prefix += "#define boneWeight" + attr.unit + "Flag\n";
		}
		return prefix + "#define numBones " + maxBones + "\n";
	}
	
	private static class DepthIDShader extends PBRShader
	{
		final long all = VertexAttributes.Usage.BoneWeight | VertexAttributes.Usage.Position;
		public DepthIDShader(Renderable renderable, Config config, String prefix) {
			super(renderable, config, prefix);
			mask = renderable.meshPart.mesh.getVertexAttributes().getMask() & all;
		}
		private int u_id;
		private int u_cameraSettings;
		private final Vector2 idVec = new Vector2();
		private final long mask;
		
		@Override
		public void init(ShaderProgram program, Renderable renderable) {
			super.init(program, renderable);
			u_id = program.getUniformLocation("u_id");
			u_cameraSettings = program.getUniformLocation("u_cameraSettings");
		}
		@Override
		public void begin(Camera camera, RenderContext context) {
			super.begin(camera, context);
			program.setUniformf(u_cameraSettings, camera.position.x, camera.position.y, camera.position.z, 1f / camera.far);
		}
		@Override
		public void render(Renderable renderable, Attributes combinedAttributes) {
			int id = 1 << 15 -1;
			if(renderable.userData instanceof Integer){
				id = (Integer)renderable.userData;
			}
			program.setUniformf(u_id, idVec.set((id % 256) / 255f, (id / 256) / 255f));
			super.render(renderable, combinedAttributes);
		}
		@Override
		public boolean canRender(Renderable renderable) {
			return mask == (renderable.meshPart.mesh.getVertexAttributes().getMask() & all);
		}
	}
	
	@Override
	protected Shader createShader(Renderable renderable) {
		
		String prefix = "";
		
		if(outDepth){
			prefix += "#define OUT_DEPTH\n";
		}
		
		if (renderable.meshPart.mesh.getVertexAttribute(Usage.Position) != null) prefix += "#define positionFlag\n";
		
		prefix = prefixMorphTargets(prefix, renderable);
		prefix = prefixBones(prefix, renderable, config.numBones);
		
		String vsPrefix = prefix;
		
		String fsPrefix = "";
		
		PBRShaderConfig config = PBRShaderProvider.defaultConfig();
		config.numDirectionalLights = 0;
		config.numPointLights= 0;
		config.numSpotLights = 0;
		config.vertexShader = vsPrefix + Gdx.files.classpath("net/mgsx/dl9/shaders/screen-ray-vert.glsl").readString();
		config.fragmentShader = fsPrefix + Gdx.files.classpath("net/mgsx/dl9/shaders/screen-ray-frag.glsl").readString();
		config.numBones = this.config.numBones;
		PBRShader shader = new DepthIDShader(renderable, config, prefix);
		
		ShaderUtils.log("DepthIDShader", shader.program);
		
		if(!shader.canRender(renderable)) throw new GdxRuntimeException("oops! recursive GPU create shader!");
		
		return shader;
	}

}
