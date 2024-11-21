package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    protected Texture texture;
    private Body body;
    protected Sprite sprite;
    protected int health;
    protected float xpos;
    protected float ypos;
    protected int xsize;
    protected int ysize;
    
    public Pig(World world, float xpos, float ypos) {
        
        this.xpos=xpos;
        this.ypos=ypos;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xpos, ypos);
        bodyDef.gravityScale = 1.0f;
        body = world.createBody(bodyDef);
        body.setUserData(this);
        CircleShape shape = new CircleShape();
        shape.setRadius(2f); // Adjust radius to bird size
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.3f; // Bouncy effect

        body.createFixture(fixtureDef);
        shape.dispose();
    }
    public void update() {
        sprite.setPosition(
            body.getPosition().x - sprite.getWidth() / 2,
            body.getPosition().y - sprite.getHeight() / 2
        );
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }
    // Getters
    public Body getBody() { return body; }
    public Texture getTexture() { return texture; }
    public Sprite getSprite() { return sprite; }
    public int getHealth() { return health; }
    public float getXpos() { return xpos; }
    public float getYpos() { return ypos; }
    public int getXsize() { return xsize; }
    public int getYsize() { return ysize; }
    
    // Setters
    public void setHealth(int health) { this.health = health; }
    public void setSprite(Sprite sprite) { this.sprite = sprite; }
    public void setTexture(Texture texture) { this.texture = texture; }
    public void setXpos(float xpos) { this.xpos = xpos; }
    public void setYpos(float ypos) { this.ypos = ypos; }
    public void setXsize(int xsize) { this.xsize = xsize; }
    public void setYsize(int ysize) { this.ysize = ysize; }
}