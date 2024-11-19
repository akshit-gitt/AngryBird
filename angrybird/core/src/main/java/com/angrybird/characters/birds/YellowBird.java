package com.angrybird.characters.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird{
    public YellowBird(World world, float xpos, float ypos){
        super(world,xpos, ypos);
        this.xsize=5;
        this.ysize=5;
        this.texture=new Texture("yellow bird.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.damage=40;
        this.speedMultiplier=2f;
    }
}
