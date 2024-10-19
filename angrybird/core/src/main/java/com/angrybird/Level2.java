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
        int base=37;
        background=new Texture("level2.jpg");
        viewport = new FitViewport(250, 120);
        SlingshotSprite.setX(30);
        SlingshotSprite.setY(base);
        birds.add(new BlueBird(7,base));
        birds.add(new YellowBird(18,base));
        birds.add(new RedBird(25,base));
        birds.add(new YellowBird(36,base+8));

        obstacles.add(new Wood(110,base));
        obstacles.add(new Wood(130,base));
        obstacles.add(new Wood(150,base));
        obstacles.add(new Wood(170,base));
        obstacles.add(new Glass(120,base+24.8f));
        obstacles.add(new Glass(140,base+24.8f));
        obstacles.add(new Glass(160,base+24.8f));

        horizontal.add(new Wood(121,base+12.3f));
        horizontal.add(new Wood(141,base+12.3f));
        horizontal.add(new Wood(161,base+12.3f));
        horizontal.add(new Glass(130.25f,base+37.1f));
        horizontal.add(new Glass(150.75f,base+37.1f));

        pigs.add(new SimplePig(120,base));
        pigs.add(new PigSoldier(137,base));
        pigs.add(new SimplePig(160,base));
        pigs.add(new SimplePig(130,base+24.7f));
        pigs.add(new SimplePig(150,base+24.7f));
        pigs.add(new SimplePig(140,base+49.4f));
    }
}
