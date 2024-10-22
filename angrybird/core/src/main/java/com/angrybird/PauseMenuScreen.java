package com.angrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseMenuScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final SpriteBatch spriteBatch;
    private final Texture backgroundTexture;
    private final Texture examboardTexture;
    private final Level level;

    // Buttons
    private final TextButton resumeButton;
    private final TextButton saveAndQuitButton;
    private final TextButton muteButton;
    private boolean isMuted = false;
    private final FitViewport viewport;
    private Table buttonTable;
    public PauseMenuScreen(Main game, Level level) {
        this.game = game;
        this.level = level;

        // Initialize viewport with 800x600 dimensions
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("pause_background.png")); // Background image
        examboardTexture = new Texture(Gdx.files.internal("examboard.png")); // Examboard image

        // Set up the stage with the viewport and input processor
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Create a skin (can be your custom skin or default one)
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Create buttons
        resumeButton = new TextButton("Resume", skin);
        saveAndQuitButton = new TextButton("Save and Quit", skin);
        muteButton = new TextButton("Mute", skin);

        // Set up listeners for the buttons
        resumeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                level.isPaused = false;
                game.setScreen(level); // Resume the game
                return true;
            }
        });

        saveAndQuitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit(); // Quit the game after saving logic
                return true;
            }
        });

        muteButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isMuted = !isMuted; // Toggle mute state
                muteButton.setText(isMuted ? "Unmute" : "Mute");
                return true;
            }
        });

        // Set up the table layout for the buttons inside the exam board
//        Table buttonTable = new Table();
//        buttonTable.center();
////        buttonTable.setFillParent(true); // Ensure it fills the parent layout
//        float buttonTableWidth = examboardWidth * 0.6f;  // Make the table smaller than the examboard
//        float buttonTableHeight = examboardHeight * 0.5f; // Adjust the height proportionally
//
//// Position the button table to be in the center of the examboard
//        float buttonTableX = examboardX + (examboardWidth - buttonTableWidth) / 2;
//        float buttonTableY = examboardY + (examboardHeight - buttonTableHeight) / 2;
//        buttonTable.setBounds(buttonTableX, buttonTableY, buttonTableWidth, buttonTableHeight);
//        // Add buttons to the table
//        resumeButton.getLabel().setFontScale(0.6f); // Scale down the text inside the button
//        resumeButton.setTransform(true); // Enable transformation for scaling
//        resumeButton.setScale(0.7f); // Scale down the button
//
//        saveAndQuitButton.getLabel().setFontScale(0.6f);
//        saveAndQuitButton.setTransform(true);
//        saveAndQuitButton.setScale(0.7f);
//
//        muteButton.getLabel().setFontScale(0.6f);
//        muteButton.setTransform(true);
//        muteButton.setScale(0.7f);
//
//// Add buttons to the table
//        buttonTable.add(resumeButton).pad(8).row();
//        buttonTable.add(saveAndQuitButton).pad(8).row();
//        buttonTable.add(muteButton).pad(8).row();
//
//        // Add the button table to the stage
//        stage.addActor(buttonTable);
        buttonTable = new Table();
        buttonTable.center();
        buttonTable.setFillParent(false); // Ensure it doesn't fill the entire screen

// Add buttons to the table
        resumeButton.getLabel().setFontScale(0.6f);
        resumeButton.setTransform(true);
        resumeButton.setScale(0.7f);

        saveAndQuitButton.getLabel().setFontScale(0.6f);
        saveAndQuitButton.setTransform(true);
        saveAndQuitButton.setScale(0.7f);

        muteButton.getLabel().setFontScale(0.6f);
        muteButton.setTransform(true);
        muteButton.setScale(0.7f);

        buttonTable.add(resumeButton).pad(8).row();
        buttonTable.add(saveAndQuitButton).pad(8).row();
        buttonTable.add(muteButton).pad(8).row();

        stage.addActor(buttonTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set stage as the input processor
    }

    @Override
    public void render(float delta) {
//        // Clear the screen and draw the background
//        ScreenUtils.clear(Color.BLACK);
//
//        // Begin the sprite batch to draw textures
//        spriteBatch.setProjectionMatrix(viewport.getCamera().combined); // Update sprite batch with the viewport's camera
//        spriteBatch.begin();
//
//        // Draw the background texture to fill the entire viewport
//        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
//
//        // Draw the examboard in the center of the screen
//        float examboardWidth = 600;
//        float examboardHeight = 600;
//        float examboardX = (viewport.getWorldWidth() - examboardWidth) / 2;
//        float examboardY = (viewport.getWorldHeight() - examboardHeight) / 2;
//        spriteBatch.draw(examboardTexture, examboardX, examboardY, examboardWidth, examboardHeight);
//
//        spriteBatch.end();
//
//        // Draw the stage containing the buttons
//        stage.act(delta);
//        stage.draw();
        ScreenUtils.clear(Color.BLACK);

        // Begin the sprite batch to draw textures
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined); // Update sprite batch with the viewport's camera
        spriteBatch.begin();

        // Draw the background texture to fill the entire viewport
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the examboard in the center of the screen
        float examboardWidth = 600;
        float examboardHeight = 600;
        float examboardX = (viewport.getWorldWidth() - examboardWidth) / 2;
        float examboardY = (viewport.getWorldHeight() - examboardHeight) / 2;
        spriteBatch.draw(examboardTexture, examboardX, examboardY, examboardWidth, examboardHeight);

        // Set button table size and position relative to the examboard
        float buttonTableWidth = examboardWidth * 0.6f;
        float buttonTableHeight = examboardHeight * 0.5f;
        float buttonTableX = examboardX + (examboardWidth - buttonTableWidth) / 2 + 50;
        float buttonTableY = examboardY + (examboardHeight - buttonTableHeight) / 2 ;

        // Update button table's bounds
        buttonTable.setBounds(buttonTableX, buttonTableY, buttonTableWidth, buttonTableHeight);

        spriteBatch.end();

        // Draw the stage containing the buttons
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update viewport and stage when resizing
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement logic when the game is paused
    }

    @Override
    public void resume() {
        // Implement logic when the game is resumed
    }

    @Override
    public void hide() {
        // Implement logic when the screen is hidden
    }

    @Override
    public void dispose() {
        // Dispose of resources
        spriteBatch.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        examboardTexture.dispose();
    }
}