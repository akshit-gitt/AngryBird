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

        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("pause_background.png")); // Background image
        examboardTexture = new Texture(Gdx.files.internal("examboard.png")); // Examboard image
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        resumeButton = new TextButton("Resume", skin);
        saveAndQuitButton = new TextButton("Save and Quit", skin);
        muteButton = new TextButton("Mute", skin);

        resumeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(level);
                return true;
            }
        });

        saveAndQuitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
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

        buttonTable = new Table();
        buttonTable.center();
        buttonTable.setFillParent(false);

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
        Gdx.input.setInputProcessor(stage);}

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        float examboardWidth = 600;
        float examboardHeight = 600;
        float examboardX = (viewport.getWorldWidth() - examboardWidth) / 2;
        float examboardY = (viewport.getWorldHeight() - examboardHeight) / 2;
        spriteBatch.draw(examboardTexture, examboardX, examboardY, examboardWidth, examboardHeight);

        float buttonTableWidth = examboardWidth * 0.6f;
        float buttonTableHeight = examboardHeight * 0.5f;
        float buttonTableX = examboardX + (examboardWidth - buttonTableWidth) / 2 + 50;
        float buttonTableY = examboardY + (examboardHeight - buttonTableHeight) / 2 ;

        buttonTable.setBounds(buttonTableX, buttonTableY, buttonTableWidth, buttonTableHeight);

        spriteBatch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
        examboardTexture.dispose();
    }
}