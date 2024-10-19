package com.angrybird;

import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Arrays;

public class Level1 extends Level{
    public Level1(Main game){
        super(game);
        this.spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        int base=26;
        background=new Texture("level1.jpg");
        viewport = new FitViewport(250, 120);
        SlingshotSprite.setX(25);
        SlingshotSprite.setY(base);
        birds.add(new RedBird(13,base));
        birds.add(new YellowBird(20,base));
        birds.add(new RedBird(31,34));
        obstacles.add(new Wood(100,base));
        obstacles.add(new Wood(180,base));
        horizontal.add(new Wood(100,36));
        horizontal.add(new Wood(180,36));
        horizontal.add(new Wood(140,base-7.3f));
        pigs.add(new SimplePig(100,48));
        pigs.add(new SimplePig(180,48));
        pigs.add(new SimplePig(140,base+5f));
    }




    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
