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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level2 extends Level{
    int base=37;
    
    public Level2(Main game) {
        super(game);
        this.levelno=2;
        this.spriteBatch=new SpriteBatch();
        this.slingshotx=38;
        this.slingshoty=base+6;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        background=new Texture("level2.jpg");
        viewport = new FitViewport(250, 120);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(38, base+2);
        bodyDef.gravityScale = 1.0f;

        Body body = world.createBody(bodyDef);
        body.setUserData(this);
        PolygonShape box = new PolygonShape();
        box.setAsBox(3, 5);

        // Create the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.8f;

        body.createFixture(fixtureDef);
        box.dispose();
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(30, base+2);
        bodyDef2.gravityScale = 1.0f;

        Body body2 = world.createBody(bodyDef2);
        body.setUserData(this);
        PolygonShape box2 = new PolygonShape();
        box2.setAsBox(2, 250);

        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = box;
        body2.createFixture(fixtureDef2);
        box2.dispose();
        SlingshotSprite.setX(30);
        SlingshotSprite.setY(base);
        birds.add(new BlueBird(world,7,base));
        birds.add(new YellowBird(world,18,base));
        birds.add(new RedBird(world,25,base));
        birds.add(new YellowBird(world,38,base+6));

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
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(125, base - 2);

        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(125, 3);
        groundBody.createFixture(groundShape, 0.0f);
        groundShape.dispose();
    }
}