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
        background=new Texture("level1.jpg");
        viewport = new FitViewport(250, 120);
        SlingshotSprite.setX(25);
        SlingshotSprite.setY(26);
        birds.add(new RedBird(13,26));
        birds.add(new YellowBird(20,26));
        birds.add(new RedBird(31,34));
        obstacles.add(new Wood(100,26));
        obstacles.add(new Wood(180,26));
        Obstacle o1=new Wood(100,36);
        Obstacle o2=new Wood(180,36);
        o1.getSprite().setRotation(90);
        o2.getSprite().setRotation(90);
        obstacles.add(o2);
        obstacles.add(o1);
        pigs.add(new SimplePig(100,48));
        pigs.add(new SimplePig(180,48));
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
