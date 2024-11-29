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
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(33, base+2);
        bodyDef.gravityScale = 1.0f;

        Body body = world.createBody(bodyDef);
        body.setUserData(this);
        PolygonShape box = new PolygonShape();
        box.setAsBox(3, 5);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        body.createFixture(fixtureDef);
        box.dispose();

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(25, base+2);
        bodyDef2.gravityScale = 1.0f;

        Body body2 = world.createBody(bodyDef2);
        body.setUserData(this);
        PolygonShape box2 = new PolygonShape();
        box2.setAsBox(2, 250);

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
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(125, base-2);
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(125, 3);
        groundBody.createFixture(groundShape, 0.0f);
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