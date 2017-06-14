package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screen.GameScreen;

public class TugasAI extends Game {
	public static final int V_WIDTH = 300;
	public static final int V_HEIGHT = 300;
	public static final float PPM = 100;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		setScreen(new GameScreen(this));
		
	}

	@Override
	public void render () {
		super.render();
	}
	
}
