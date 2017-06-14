package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DWorld {
	 public World world;
	 public Box2DDebugRenderer b2dr;
	 
	 public Box2DWorld(){
		 // ZERO GRAVITY
	     world = new World(new Vector2(0.0f, 0.0f), true);
	     world.setContactListener(setup_listener());
	     b2dr = new Box2DDebugRenderer();
	  }
	 
	 private ContactListener setup_listener() {
	        ContactListener listener;
	        
	        listener = new ContactListener() {
	            @Override
	            public void preSolve(Contact contact, Manifold oldManifold) {}
	            
	             
	            @Override
	            public void postSolve(Contact contact, ContactImpulse impulse) {}

				@Override
				public void beginContact(Contact contact) {
					Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                System.out.println(fixtureA.hashCode() + " hit " + fixtureB.hashCode());
	                //process_collisions(fixtureA, fixtureB, true);
				}

				@Override
				public void endContact(Contact contact) {
					Fixture fixtureA = contact.getFixtureA();
					Fixture fixtureB = contact.getFixtureB();
		            
					System.out.println(fixtureA.hashCode() + " no longer in contact with " + fixtureB.hashCode());
					//process_collisions(fixtureA, fixtureB, false);
				}
	        };
	        
	        return listener;
	    }  
}
