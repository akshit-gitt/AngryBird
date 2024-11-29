package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle {
    private Body body;
    protected Texture texture;
    public Sprite sprite;
    protected int health;
    protected int damage;
    private float xpos;
    private float ypos;
    private float width;
    private float height;

    public Obstacle(World world, float xpos, float ypos, float width, float height) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xpos, ypos);
        bodyDef.gravityScale = 1.0f;

        body = world.createBody(bodyDef);
        body.setUserData(this);
        // Create the shape
        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height*0.5f);

        // Create the fixture
        FixtureDef fixtureDef = new FixtureDef();
        if(this instanceof Wood || this instanceof Woodh) {
            fixtureDef.shape = box;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.1f;
        }
        else if(this instanceof Glass || this instanceof Glassh){
            fixtureDef.shape = box;
            fixtureDef.density = 1f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.1f;
        }
        else if(this instanceof Stone || this instanceof Stoneh){
            fixtureDef.shape = box;
            fixtureDef.density = 1.3f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.1f;
        }

        body.createFixture(fixtureDef);
        box.dispose();
    }

    // Getters and Setters
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
        updateShape();
    }

    public void setHeight(float height) {
        this.height = height;
        updateShape();
    }

    public void updateShape() {
        body.destroyFixture(body.getFixtureList().first());
        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height / 2);

        // Create new fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 3.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        box.dispose();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}