package com.angrybird.characters.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class SimplePig extends Pig{
    public SimplePig(World world, float xpos, float ypos){
        super(world,xpos, ypos);
        this.xsize=5;
        this.ysize=5;
        this.texture=new Texture("default pig.png");
        this.sprite=new Sprite(texture);
        this.health=30;
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
    }
}
