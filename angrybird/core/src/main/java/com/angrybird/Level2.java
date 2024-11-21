package com.angrybird;

import com.angrybird.characters.birds.BlueBird;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.PigSoldier;
import com.angrybird.characters.pigs.SimplePig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level2 extends Level{
    int base=37;
    public Level2(Main game) {
        super(game);
        this.spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        background=new Texture("level2.jpg");
        viewport = new FitViewport(250, 120);
        SlingshotSprite.setX(30);
        SlingshotSprite.setY(base);
        birds.add(new BlueBird(world,7,base));
        birds.add(new YellowBird(world,18,base));
        birds.add(new RedBird(world,25,base));
        birds.add(new YellowBird(world,36,base));

        obstacles.add(new Wood(world,110,base+10,5,20));
        obstacles.add(new Wood(world,130,base+10,5,20));
        obstacles.add(new Wood(world,150,base+10,5,20));
        obstacles.add(new Wood(world,170,base+10,5,20));
        obstacles.add(new Glass(world,120,base+35,5,20));
        obstacles.add(new Glass(world,140,base+35,5,20));
        obstacles.add(new Glass(world,160,base+35,5,20));

        obstacles.add(new Woodh(world,121,base+23.5f,20,5));
        obstacles.add(new Woodh(world,141,base+23.5f,20,5));
        obstacles.add(new Woodh(world,161,base+23.5f,20,5));
        obstacles.add(new Glassh(world,130.25f,base+48f,20,5));
        obstacles.add(new Glassh(world,150.75f,base+48f,20,5));

        pigs.add(new SimplePig(world,120,base+5));
        pigs.add(new PigSoldier(world,137,base+5));
        pigs.add(new SimplePig(world,160,base+5));
        pigs.add(new SimplePig(world,130,base+27.7f));
        pigs.add(new SimplePig(world,150,base+27.7f));
        pigs.add(new SimplePig(world,140,base+52.4f));
        createGround();
    }
    private void createGround() {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(125, base - 2); // Center X at 125, Y slightly below base

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
}
