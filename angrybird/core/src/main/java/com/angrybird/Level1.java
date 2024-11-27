package com.angrybird;

import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Arrays;

public class Level1 extends Level{
    int base=26;
    public Level1(Main game){
        super(game);
        this.levelno=1;
        this.spriteBatch=new SpriteBatch();
        this.slingshotx=33;
        this.slingshoty=base+6;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        background=new Texture("level1.jpg");
        viewport = new FitViewport(250, 120);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
        bodyDef.position.set(33, base+2);
        bodyDef.gravityScale = 1.0f; // Add gravity

        // Create the body
        Body body = world.createBody(bodyDef);
        // Add this to constructor after creating the body
        body.setUserData(this); // For collision detection
        // Create the shape
        PolygonShape box = new PolygonShape();
        box.setAsBox(3, 5); // Half-width and half-height

        // Create the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        body.createFixture(fixtureDef);
        box.dispose();

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
        bodyDef2.position.set(25, base+2);
        bodyDef2.gravityScale = 1.0f; // Add gravity

        // Create the body
        Body body2 = world.createBody(bodyDef2);
        // Add this to constructor after creating the body
        body.setUserData(this); // For collision detection
        // Create the shape
        PolygonShape box2 = new PolygonShape();
        box2.setAsBox(2, 250); // Half-width and half-height

        // Create the fixture
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = box;
        body2.createFixture(fixtureDef2);
        box2.dispose();

        SlingshotSprite.setX(25);
        SlingshotSprite.setY(base);
        birds.add(new RedBird(world,13,base));
        birds.add(new YellowBird(world,20,base));
        birds.add(new RedBird(world,33,base+6));
        obstacles.add(new Wood(world,100,base+10,5,20));
        obstacles.add(new Wood(world,180,base+10,5,20));
        obstacles.add(new Woodh(world,100,base+23.5f,20,5));
        obstacles.add(new Woodh(world,180,base+23.5f,20,5));
        obstacles.add(new Woodh(world,140,base+5,20,5));
        pigs.add(new SimplePig(world,100,base+26));
        pigs.add(new SimplePig(world,180,base+26));
        pigs.add(new SimplePig(world,140,base+6));
        createGround();
    }

    private void createGround() {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(125, base-2); // Center X at 125, Y slightly below base

        // Create the body
        Body groundBody = world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(125, 3); // Adjust width and height

        // Attach the shape to the body
        groundBody.createFixture(groundShape, 0.0f);

        // Dispose of the shape to free resources
        groundShape.dispose();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}