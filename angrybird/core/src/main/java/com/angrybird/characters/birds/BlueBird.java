package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlueBird extends Bird{
    public BlueBird(int xpos,int ypos){
        super(xpos,ypos);
        this.texture=new Texture("blue bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=70;
        this.speedMultiplier=0.75f;
    }
}
