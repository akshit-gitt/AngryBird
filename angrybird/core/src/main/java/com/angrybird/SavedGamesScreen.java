package com.angrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SavedGamesScreen implements Screen {
    private final Main game; // Reference to the game instance
    private Stage stage;
    private Texture backgroundTexture;

    public SavedGamesScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load and set the background image
        backgroundTexture = new Texture("pause_background.png"); // Replace with your image file
        Image backgroundImage = new Image(new TextureRegionDrawable(backgroundTexture));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Create table for button alignment
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create button style with larger text and black box background
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        BitmapFont font = new BitmapFont(); // Default font
        font.getData().setScale(2.0f); // Make the text larger
        buttonStyle.font = font;

        // Create a black box texture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Drawable blackBox = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        // Set the black box as the background
        buttonStyle.up = blackBox; // Normal state background
        buttonStyle.down = blackBox; // Pressed state background

        // Create buttons
        TextButton savedGame1 = new TextButton("Saved Game 1", buttonStyle);
        TextButton savedGame2 = new TextButton("Saved Game 2", buttonStyle);
        TextButton savedGame3 = new TextButton("Saved Game 3", buttonStyle);

        // Add click listeners
        savedGame1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 1 clicked!");
                game.setScreen(new Level1(game)); // Transition to Level1
            }
        });

        savedGame2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 2 clicked!");
                game.setScreen(new Level2(game)); // Transition to Level2
            }
        });

        savedGame3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 3 clicked!");
                game.setScreen(new Level3(game)); // Transition to Level3
            }
        });

        // Add buttons to the table with padding
        table.add(savedGame1).padBottom(30).width(300).height(100).row();
        table.add(savedGame2).padBottom(30).width(300).height(100).row();
        table.add(savedGame3).width(300).height(100).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
