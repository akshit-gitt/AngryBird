package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class YellowBird extends Bird{
    public YellowBird(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("yellow bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=40;
        this.speedMultiplier=2f;
    }
}
