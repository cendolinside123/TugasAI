package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TugasAI;

import Scenes.hud;
import character.player;

import Tools.WorldSetting;


public class GameScreen implements Screen {
	public TugasAI main;
	private TextureAtlas atlas;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private hud hud;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	Texture texture;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	private player player;
	
	public GameScreen(TugasAI main)
	{
		atlas = new TextureAtlas("player_char.pack");
		this.main = main;
		
		gamecam = new OrthographicCamera();
		gamePort = new StretchViewport(TugasAI.V_WIDTH / 150,TugasAI.V_HEIGHT / 150,gamecam);
		hud = new hud(main.batch);
		
		maploader = new TmxMapLoader();
		map = maploader.load("isometric2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map,1 / TugasAI.PPM);
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		world = new World(new Vector2(0,-10),true);
		b2dr = new Box2DDebugRenderer();
		
		new WorldSetting(world,map);
		player = new player(world,this);
		
	}
	
	public TextureAtlas getAtlas(){
		
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void input(float in)
	{
		/*if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 2)
			player.b2body.applyLinearImpulse(new Vector2(0, 1.1f),player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -2)
			player.b2body.applyLinearImpulse(new Vector2(0, -1.1f),player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);*/
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			player.b2body.setLinearVelocity(0, 0.1f);
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			player.b2body.setLinearVelocity(0, -0.1f);
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			player.b2body.setLinearVelocity(0.1f, 0);
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			player.b2body.setLinearVelocity(-0.1f, 0);
		else
			player.b2body.setLinearVelocity(0, 0);
		
	}
	
	public void update(float up)
	{
		input(up);
		
		world.step(1/60f, 6, 2);
		player.update(up);
		
		gamecam.position.x = player.b2body.getPosition().x;
		gamecam.position.y = player.b2body.getPosition().y;
		gamecam.update();
		
		renderer.setView(gamecam);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update(delta);
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		b2dr.render(world, gamecam.combined);
		
		hud.stage.draw();
		main.batch.setProjectionMatrix(gamecam.combined);
		main.batch.begin();
	      
	      
	    player.draw(main.batch);
	    main.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
	}
}
