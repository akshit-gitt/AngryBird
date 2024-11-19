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
        this.spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        background=new Texture("level1.jpg");
        viewport = new FitViewport(250, 120);

        SlingshotSprite.setX(25);
        SlingshotSprite.setY(base);
        birds.add(new RedBird(world,13,base));
        birds.add(new YellowBird(world,20,base));
        birds.add(new RedBird(world,31,34));
        obstacles.add(new Wood(world,100,base,5,20));
        obstacles.add(new Wood(world,180,base,5,20));
        horizontal.add(new Wood(world,100,36,5,20));
        horizontal.add(new Wood(world,180,36,5,20));
        horizontal.add(new Wood(world,140,base-7.3f,5,20));
        pigs.add(new SimplePig(world,100,48));
        pigs.add(new SimplePig(world,180,48));
        pigs.add(new SimplePig(world,140,base+5f));
        createGround();
    }

    private void createGround() {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(125, base - 3); // Center X at 125, Y slightly below base

        // Create the body
        Body groundBody = world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(125, 5); // Adjust width and height

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
