package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird{
    public BlueBird(World world, float xpos, float ypos){
        super(world,xpos, ypos);
        this.xsize=10;
        this.ysize=10;
        this.texture=new Texture("blue bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=70;
        this.speedMultiplier=0.75f;
    }
}
