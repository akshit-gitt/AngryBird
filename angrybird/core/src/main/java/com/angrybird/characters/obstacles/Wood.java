package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
public class Wood extends Obstacle{
    public Wood(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("wood.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.health=20;
        this.damage=30;
    }
}
