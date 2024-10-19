package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PigSoldier extends Pig{
    public PigSoldier(float xpos,float ypos){
        super(xpos, ypos);
        this.xsize=13;
        this.ysize=7;
        this.texture=new Texture("Pig soldier.png");
        this.sprite=new Sprite(texture);
        this.health=60;
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
    }
}
