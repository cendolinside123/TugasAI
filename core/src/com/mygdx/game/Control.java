package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

public class Control extends InputAdapter implements InputProcessor{
    // DIRECTIONS
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    // MOUSE
    public boolean  LMB;
    public boolean  RMB;
    public boolean  processed_click;

    // DEBUG
    public boolean debug;
    
    
    public Control(){

    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
        case Keys.DOWN:
            down = true;
            break;
        case Keys.UP:
            up = true;
            break;
        case Keys.LEFT:
            left = true;
            break;
        case Keys.RIGHT:
            right = true;
            break;
        case Keys.S:
            down = true;
            break;
        case Keys.W:
            up = true;
            break;
        case Keys.A:
            left = true;
            break;
        case Keys.D:
            right = true;
            break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
        case Keys.DOWN:
            down = false;
            break;
        case Keys.UP:
            up = false;
            break;
        case Keys.LEFT:
            left = false;
            break;
        case Keys.RIGHT:
            right = false;
            break;
        case Keys.S:
            down = false;
            break;
        case Keys.W:
            up = false;
            break;
        case Keys.A:
            left = false;
            break;
        case Keys.D:
            right = false;
            break;
        case Keys.ESCAPE:
            Gdx.app.exit();
            break;
        case Keys.BACKSPACE:
            debug = !debug;
            break;
        }
        return false;
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = true; 
        } else if (pointer == 0 && button == 0){
            RMB = true; 
        }
        
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = false; 
            processed_click = false;
        } else if (pointer == 0 && button == 0){
            RMB = false; 
        }
        
        return false;
    }

   
}
