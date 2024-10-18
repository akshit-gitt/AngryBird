package com.angrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FirstScreen implements Screen {
    private Main game;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    Texture introimage;
    FitViewport viewport;

    public FirstScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(); // Initialize BitmapFont
        font.setColor(Color.WHITE); // Set the font color to white
        font.getData().setScale(0.5f); // Smaller scale, adjust if needed
    }

    @Override
    public void show() {
        introimage = new Texture("intro.png");
        viewport = new FitViewport(160, 120);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);

        // Apply viewport and set projection matrix for the SpriteBatch
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Begin drawing
        spriteBatch.begin();

        // Draw the intro image (fill the viewport)
        spriteBatch.draw(introimage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Get the world size for centering the text
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Draw the text at the center of the screen (adjust y position if necessary)
       // font.draw(spriteBatch, "Click anywhere to continue", worldWidth / 4f, worldHeight / 17f);

        // End drawing
        spriteBatch.end();

        // If the screen is touched, switch to the game screen
        if (Gdx.input.isTouched()) {
            game.setScreen(new LevelSelectScreen(game));
        }
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
        font.dispose();
        introimage.dispose();
    }
}
