package net.mgsx.dl9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.utils.EnvironmentUtil;

/**
 * TODO 
 * load level file
 * entites : 
 * - bg
 * - mob empty
 * - camera (and animations)
 * - 
 * 
 * 
 */
public class GameOverScreen extends BaseScreen
{
	private final Color bgColor = new Color(Color.BLACK);
	private SceneManager sceneManager;
	private Scene scene;

	public GameOverScreen() {
		
		GameAudio.i.playMusic(GameAssets.i.musicMenu1);
		
		fadeIn(Color.RED, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
			@Override
			public void run() {
				scene.animations.loopAll();
				
				root.addAction(Actions.sequence(
						Actions.delay(1.5f), // delay in animation to spawn sound
						Actions.run(new Runnable() {
							@Override
							public void run() {
								GameAssets.i.sfxDeath.play();
							}
						})));
				
				root.addAction(Actions.sequence(
						Actions.delay(4f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								fadeOut(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
									@Override
									public void run() {
										DL9Game.i().gotoStats();
										// use this to test sync: 
										// DL9Game.i().gotoDieScreen();
									}
								});
							}
						})));
			}
		});
		
		scene = new Scene(GameAssets.i.assetEndGame.scene);
		
		Camera camera = scene.getCamera("Camera_Orientation");
		
		sceneManager = new SceneManager();
		sceneManager.addScene(scene);
		sceneManager.setCamera(camera);
		sceneManager.setAmbientLight(1f); // XXX .01f);
		
		Cubemap cmDiffuse = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/diffuse/diffuse_", ".jpg", EnvironmentUtil.FACE_NAMES_NEG_POS);
		sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(cmDiffuse));
		
		Cubemap cmSpecular = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/specular/specular_", "_", ".jpg", 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
		sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(cmSpecular));
		
//		Cubemap cmEnv = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/environment/environment_", ".jpg", EnvironmentUtil.FACE_NAMES_NEG_POS);
//		sceneManager.setSkyBox(new SceneSkybox(cmEnv));
		
		// cmEnv.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Texture lut = new Texture(Gdx.files.internal("env/brdfLUT.png"));
		sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, lut));
		
		sceneManager.environment.set(new ColorAttribute(ColorAttribute.Fog, Color.BLACK));
		sceneManager.environment.set(FogAttribute.createFog(.1f, 20f, 1f));
		
		PointLight pLight = ((PointLight)scene.getLight("Light_Orientation"));
		pLight.intensity *= 5;
//		
//		
		DirectionalLightEx dirLight = ((DirectionalLightEx)scene.getLight("Sun_Orientation"));
		dirLight.intensity *= 2;
		
		//		
//		DirectionalShadowLight shadowLight = new DirectionalShadowLight(1024, 1024, 1000, 1000, -500, 500);
//		sceneManager.environment.remove(dirLight);
//		// sceneManager.environment.remove(pLight);
//		
//		
//		shadowLight.set(dirLight);
//		shadowLight.intensity = 5;
//		
//		sceneManager.environment.add(shadowLight);
		
		Material m = scene.modelInstance.materials.first();
		
		Texture emiTex = new Texture(Gdx.files.internal("models/ending-poc/BackedMat Emissive Color.png"));
		emiTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		m.set(new PBRTextureAttribute(PBRTextureAttribute.EmissiveTexture, emiTex));
		
		// BackedMat
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		sceneManager.updateViewport(width, height);
	}
	
	@Override
	public void render(float delta) {
		sceneManager.update(delta);
		
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		sceneManager.render();
		
		stage.act();
		stage.draw();
	}
}
