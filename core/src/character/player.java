package character;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.TugasAI;

import Screen.GameScreen;

public class player extends Sprite {
	public enum kondisi { atas, bawah, samping, diam};
	public kondisi kondisiSekarang;
	public kondisi kondisiSebelumnya;
	public World world;
	public Body b2body;
	private TextureRegion playerStart;
	private Animation<Object> playerSamping;
	private Animation<Object> playerAtas;
	private Animation<Object> playerBawah;
	private TextureRegion playerDiamSamping;
	private TextureRegion playerDiamAtas;
	private TextureRegion playerDiamBawah;
	
	private float stateTimer;
	private boolean keKanan;
	
	public player(World world,GameScreen screen)
	{
		super(screen.getAtlas().findRegion("player_char"));
		this.world = world;
		
		kondisiSekarang = kondisi.atas;
		kondisiSebelumnya = kondisi.atas;
		stateTimer = 0;
		keKanan = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1;i<3;i++)
			frames.add(new TextureRegion(getTexture(),i * 25, 1, 31, 40));
			Animation<Object> animation = new Animation<Object>(0.1f, frames);
		playerAtas = animation;
		frames.clear();
		
		for(int j = 4;j<6;j++)
			frames.add(new TextureRegion(getTexture(),j * 25, 1, 31, 40));
			Animation<Object> animation1 = new Animation<Object>(0.1f, frames);
		playerBawah = animation1;
		frames.clear();
		
		for(int k = 7;k<10;k++)
			frames.add(new TextureRegion(getTexture(),k * 25, 1, 26, 40));
			Animation<Object> animation11 = new Animation<Object>(0.1f, frames);
		playerSamping = animation11;
		frames.clear();
		
		playerDiamSamping = new TextureRegion(getTexture(),155, 1, 30, 40);
		playerDiamAtas = new TextureRegion(getTexture(),1,1, 30, 40);
		playerDiamBawah = new TextureRegion(getTexture(),100, 1, 25, 40);
		definePlayer();
		
		setBounds(0, 0, 16 / TugasAI.PPM, 16 / TugasAI.PPM);
		setRegion(playerDiamSamping);
		//b2body.setLinearVelocity(b2body.getPosition().x, b2body.getPosition().y);
	}
	
	public void update(float dt)
	{
		setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame(float dt)
	{
		kondisiSekarang = getState();
		//kondisiSebelumnya = getState();
		TextureRegion region;
		switch(kondisiSekarang){
			case atas:
				region = (TextureRegion) playerAtas.getKeyFrame(stateTimer, true);
				break;
			case bawah:
				region = (TextureRegion) playerBawah.getKeyFrame(stateTimer, true);
				break;
			case samping:
				region = (TextureRegion) playerSamping.getKeyFrame(stateTimer, true);
				break;
			case diam:
				switch(kondisiSebelumnya){
				case atas:
					region = playerDiamAtas;
					break;
				case bawah:
					region = playerDiamBawah;
					break;
				case samping:
					region = playerDiamSamping;
				case diam:
				default:
					region = playerDiamAtas;
					break;
				
			}
				//region = playerDiamAtas;
				break;
			default:
				switch(kondisiSebelumnya){
				case atas:
					region = playerDiamAtas;
					break;
				case bawah:
					region = playerDiamBawah;
					break;
				case samping:
					region = playerDiamSamping;
				case diam:
				default:
					region = playerDiamAtas;
					break;
				
			}
				//region = playerDiamAtas;
				break;
		}
		if((b2body.getLinearVelocity().x < 0 || !keKanan) && !region.isFlipX()){
			region.flip(true, false);
			keKanan = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || keKanan) && region.isFlipX()){
			region.flip(true, false);
			keKanan = true;
		}
		
		stateTimer = kondisiSekarang == kondisiSebelumnya ? stateTimer + dt : 0;
		kondisiSekarang = kondisiSebelumnya;
		
		return region;
	}
	
	public kondisi getState()
	{
		if(b2body.getLinearVelocity().y > 0)
		{
			return kondisi.atas;
		}
		if(b2body.getLinearVelocity().y < 0)
		{
			return kondisi.bawah;
		}
		if(b2body.getLinearVelocity().x != 0)
		{
			return kondisi.samping;
		}
		else
		{
			return kondisi.diam;
		}
	}
	
	public void definePlayer()
	{
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / TugasAI.PPM,32 / TugasAI.PPM);
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / TugasAI.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
	}
	
}
