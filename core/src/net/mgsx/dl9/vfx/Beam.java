package net.mgsx.dl9.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import net.mgsx.dl9.utils.ShaderUtils;

public class Beam implements Disposable {

	private ShaderProgram beamShaderLight;
	private ShaderProgram beamShaderDark;
	private ShaderProgram burstShaderLight;
	private ShaderProgram burstShaderDark;
	private ImmediateModeRenderer20 shapeRenderer;

	private Vector3 rayStart = new Vector3();
	private Vector3 rayEnd = new Vector3();
	private Vector3 rayTan = new Vector3();
	private Vector3 rayPos = new Vector3();
	private float time;
	private float timeout;
	
	public void spawnAt(Vector3 position) {
		timeout = 1;
		
		rayEnd.set(position);
	}
	
	public Beam() {
		beamShaderLight = new ShaderProgram(Gdx.files.classpath("net/mgsx/dl9/shaders/beam.vs"), Gdx.files.internal("net/mgsx/dl9/shaders/beam.fs"));
		beamShaderDark = new ShaderProgram(Gdx.files.internal("net/mgsx/dl9/shaders/beam.vs"), Gdx.files.internal("net/mgsx/dl9/shaders/beam-dark.fs"));
		burstShaderLight = new ShaderProgram(Gdx.files.internal("net/mgsx/dl9/shaders/burst.vs"), Gdx.files.internal("net/mgsx/dl9/shaders/burst.fs"));
		burstShaderDark = new ShaderProgram(Gdx.files.internal("net/mgsx/dl9/shaders/burst.vs"), Gdx.files.internal("net/mgsx/dl9/shaders/burst-dark.fs"));
		
		ShaderUtils.log("beamShaderLight", beamShaderLight);
		ShaderUtils.log("beamShaderDark", beamShaderDark);
		ShaderUtils.log("burstShaderLight", burstShaderLight);
		ShaderUtils.log("burstShaderDark", burstShaderDark);
		
		shapeRenderer = new ImmediateModeRenderer20(4, false, true, 1, beamShaderLight);
	}
	
	@Override
	public void dispose() {
		beamShaderLight.dispose();
		beamShaderDark.dispose();
		burstShaderLight.dispose();
		burstShaderDark.dispose();
		shapeRenderer.dispose();
	}
	
	public void update(float delta){
		time += delta;
		
		timeout -= delta * 3; // TODO config beam time
	}
	
	public void render(Camera camera){

		if(timeout < 0) return;
		
		float rayLen = 2f; // control how many waves
		float rayWidth = .1f;
		float rayCamOffset = -.2f; //-.5f; // -1.5f // control beam offset on camera
		
		boolean lightRay = true;
		
		boolean impactOccured = true;
		
		Color c = new Color(Color.WHITE);
		c.a = timeout;
			
		// ray.getEndPoint(rayEnd, rayLen);
		
		rayTan.set(camera.direction).crs(camera.up).nor();
		rayStart.set(camera.position).mulAdd(camera.direction, camera.near);
		
		rayStart.y += rayCamOffset;
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		if(lightRay)
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		else
			Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		
		ShaderProgram beamShader = lightRay ? beamShaderLight : beamShaderDark;
		
		beamShader.begin();
		beamShader.setUniformf("u_time", time);
		
		shapeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_STRIP);
		shapeRenderer.setShader(beamShader);
		
		
		rayPos.set(rayStart).mulAdd(rayTan, rayWidth);
		shapeRenderer.color(c);
		shapeRenderer.texCoord(0, 0);
		shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
		
		rayPos.set(rayStart).mulAdd(rayTan, -rayWidth);
		shapeRenderer.color(c);
		shapeRenderer.texCoord(1, 0);
		shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
		
		rayPos.set(rayEnd).mulAdd(rayTan, rayWidth);
		shapeRenderer.color(c);
		shapeRenderer.texCoord(0, rayLen);
		shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
		
		rayPos.set(rayEnd).mulAdd(rayTan, -rayWidth);
		shapeRenderer.color(c);
		shapeRenderer.texCoord(1, rayLen);
		shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
		
		
		shapeRenderer.end();
		
		
		if(impactOccured){
			
			ShaderProgram burstShader =  lightRay ? burstShaderLight : burstShaderDark;
			
			float impactSize = 3f;
			shapeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_STRIP);
			shapeRenderer.setShader(burstShader);
			
			rayPos.set(rayEnd).mulAdd(rayTan, impactSize).mulAdd(camera.up, impactSize);
			shapeRenderer.color(c);
			shapeRenderer.texCoord(0, 0);
			shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
			
			rayPos.set(rayEnd).mulAdd(rayTan, -impactSize).mulAdd(camera.up, impactSize);
			shapeRenderer.color(c);
			shapeRenderer.texCoord(1, 0);
			shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
			
			rayPos.set(rayEnd).mulAdd(rayTan, impactSize).mulAdd(camera.up, -impactSize);
			shapeRenderer.color(c);
			shapeRenderer.texCoord(0, 1);
			shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
			
			rayPos.set(rayEnd).mulAdd(rayTan, -impactSize).mulAdd(camera.up, -impactSize);
			shapeRenderer.color(c);
			shapeRenderer.texCoord(1, 1);
			shapeRenderer.vertex(rayPos.x, rayPos.y, rayPos.z);
			
			
			
			shapeRenderer.end();
		}
		
		
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
}
