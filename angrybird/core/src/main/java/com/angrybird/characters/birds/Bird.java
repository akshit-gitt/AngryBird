package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    Texture texture;
    protected Body body;
    Sprite sprite;
    int damage;
    float speedMultiplier;
    float xpos;
    float ypos;
    int xsize;
    int ysize;
    private float launchTime = -1f;
    boolean islaunched;
    public Bird(World world,float xpos,float ypos){
        this.xpos=xpos;
        this.ypos=ypos;
        this.islaunched=false;
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

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public void setYpos(float ypos) {
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

    public boolean isIslaunched() {
        return islaunched;
    }

    public void setIslaunched(boolean islaunched) {
        this.islaunched = islaunched;
    }

    public float getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(float launchTime) {
        this.launchTime = launchTime;
    }
}