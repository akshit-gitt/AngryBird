package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SimplePig extends Pig{
    public SimplePig(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("default pig.png");
        this.sprite=new Sprite(texture);
        this.health=30;
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
    }
}
