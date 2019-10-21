package net.mgsx.dl9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameListener;
import net.mgsx.dl9.model.game.GameLoader;
import net.mgsx.dl9.model.game.camera.SimpleCameraAnimator;
import net.mgsx.dl9.ui.GameHUD;
import net.mgsx.dl9.vfx.Beam;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
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
public class GameScreen extends BaseScreen
{
	private SceneManager sceneManager;
	private GameLevel level;
	private GameHUD hud;
	private float time;
	private Beam beam;
	protected Color fogColorTarget = new Color();
	protected float fogTransition = 0;
	protected float fogTime;
	private SceneSkybox skyBox;

	public GameScreen() {
		
		bgColor.set(Color.BLACK);
		
		level = new GameLoader().load(Gdx.files.internal("models/main-scene/main-scene.gltf"));
		
		beam = new Beam();
		
		level.cameraAnimator = new SimpleCameraAnimator(level);
		// level.cameraAnimator = new SplineCameraAnimator(level);
		
		sceneManager = new SceneManager();
		sceneManager.addScene(level.scene);
		sceneManager.setCamera(level.camera);
		sceneManager.setAmbientLight(.2f); // XXX .01f);
		
		if(GameConfig.GPU_NO_LIGHTS){
			sceneManager.environment.clear();
		}
		
		Cubemap cmDiffuse = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/diffuse/diffuse_", ".jpg", EnvironmentUtil.FACE_NAMES_NEG_POS);
		sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(cmDiffuse));
		
		Cubemap cmSpecular = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/specular/specular_", "_", ".jpg", 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
		sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(cmSpecular));
		
		Cubemap cmEnv = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "env/environment/environment_", ".jpg", EnvironmentUtil.FACE_NAMES_NEG_POS);
		sceneManager.setSkyBox(skyBox = new SceneSkybox(cmEnv));
		
		// cmEnv.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		if(GameConfig.GPU_FOG){
			sceneManager.environment.set(new ColorAttribute(ColorAttribute.Fog, Color.BLACK));
			sceneManager.environment.set(FogAttribute.createFog(.1f, 20f, 1f));
		}
		
		/*
		PointLight pLight = ((PointLight)level.scene.getLight("Light_Orientation"));
		if(pLight != null){
			pLight.intensity *= 2;
		}
		
		
		DirectionalLightEx dirLight = ((DirectionalLightEx)level.scene.getLight("Sun_Orientation"));
		if(dirLight != null){
			
			DirectionalShadowLight shadowLight = new DirectionalShadowLight(1024, 1024, 1000, 1000, -500, 500);
			sceneManager.environment.remove(dirLight);
			// sceneManager.environment.remove(pLight);
			
			
			shadowLight.set(dirLight);
			shadowLight.intensity = 5;
			
			sceneManager.environment.add(shadowLight);
		}
		*/
		
		stage.addActor(hud = new GameHUD(level, skin));
		
		level.listeners.add(new GameListener(){
			@Override
			public void onMobShoot(Vector3 position) {
				beam.spawnAt(position);
			}
			@Override
			public void onHeroDead() {
				fogColorTarget.set(Color.RED);
				fogTransition = GameConfig.FULLFOG_SPEED;
			}
			@Override
			public void onHeroWin() {
				fogColorTarget.set(Color.WHITE);
				fogTransition = GameConfig.FULLFOG_SPEED;
			}
		});
		
		fadeIn(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION, new Runnable() {
			@Override
			public void run() {
				level.start();
			}
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(button == Input.Buttons.LEFT){
					level.input.shoot();
				}else if(button == Input.Buttons.RIGHT){
					level.input.reload();
				}
				return true;
			}
		});
		
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		sceneManager.updateViewport((int)stage.getWidth(), (int)stage.getHeight());
	}
	
	@Override
	public void render(float delta) {
		
		// updates
		time += delta;
		stage.act(delta);
		level.update(delta);
		sceneManager.update(delta);
		beam.update(delta);
		
		hud.setLife((float)level.heroLife / (float)GameConfig.HERO_LIFE_MAX);
		
		// full fog effect
		fogTime = MathUtils.clamp(fogTime + delta * fogTransition, 0, 1);

		skyBox.getColor().set(Color.WHITE).lerp(fogColorTarget, fogTime);
		
		fade(fogColorTarget, fogTime);
		
		if(GameConfig.GPU_FOG){
			sceneManager.environment.get(ColorAttribute.class, ColorAttribute.Fog).color.set(Color.BLACK).lerp(fogColorTarget, fogTime);
			float near = MathUtils.lerp(GameConfig.FOG_NEAR, GameConfig.FOG_NEAR, fogTime);
			float far = MathUtils.lerp(GameConfig.FOG_FAR, GameConfig.FOG_FAR, fogTime);
			float exp = MathUtils.lerp(GameConfig.FOG_EXP, .1f, fogTime);
			sceneManager.environment.get(FogAttribute.class, FogAttribute.FogEquation).set(near, far, exp);
		}
		
		// rendering
		
		sceneManager.renderShadows();
		
		
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.getViewport().apply(true);
		
		sceneManager.renderColors();
		
		beam.render(level.camera);
		
		stage.draw();
		
		if(level.isGameover() && fogTime >= 1){
			if(level.heroLife <= 0){
				DL9Game.i().gotoDieScreen();
			}else{
				DL9Game.i().gotoWinScreen();
			}
		}
	}
}
