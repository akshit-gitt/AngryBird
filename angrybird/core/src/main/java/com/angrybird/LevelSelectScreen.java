package com.angrybird;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
public class LevelSelectScreen implements Screen {
    private Main game;
    private Texture backgroundImage;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Stage stage;
    private Skin skin;
    private Label outputLabel;

    // SelectLevelSprite and Texture
    private Sprite SelectLevelSprite;
    private Texture SelectLevelTexture;

    public LevelSelectScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Load background and sprite resources
        backgroundImage = new Texture("LevelSelect.png");
        SelectLevelTexture = new Texture("img_3.png"); // Ensure this file path is correct
        SelectLevelSprite = new Sprite(SelectLevelTexture);

        // Set up sprite batch, viewport, and stage
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 6);
        stage = new Stage(new ScreenViewport());

        // Set up input processor
        Gdx.input.setInputProcessor(stage);

        // Load skin
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Create buttons and UI
        createButtons();
    }
    private void createButtons() {
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        float centerX = Gdx.graphics.getWidth() / 2f;
        float buttonSpacing = col_width * 2.5f;

        // Create Level 2 button (middle)
        ImageButton.ImageButtonStyle styleLevel2 = new ImageButton.ImageButtonStyle();
        styleLevel2.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img_1.png")));
        ImageButton Level2 = new ImageButton(styleLevel2);
        Level2.setSize(col_width * 2.15f, row_height * 2.15f);
        Level2.setPosition(centerX - Level2.getWidth() / 2, row_height * 3);
        Level2.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Level2Screen(game));  // Navigate to Level 2 Screen
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(Level2);

        // Create Level 1 button (left)
        ImageButton.ImageButtonStyle styleLevel1 = new ImageButton.ImageButtonStyle();
        styleLevel1.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img.png")));
        ImageButton Level1 = new ImageButton(styleLevel1);
        Level1.setSize(col_width * 2f, row_height * 2f);
        Level1.setPosition(centerX - buttonSpacing - Level1.getWidth(), row_height * 3);
        Level1.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Level1Screen(game));  // Navigate to Level 1 Screen
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(Level1);

        // Create Level 3 button (right)
        ImageButton.ImageButtonStyle styleLevel3 = new ImageButton.ImageButtonStyle();
        styleLevel3.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img_2.png")));
        ImageButton Level3 = new ImageButton(styleLevel3);
        Level3.setSize(col_width * 2.15f, row_height * 2.15f);
        Level3.setPosition(centerX + buttonSpacing, row_height * 3);
        Level3.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Level3Screen(game));  // Navigate to Level 3 Screen
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(Level3);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        outputLabel = new Label("Press a Button", labelStyle);
        outputLabel.setSize(Gdx.graphics.getWidth(), row_height);
        outputLabel.setPosition(0, row_height);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);
    }
    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);

        // Apply viewport
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Begin drawing
        spriteBatch.begin();

        // Draw background
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundImage, 0, 0, worldWidth, worldHeight);

        // Scale the sprite to fit within the world dimensions
        float desiredWidth = worldWidth * 0.5f;  // Scale to 80% of the world width
        float desiredHeight = desiredWidth * (SelectLevelSprite.getHeight() / SelectLevelSprite.getWidth());  // Maintain aspect ratio

        // Draw the sprite at the center of the screen
        float xPos = (worldWidth - desiredWidth) / 2;  // Center horizontally
        float yPos = (worldHeight - desiredHeight) / 1.1f;  // Center vertically

        // Set size and position, then draw sprite
        SelectLevelSprite.setSize(desiredWidth, desiredHeight);
        SelectLevelSprite.setPosition(xPos, yPos);
        SelectLevelSprite.draw(spriteBatch);

        // End drawing
        spriteBatch.end();

        // Render stage for UI components
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
        // Clean up resources when the screen is hidden
    }

    @Override
    public void dispose() {
        // Dispose of resources to avoid memory leaks
        spriteBatch.dispose();
        backgroundImage.dispose();
        SelectLevelTexture.dispose(); // Dispose of sprite texture
        stage.dispose(); // Dispose of stage
        skin.dispose();  // Dispose of skin if it's no longer needed
    }
}
