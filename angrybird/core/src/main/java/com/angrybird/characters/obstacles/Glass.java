package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Glass extends Obstacle{
    public Glass(float xpos,float ypos){
        super(xpos, ypos);
        this.texture=new Texture("glass.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.health=50;
        this.damage=60;
    }
}
