package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PigKing extends Pig{
    public PigKing(float xpos,float ypos){
        super(xpos, ypos);
        this.texture=new Texture("pig king.png");
        this.sprite=new Sprite(texture);
        this.xsize=10;
        this.ysize=10;
        this.health=100;
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
    }
}
