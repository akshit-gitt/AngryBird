package com.angrybird;

import com.angrybird.characters.birds.BlueBird;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.obstacles.Glass;
import com.angrybird.characters.obstacles.Obstacle;
import com.angrybird.characters.obstacles.Stone;
import com.angrybird.characters.obstacles.Wood;
import com.angrybird.characters.pigs.PigSoldier;
import com.angrybird.characters.pigs.SimplePig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level2 extends Level{
    public Level2(Main game) {
        super(game);
        this.spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        int base=37;
        background=new Texture("level2.jpg");
        viewport = new FitViewport(250, 120);
        SlingshotSprite.setX(30);
        SlingshotSprite.setY(base);
        birds.add(new BlueBird(world,7,base));
        birds.add(new YellowBird(world,18,base));
        birds.add(new RedBird(world,25,base));
        birds.add(new YellowBird(world,36,base+8));

        obstacles.add(new Wood(world,110,base,5,20));
        obstacles.add(new Wood(world,130,base,5,20));
        obstacles.add(new Wood(world,150,base,5,20));
        obstacles.add(new Wood(world,170,base,5,20));
        obstacles.add(new Glass(world,120,base+24.8f,5,20));
        obstacles.add(new Glass(world,140,base+24.8f,5,20));
        obstacles.add(new Glass(world,160,base+24.8f,5,20));

        horizontal.add(new Wood(world,121,base+12.3f,5,20));
        horizontal.add(new Wood(world,141,base+12.3f,5,20));
        horizontal.add(new Wood(world,161,base+12.3f,5,20));
        horizontal.add(new Glass(world,130.25f,base+37.1f,5,20));
        horizontal.add(new Glass(world,150.75f,base+37.1f,5,20));

        pigs.add(new SimplePig(world,120,base));
        pigs.add(new PigSoldier(world,137,base));
        pigs.add(new SimplePig(world,160,base));
        pigs.add(new SimplePig(world,130,base+24.7f));
        pigs.add(new SimplePig(world,150,base+24.7f));
        pigs.add(new SimplePig(world,140,base+49.4f));
    }
}
