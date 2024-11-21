package com.angrybird.characters.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Obstacle{
    public Stone(World world, float xpos, float ypos, float width, float height){
        super(world,xpos,ypos,width,height);
        this.texture=new Texture("stone.png");
        this.sprite=new Sprite(this.texture);
        this.sprite.setX(xpos);
        this.sprite.setY(ypos);
        this.health=70;
        this.damage=80;
    }
}
