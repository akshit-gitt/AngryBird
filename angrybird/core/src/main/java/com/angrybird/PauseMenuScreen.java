package com.angrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseMenuScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private final Texture backgroundTexture;
    private ImageButton resumeButton;
    private ImageButton exitButton;

    public PauseMenuScreen(Main game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.viewport = new FitViewport(800, 600);
        this.stage = new Stage(viewport);
        this.backgroundTexture = new Texture("pause.png");
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        resumeButton = new ImageButton(skin);
        resumeButton.setPosition(viewport.getWorldWidth() / 2 - resumeButton.getWidth() / 2, viewport.getWorldHeight() / 2 + 30);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level(game)); // Resume the game
            }
        });
        exitButton = new ImageButton(skin);
        exitButton.setPosition(viewport.getWorldWidth() / 2 - exitButton.getWidth() / 2, viewport.getWorldHeight() / 2 - 30);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle exiting to the main menu or quitting
                game.setScreen(new LevelSelectScreen(game)); // Replace with your main menu screen
            }
        });

        stage.addActor(resumeButton);
        stage.addActor(exitButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
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
        backgroundTexture.dispose();
    }
}