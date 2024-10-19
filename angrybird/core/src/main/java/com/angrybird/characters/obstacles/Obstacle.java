package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
    Texture texture;
    Sprite sprite;
    int health;
    int damage;
    float xpos;
    float ypos;
    public Obstacle(float xpos,float ypos){
        this.xpos=xpos;
        this.ypos=ypos;
    }
    public Texture getTexture() {
        return texture;
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
