package net.mgsx.dl9.core.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;

import net.mgsx.gltf.scene3d.scene.Scene;

/**
 * FBO and shader based screen ray : uses GPU to paint object ID and retrieve by ray casting.
 * depth information can be returned (optional)
 * 
 * @author mgsx
 *
 */
public class ScreenRay {

	public static class ScreenRayResult {
		public float depth;
		public int id;
	}
	
	private static final ScreenRayResult result = new ScreenRayResult();
	
	private FrameBuffer fbo;
	
	private boolean rendering;
	private boolean active;
	
	private boolean depth;
	private ObjectMap<Object, Integer> idMap = new ObjectMap<Object, Integer>();
	private ModelBatch batch;
	private ShaderProvider shaderProvider;
	public Node soloNode;
	
	private class WithIDRenderableProvider implements RenderableProvider{
		public ModelInstance provider;
		@Override
		public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
			for (Node node : provider.nodes) {
				getRenderables(node, renderables, pool);
			}
		}
		protected void getRenderables (Node node, Array<Renderable> renderables, Pool<Renderable> pool) {
			if(soloNode == null || node == soloNode){
				if (node.parts.size > 0) {
					for (NodePart nodePart : node.parts) {
						if (nodePart.enabled){
							Renderable renderable = provider.getRenderable(pool.obtain(), node, nodePart);
							renderable.userData = idMap.get(node);
							renderables.add(renderable);
						}
					}
				}
			}

			for (Node child : node.getChildren()) {
				getRenderables(child, renderables, pool);
			}
		}

	}
	
	private WithIDRenderableProvider withIDRenderableProvider = new WithIDRenderableProvider();

	public ScreenRay(boolean depth, int maxBones){
		this.depth = depth;
		shaderProvider = new ScreenRayShaderProvider(maxBones, depth);
		batch = new ModelBatch(shaderProvider);
	}
	
	public void set(int id, Node object){
		idMap.put(object, id);
	}
	public void set(int id, Material object){
		idMap.put(object, id);
	}
	public void begin(boolean applyViewport){
		if(active) throw new IllegalStateException("nope");
		active = true;
		ensureFBO();
		if(applyViewport){
			fbo.begin();
		}else{
			fbo.bind();
		}
	}
	public void renderBegin(Camera camera, boolean clear){
		rendering = true;
		if(clear){
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		}
		batch.begin(camera);
	}
	public void render(Scene scene){
		withIDRenderableProvider.provider = scene.modelInstance;
		batch.render(withIDRenderableProvider);
	}
	public void renderEnd(){
		batch.end();
		rendering = false;
	}
	
	public void end(boolean restoreViewport){
		if(!active) throw new IllegalStateException("nope");
		if(restoreViewport){
			fbo.end();
		}else{
			FrameBuffer.unbind();
		}
		active = false;
	}
	
	
	private void ensureFBO() {
		int w = Gdx.graphics.getBackBufferWidth();
		int h = Gdx.graphics.getBackBufferHeight();
		
		if(fbo != null && fbo.getWidth() == w && fbo.getHeight() == h) return;
		
		if(fbo != null) fbo.dispose();
		
		fbo = new FrameBuffer(Format.RGBA8888, w, h, true);
	}

	public void clear(){
		idMap.clear();
	}
	
	public ScreenRayResult getPick(Camera camera){
		return getPick(camera, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
	}
	public ScreenRayResult getPick(Camera camera, int screenX, int screenY){
		
		byte[] data = ScreenUtils.getFrameBufferPixels(screenX, screenY, 1, 1, false);
		int depth = (data[3] & 0xFF) << 8 | (data[2] & 0xFF);
		int id = (data[1] & 0xFF) << 8 | (data[0] & 0xFF);
		result.depth = depth == 0 ? -1 : depth / 255f;
		result.id = id;
		return result;
	}

	
}
