package com.angrybird;
import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Objects;

public class Level implements Screen {
    private Main game;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture background;
    ArrayList<Bird> birds= new ArrayList<Bird>();
    ArrayList<Pig> pigs=new ArrayList<Pig>();
    ArrayList<Obstacle> obstacles=new ArrayList<Obstacle>();
    ArrayList<Obstacle> horizontal=new ArrayList<Obstacle>();
    Texture SlingshotTexture=new Texture("Slingshot.png");
    Sprite SlingshotSprite=new Sprite(SlingshotTexture);

    public Level(Main game){
        this.game=game;

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        SlingshotSprite.draw(spriteBatch);
        for(Bird bird:birds){
            bird.getSprite().setSize(bird.getXsize(),bird.getYsize());
            bird.getSprite().draw(spriteBatch);
        }
        for(Obstacle obstacle:obstacles){
            obstacle.getSprite().setSize(5,20);
            obstacle.getSprite().setOriginCenter();
            obstacle.getSprite().draw(spriteBatch);
        }
        for(Obstacle obstacle:horizontal){
            obstacle.getSprite().setRotation(90);
            obstacle.getSprite().setSize(5,20);
            obstacle.getSprite().setOriginCenter();
            obstacle.getSprite().draw(spriteBatch);
        }
        for(Pig pig:pigs){
            pig.getSprite().setSize(pig.getXsize(),pig.getYsize());
            pig.getSprite().draw(spriteBatch);
        }
        SlingshotSprite.setSize(15, 15);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
