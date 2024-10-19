package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bird {
    Texture texture;
    Sprite sprite;
    int damage;
    float speedMultiplier;
    int xpos;
    int ypos;
    public Bird(int xpos,int ypos){
        this.xpos=xpos;
        this.ypos=ypos;
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

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
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
}
