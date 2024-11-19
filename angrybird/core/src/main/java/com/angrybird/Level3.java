package com.angrybird;

import com.angrybird.characters.birds.BlueBird;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.obstacles.Glass;
import com.angrybird.characters.obstacles.Stone;
import com.angrybird.characters.obstacles.Wood;
import com.angrybird.characters.pigs.PigKing;
import com.angrybird.characters.pigs.PigSoldier;
import com.angrybird.characters.pigs.SimplePig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level3 extends Level {
    public Level3(Main game) {
        super(game);
        this.spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background = new Texture("level3.jpg");
        viewport = new FitViewport(250, 120);
        int base=37;
        SlingshotSprite.setX(30);
        SlingshotSprite.setY(base);
        birds.add(new BlueBird(world,2,base));
        birds.add(new BlueBird(world,13,base));
        birds.add(new YellowBird(world,25,base));
        birds.add(new RedBird(world,36,base+8));

        obstacles.add(new Stone(world,132.5f,base,5,20));
        obstacles.add(new Stone(world,147.5f,base,5,20));
        obstacles.add(new Glass(world,127.4f,base,5,20));
        obstacles.add(new Glass(world,152.7f,base,5,20));
        obstacles.add(new Wood(world,115,base,5,20));
        obstacles.add(new Wood(world,115,base+20,5,20));
        obstacles.add(new Wood(world,115,base+40,5,20));
        obstacles.add(new Wood(world,132.5f,base+40,5,20));
        obstacles.add(new Wood(world,147.5f,base+40,5,20));
        obstacles.add(new Wood(world,165.1f,base,5,20));
        obstacles.add(new Wood(world,165.1f,base+20,5,20));
        obstacles.add(new Wood(world,165.1f,base+40,5,20));


        horizontal.add(new Stone(world,140,base+12.3f,5,20));
        horizontal.add(new Stone(world,140,base+17.5f,5,20));
        horizontal.add(new Wood(world,122.5f,base+52.5f,5,20));
        horizontal.add(new Wood(world,140,base+22.6f,5,20));
        horizontal.add(new Wood(world,140,base+27.6f,5,20));
        horizontal.add(new Wood(world,157.5f,base+52.5f,5,20));

        pigs.add(new PigKing(world,137.6f,base));
        pigs.add(new PigSoldier(world,122.4f,base+19.5f));
        pigs.add(new PigSoldier(world,149.4f,base+19.5f));
        pigs.add(new PigSoldier(world,136.6f,base+39.5f));
        pigs.add(new SimplePig(world,125,base+65));
        pigs.add(new SimplePig(world,155,base+65));
    }
}
