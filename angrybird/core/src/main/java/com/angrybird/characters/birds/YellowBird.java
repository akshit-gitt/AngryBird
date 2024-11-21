package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird{
    public YellowBird(World world, float xpos, float ypos){
        super(world,xpos, ypos);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xpos, ypos);
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(1.5f); // Adjust radius to bird size
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.8f; // Bouncy effect

        body.createFixture(fixtureDef);
        shape.dispose();
        this.xsize=5;
        this.ysize=5;
        this.texture=new Texture("yellow bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=40;
        this.speedMultiplier=2f;
    }
}