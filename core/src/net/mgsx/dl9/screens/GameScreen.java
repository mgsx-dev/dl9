package net.mgsx.dl9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import net.mgsx.dl9.DL9Game;
import net.mgsx.dl9.GameConfig;
import net.mgsx.dl9.assets.GameAssets;
import net.mgsx.dl9.audio.GameAudio;
import net.mgsx.dl9.events.GotoMenuEvent;
import net.mgsx.dl9.model.game.GameLevel;
import net.mgsx.dl9.model.game.GameListener;
import net.mgsx.dl9.model.game.GameLoader;
import net.mgsx.dl9.model.game.camera.SimpleCameraAnimator;
import net.mgsx.dl9.model.settings.Settings;
import net.mgsx.dl9.ui.GameHUD;
import net.mgsx.dl9.ui.SettingsStatic;
import net.mgsx.dl9.vfx.Beam;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.EnvironmentUtil;

public class GameScreen extends BaseScreen
{
	private static final float pointLightFactor = 10;
	private static final float dirLightFactor = 5;
	
	private SceneManager sceneManager;
	private GameLevel level;
	private GameHUD hud;
	private float time;
	private Beam beam;
	protected Color fogColorTarget = new Color();
	protected float fogTransition = 0;
	protected float fogTime;
	private SceneSkybox skyBox;

	private float ambient = .5f;
	private Table menu;
	
	private final Settings settings;
	
	public GameScreen() {
		
		settings = DL9Game.i().settings;
		
		bgColor.set(Color.BLACK);
		
		level = new GameLoader().load(GameAssets.i.mainModel);
		
		level.stage = stage;
		
		beam = new Beam();
		
		level.cameraAnimator = new SimpleCameraAnimator(level);
		
		PBRShaderConfig cfg = PBRShaderProvider.defaultConfig();
		
		cfg.numBones = GameConfig.MAX_BONES;
		cfg.numSpotLights = 0;
		cfg.numPointLights = 12;
		cfg.numDirectionalLights = 3;
		
		cfg.fragmentShader = Gdx.files.classpath("net/mgsx/dl9/shaders/gdx-pbr.fs.glsl").readString();
		
		sceneManager = new SceneManager(
				new PBRShaderProvider(cfg), 
				PBRShaderProvider.createDepthShaderProvider(GameConfig.MAX_BONES));
		
		sceneManager.addScene(level.scene);
		sceneManager.addScene(level.witchScene);
		sceneManager.setCamera(level.camera);
		sceneManager.setAmbientLight(ambient);
		
		if(!GameConfig.GPU_LIGHTS){
			sceneManager.environment.remove(DirectionalLightsAttribute.Type);
			sceneManager.environment.remove(PointLightsAttribute.Type);
			sceneManager.environment.remove(SpotLightsAttribute.Type);
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
		
		
		// Adjust light intensity
		for(Entry<Node, BaseLight> e : level.scene.lights){
			BaseLight light = e.value;
			if(light instanceof PointLight){
				((PointLight) light).intensity *= pointLightFactor;
			}else if(light instanceof DirectionalLightEx){
				((DirectionalLightEx) light).intensity = dirLightFactor;
			}
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
		
		hud = new GameHUD(level, skin);
		
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
			@Override
			public void onMenuShow() {
				showMenu(false);
			}
		});
		
		stage.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof GotoMenuEvent){
					showMenu(true);
				}
			}
		});
		
		level.start();
		
		
		fadeIn(Color.BLACK, GameConfig.DEFAULT_FADE_DURATION * 10, new Runnable() {
			@Override
			public void run() {
				if(level.isFirstSequence())	level.next();
			}
		});
		
	}
	
	protected void showMenu(final boolean backFromSettings) {
		
		stage.getRoot().clearChildren();
		
		menu = new Table(skin);
		
		TextButton btPlay = new TextButton("Start", skin);
		btPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameAudio.i.sfxButton();
				menu.addAction(Actions.sequence(
						Actions.alpha(0, 1),
						Actions.removeActor()
						));
				launchGame();
			}
		});
		
		TextButton btSettings = new TextButton("Settings", skin);
		btSettings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameAudio.i.sfxButton();
				menu.addAction(Actions.sequence(
						Actions.alpha(0, .2f),
						Actions.removeActor()));
				SettingsStatic.create(stage, skin);
			}
		});
		
		
		menu.setFillParent(true);
		menu.add(new Label(GameConfig.GAME_TITLE, skin, "stylized")).row();
		menu.add().height(100).row();
		menu.add(btPlay).row();
		menu.add(btSettings).row();
		
		menu.getColor().a = 0;
		menu.addAction(Actions.sequence(Actions.alpha(1, backFromSettings ? .5f : 3f)));
		
		stage.addActor(menu);
	}

	protected void launchGame() {
		stage.addActor(hud);
		level.next();
	}

	@Override
	public void dispose() {
		super.dispose();
		beam.dispose();
		level.dispose();
		sceneManager.dispose();
		skyBox.dispose();
	}
	
	@Override
	public void show() {
		InputAdapter gameInput = new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(button == Input.Buttons.LEFT){
					level.input.shoot();
				}else if(button == Input.Buttons.RIGHT){
					level.input.reload();
				}
				return true;
			}
		};
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, gameInput));
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
		
		if(fogTime > 0){
			fade(fogColorTarget, fogTime);
		}
		
		if(GameConfig.GPU_FOG){
			sceneManager.environment.get(ColorAttribute.class, ColorAttribute.Fog).color.set(Color.BLACK).lerp(fogColorTarget, fogTime);
			float near = MathUtils.lerp(GameConfig.FOG_NEAR, GameConfig.FOG_NEAR, fogTime);
			float far = MathUtils.lerp(GameConfig.FOG_FAR, GameConfig.FOG_FAR, fogTime);
			float exp = MathUtils.lerp(GameConfig.FOG_EXP, .1f, fogTime);
			sceneManager.environment.get(FogAttribute.class, FogAttribute.FogEquation).set(near, far, exp);
		
			// XXX
			float [] fogExps = {1f, .1f, .05f, .03f, 0f};
			float fogExp = fogExps[settings.luminosity.value];
			sceneManager.environment.get(FogAttribute.class, FogAttribute.FogEquation).set(.1f, level.camera.far, fogExp); // .1f is good
		}
		

		// animate lights
		for(Entry<Node, BaseLight> e : level.scene.lights){
			BaseLight light = e.value;
			if(light instanceof PointLight){
				((PointLight) light).intensity = (MathUtils.sin(time * .12f * (1 + MathUtils.sin(time * 1.9f))) + 1.5f) * 40 + 200;
			}else if(light instanceof DirectionalLightEx){
				((DirectionalLightEx) light).intensity = dirLightFactor;
			}
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
		
		if(GameConfig.DEBUG){
			if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
				DL9Game.i().gotoGame();
			}
		}
	}

}
