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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Objects;

public class Level implements Screen {
    private Main game;
    boolean isPaused = false;
    PauseMenuScreen pausemenuscreen;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture background;
    ArrayList<Bird> birds= new ArrayList<Bird>();
    ArrayList<Pig> pigs=new ArrayList<Pig>();
    ArrayList<Obstacle> obstacles=new ArrayList<Obstacle>();
    ArrayList<Obstacle> horizontal=new ArrayList<Obstacle>();
    Texture SlingshotTexture=new Texture("Slingshot.png");
    Sprite SlingshotSprite=new Sprite(SlingshotTexture);
    public Stage stage;
    ImageButton pauseButton;
//
public Level(Main game){
    this.game = game;
    pausemenuscreen = new PauseMenuScreen(game,this);
    this.spriteBatch = new SpriteBatch();
    this.viewport = new FitViewport(250,120);
    stage = new Stage(viewport);

    // Set the button style
    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
    style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("pause.png")));

    // Use the class-level pauseButton
    pauseButton = new ImageButton(style);
    pauseButton.setSize(20, 20);

    // Set the position of the pause button (top right corner)
    pauseButton.setPosition(viewport.getWorldWidth() - pauseButton.getWidth() - 10, viewport.getWorldHeight() - pauseButton.getHeight() - 5);

    // Add a click listener to the pause button
    pauseButton.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            isPaused = true;
            game.setScreen(pausemenuscreen);
            return true;
        }
    });

    // Add the pause button to the stage
    stage.addActor(pauseButton);
}

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if(isPaused){
            pausemenuscreen.render(delta);
            return;
        }
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
        stage.act(delta);
        stage.draw();
    }
    public void resumeGame() {
        isPaused = false;
        Gdx.input.setInputProcessor(stage);
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
        spriteBatch.dispose();
        stage.dispose();
        background.dispose();
    }
}
