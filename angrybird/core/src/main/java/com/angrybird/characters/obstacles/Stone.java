package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Stone extends Obstacle{
    public Stone(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("stone.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.health=70;
        this.damage=80;
    }
}
