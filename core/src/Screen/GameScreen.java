package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Box2DWorld;
import com.mygdx.game.Control;
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
	
	// Added by CC
	private Control control;
	private int dir_x, dir_y;
	//
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	Texture texture;
	
	// Box2D code moved to a new class
	private Box2DWorld b2World;
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
		b2World = new Box2DWorld();
		
		new WorldSetting(b2World.world,map);
		player = new player(b2World.world,this);
		
		// Added by CC
		control = new Control();
		Gdx.input.setInputProcessor(control);
		//
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
		// Added by CC
		// This seems easier to read to me
		// It also allows movement in diagonal directions
		
		dir_x = 0;
	    dir_y = 0;
	        
        if(control.down)  dir_y = -1 ;
        if(control.up)    dir_y = 1 ;
        if(control.left)  dir_x = -1;
        if(control.right) dir_x = 1;
	        
        player.b2body.setLinearVelocity(new Vector2(dir_x * player.speed,dir_y * player.speed));
	}
	
	public void update(float up)
	{
		input(up);
		
		b2World.world.step(1/60f, 6, 2);
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
		
		if(control.debug){
			b2World.b2dr.render(b2World.world, gamecam.combined);	
		}
		
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
		b2World.world.dispose();
		b2World.b2dr.dispose();
		hud.dispose();
	}
}
