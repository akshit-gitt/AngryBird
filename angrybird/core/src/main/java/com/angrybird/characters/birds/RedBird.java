package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RedBird extends Bird{
    public RedBird(int xpos,int ypos){
        super(xpos, ypos);
        this.texture=new Texture("red bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=50;
        this.speedMultiplier=1f;
    }
}
