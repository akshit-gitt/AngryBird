package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private Body body;
    Texture texture;
    Sprite sprite;
    int health;
    Texture injuredTexture=new Texture("weak pig.png");
    Sprite injuredSprite=new Sprite(injuredTexture);
    float xpos;
    float ypos;
    int xsize;
    int ysize;
    public Pig(World world,float xpos,float ypos){
        this.xpos=xpos;
        this.ypos=ypos;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Pigs are stationary unless moved by external forces
        bodyDef.position.set(xpos, ypos);
        this.body = world.createBody(bodyDef);

        // Define Box2D shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(xsize / 2, ysize / 2); // Half-width and half-height

        // Define fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f; // Adjust density for appropriate reactions
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // Slight bounce when hit

        this.body.createFixture(fixtureDef);
        shape.dispose();
        this.body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInjuredSprite(Sprite injuredSprite) {
        this.injuredSprite = injuredSprite;
    }

    public void setInjuredTexture(Texture injuredTexture) {
        this.injuredTexture = injuredTexture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getYpos() {
        return ypos;
    }

    public float getXpos() {
        return xpos;
    }

    public int getHealth() {
        return health;
    }

    public Sprite getInjuredSprite() {
        return injuredSprite;
    }

    public Texture getInjuredTexture() {
        return injuredTexture;
    }

    public int getXsize() {
        return xsize;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }

    public int getYsize() {
        return ysize;
    }

    public void setXsize(int xsize) {
        this.xsize = xsize;
    }

    public void setYsize(int ysize) {
        this.ysize = ysize;
    }
}
