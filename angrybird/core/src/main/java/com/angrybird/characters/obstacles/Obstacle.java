package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.*;
public class Obstacle {
    private Body body;
    Texture texture;
    Sprite sprite;
    int health;
    int damage;
    float xpos;
    float ypos;
    public Obstacle(World world,float xpos,float ypos,float width,float height){
        this.xpos=xpos;
        this.ypos=ypos;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xpos, ypos);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height / 2); // Half-width and half-height

        body.createFixture(box, 0.0f);
        box.dispose();
    }
    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }
    public Sprite getSprite() {
        return sprite;
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
}
