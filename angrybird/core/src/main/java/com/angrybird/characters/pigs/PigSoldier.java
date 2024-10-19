package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PigSoldier extends Pig{
    public PigSoldier(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("Pig soldier.png");
        this.sprite=new Sprite(texture);
        this.health=60;
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
    }
}
