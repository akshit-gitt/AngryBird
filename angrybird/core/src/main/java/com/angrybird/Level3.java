package com.angrybird;

import com.angrybird.characters.birds.BlueBird;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.PigKing;
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

public class Level3 extends Level {

    int base=37;
    public Level3(Main game) {
        super(game);
        this.levelno=3;
        this.spriteBatch = new SpriteBatch();
        this.slingshotx=38;
        this.slingshoty=base+6;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background = new Texture("level3.jpg");
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
        birds.add(new BlueBird(world,2,base));
        birds.add(new BlueBird(world,13,base));
        birds.add(new RedBird(world,25,base));
        birds.add(new RedBird(world,38,base+6));

        obstacles.add(new Stone(world,132.5f,base+10,5,20));
        obstacles.add(new Stone(world,147.5f,base+10,5,20));
        obstacles.add(new Glass(world,127.4f,base+10,5,20));
        obstacles.add(new Glass(world,152.7f,base+10,5,20));
        obstacles.add(new Wood(world,115,base+10,5,20));
        obstacles.add(new Wood(world,165.1f,base+10,5,20));
        obstacles.add(new Wood(world,115,base+30,5,20));
        obstacles.add(new Wood(world,115,base+50,5,20));
        obstacles.add(new Wood(world,132.5f,base+53,5,20));
        obstacles.add(new Wood(world,147.5f,base+53,5,20));

        obstacles.add(new Wood(world,165.1f,base+30,5,20));
        obstacles.add(new Wood(world,165.1f,base+50,5,20));


        obstacles.add(new Stoneh(world,140,base+23.5f,20,5));
        obstacles.add(new Stoneh(world,140,base+28.5f,20,5));
        obstacles.add(new Woodh(world,122.5f,base+65f,20,5));
        obstacles.add(new Woodh(world,140,base+33.6f,20,5));
        obstacles.add(new Woodh(world,140,base+38.6f,20,5));
        obstacles.add(new Woodh(world,157.5f,base+65f,20,5));

        pigs.add(new PigKing(world,141f,base+2));
        pigs.add(new PigSoldier(world,127.4f,base+19.5f));
        pigs.add(new PigSoldier(world,152.4f,base+19.5f));
        pigs.add(new PigSoldier(world,140.6f,base+40.5f));
        pigs.add(new SimplePig(world,125,base+70));
        pigs.add(new SimplePig(world,155,base+68));
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