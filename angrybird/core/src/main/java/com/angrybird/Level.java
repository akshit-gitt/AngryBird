package com.angrybird;
import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
public class Level implements Screen {
    private Main game;
//    PauseMenuScreen pausemenuscreen;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture background;
    ArrayList<Bird> birds = new ArrayList<Bird>();
    ArrayList<Pig> pigs = new ArrayList<Pig>();
    ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    ArrayList<Obstacle> horizontal = new ArrayList<Obstacle>();
    Texture SlingshotTexture = new Texture("Slingshot.png");
    Sprite SlingshotSprite = new Sprite(SlingshotTexture);
    public Stage stage;
    ImageButton pauseButton;
    ImageButton backButton;
    private Skin skin;
    private Texture examboardTexture;
    private Texture resumeTexture;
    private Texture saveTexture;
    private Texture muteTexture;
    private Texture unmuteTexture;
    private boolean isMuted = false;

    public Level(Main game) {
        this.game = game;
//        pausemenuscreen = new PauseMenuScreen(game, this);
        this.spriteBatch = new SpriteBatch();
        this.viewport = new FitViewport(250, 120);
        stage = new Stage(viewport);

        // Load textures
//        background = new Texture("background.png");
        examboardTexture = new Texture("examboard.png");
        resumeTexture = new Texture("resume.png");
        saveTexture = new Texture("saveandexit.png");
        muteTexture = new Texture("mute.png");
        unmuteTexture = new Texture("unmute.png");

        // Initialize the skin for UI elements
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up pause button
        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("pause.png")));
        pauseButton = new ImageButton(pauseStyle);
        pauseButton.setSize(20, 20);
        pauseButton.setPosition(viewport.getWorldWidth() - pauseButton.getWidth() - 10, viewport.getWorldHeight() - pauseButton.getHeight() - 3);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseDialog();
                return;
            }
        });

        // Set up back button
        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("back.png")));
        backButton = new ImageButton(backStyle);
        backButton.setSize(16, 16);
        backButton.setPosition(10, viewport.getWorldHeight() - backButton.getHeight() - 6);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        // Add buttons to the stage
        stage.addActor(pauseButton);
        stage.addActor(backButton);
    }

//    private void showPauseDialog() {
//        Dialog dialog = new Dialog("", skin) {
//            @Override
//            public void result(Object obj) {}
//        };
//        dialog.setBackground(new TextureRegionDrawable(examboardTexture) {
//            @Override
//            public void draw(Batch batch, float x, float y, float width, float height) {
//                batch.setColor(1, 1, 1, 0.8f); // 0.8f is the alpha value, adjust as needed
//                super.draw(batch, x, y, width, height);
//                batch.setColor(1, 1, 1, 1); // Reset color
//            }
//        });
//        dialog.setSize(viewport.getWorldWidth() * 0.6f, viewport.getWorldHeight() * 0.6f);
//        dialog.setBackground(new TextureRegionDrawable(examboardTexture));
//
//        dialog.setPosition(viewport.getWorldWidth() * 0.2f, viewport.getWorldHeight() * 0.2f);
//        dialog.getColor().a = 0f;
//        dialog.addAction(Actions.fadeIn(0.5f));
//        Table overlay = new Table();
//        overlay.setFillParent(true);
//        overlay.setBackground(new TextureRegionDrawable(new Texture(1, 1, Pixmap.Format.RGB888)).tint(new Color(0, 0, 0, 0.5f)));
//        stage.addActor(overlay);
//        overlay.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
//        stage.addActor(dialog);
//        // Create buttons
//        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
//        ImageButton saveButton = new ImageButton(new TextureRegionDrawable(saveTexture));
//        final ImageButton muteButton = new ImageButton(new TextureRegionDrawable(muteTexture));
//
//        // Set up button listeners
//        resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                dialog.hide();
//                resumeGame();
//            }
//        });

//        saveButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Implement save logic here
//                game.setScreen(new LevelSelectScreen(game));
//            }
//        });
//
//        muteButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                isMuted = !isMuted;
//                muteButton.getStyle().imageUp = new TextureRegionDrawable(isMuted ? unmuteTexture : muteTexture);
//                // Implement mute/unmute logic here
//            }
//        });
//
//        // Add buttons to dialog
//        dialog.getContentTable().add(resumeButton).pad(10);
//        dialog.getContentTable().add(saveButton).pad(10);
//        dialog.getContentTable().row();
//        dialog.getContentTable().add(muteButton).colspan(2).pad(10);
//
//        dialog.show(stage);
//    }
public void showPauseDialog() {

    // Create dialog
    Dialog dialog = new Dialog("Pause Menu", skin);

    // Get actual screen dimensions
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    // Set dialog size to 50% of the screen size
    float dialogWidth = screenWidth * 0.5f;
    float dialogHeight = screenHeight * 0.5f;
    dialog.setSize(125, 60);

    // Center the dialog in the screen
    //dialog.setPosition((screenWidth - dialogWidth) / 2, (screenHeight - dialogHeight) / 2);
    dialog.setPosition(125,60);

    // Set background image with proper scaling
    dialog.setBackground(new TextureRegionDrawable(new TextureRegion(examboardTexture)) {
        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            batch.draw(examboardTexture, 47, 18, 155, 90);
        }
    });
    dialog.getColor().a = 0f;
    dialog.addAction(Actions.fadeIn(0.5f));
    Table overlay = new Table();
        overlay.setFillParent(true);
      overlay.setBackground(new TextureRegionDrawable(new Texture(1, 1, Pixmap.Format.RGB888)).tint(new Color(0, 0, 0, 0.5f)));
      stage.addActor(overlay);
    overlay.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
        stage.addActor(dialog);
    // Create buttons
    ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
    ImageButton saveButton = new ImageButton(new TextureRegionDrawable(saveTexture));
    final ImageButton muteButton = new ImageButton(isMuted ? new TextureRegionDrawable(unmuteTexture) : new TextureRegionDrawable(muteTexture));

    // Set up button listeners
    resumeButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dialog.hide();
            resumeGame();
        }
    });

    saveButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            // Implement save logic
            game.setScreen(new LevelSelectScreen(game));
        }
    });

    muteButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            isMuted = !isMuted;
            muteButton.getStyle().imageUp = new TextureRegionDrawable(isMuted ? unmuteTexture : muteTexture);
            // Implement mute/unmute logic
        }
    });

    // Add buttons to the dialog
    dialog.getContentTable().add(resumeButton).pad(10).width(dialogWidth / 3).row();
    dialog.getContentTable().add(saveButton).pad(10).width(dialogWidth / 3).row();
    dialog.getContentTable().add(muteButton).pad(10).width(dialogWidth / 3);

    // Add dialog to stage and display
    dialog.show(stage);
}
    public void resumeGame() {
        Gdx.input.setInputProcessor(stage);
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
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        SlingshotSprite.setSize(15, 15);
        SlingshotSprite.draw(spriteBatch);

        for (Bird bird : birds) {
            bird.getSprite().setSize(bird.getXsize(), bird.getYsize());
            bird.getSprite().draw(spriteBatch);
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.getSprite().setSize(5, 20);
            obstacle.getSprite().setOriginCenter();
            obstacle.getSprite().draw(spriteBatch);
        }
        for (Obstacle obstacle : horizontal) {
            obstacle.getSprite().setRotation(90);
            obstacle.getSprite().setSize(5, 20);
            obstacle.getSprite().setOriginCenter();
            obstacle.getSprite().draw(spriteBatch);
        }
        for (Pig pig : pigs) {
            pig.getSprite().setSize(pig.getXsize(), pig.getYsize());
            pig.getSprite().draw(spriteBatch);
        }

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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
        background.dispose();
        SlingshotTexture.dispose();
        examboardTexture.dispose();
        resumeTexture.dispose();
        saveTexture.dispose();
        muteTexture.dispose();
        unmuteTexture.dispose();
    }
}
