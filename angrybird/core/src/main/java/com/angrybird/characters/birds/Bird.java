package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    Texture texture;
    private Body body;
    Sprite sprite;
    int damage;
    float speedMultiplier;
    float xpos;
    float ypos;
    int xsize;
    int ysize;
    public Bird(World world,float xpos,float ypos){
        this.xpos=xpos;
        this.ypos=ypos;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xpos, ypos);
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f); // Adjust radius to bird size
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.8f; // Bouncy effect

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public int getDamage() {
        return damage;
    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeedMultiplier(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setTexture(Texture texture) {
        this.texture=texture;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public void setXsize(int xsize) {
        this.xsize = xsize;
    }

    public void setYsize(int ysize) {
        this.ysize = ysize;
    }

    public int getYsize() {
        return ysize;
    }

    public int getXsize() {
        return xsize;
    }
}
