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
        backgroundImage = new Texture("LevelSelect.png");
        SelectLevelTexture = new Texture("img_3.png");
        SelectLevelSprite = new Sprite(SelectLevelTexture);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 6);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        createButtons();
    }
    private void createButtons() {
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        float centerX = Gdx.graphics.getWidth() / 2f;
        float buttonSpacing = col_width * 2.5f;

        ImageButton.ImageButtonStyle styleLevel2 = new ImageButton.ImageButtonStyle();
        styleLevel2.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img_1.png")));
        ImageButton Level2 = new ImageButton(styleLevel2);
        Level2.setSize(col_width * 2.15f, row_height * 1.9f);
        Level2.setPosition(centerX - Level2.getWidth() / 2, row_height * 3);
        Level2.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(Level2);
        ImageButton.ImageButtonStyle styleLevel1 = new ImageButton.ImageButtonStyle();
        styleLevel1.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img.png")));
        ImageButton Level1 = new ImageButton(styleLevel1);
        Level1.setSize(col_width * 1.95f, row_height * 1.95f);
        Level1.setPosition(centerX - buttonSpacing - Level1.getWidth(), row_height * 3);
        Level1.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Level1(game));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(Level1);
        ImageButton.ImageButtonStyle styleLevel3 = new ImageButton.ImageButtonStyle();
        styleLevel3.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("img_2.png")));
        ImageButton Level3 = new ImageButton(styleLevel3);
        Level3.setSize(col_width * 2f, row_height * 2f);
        Level3.setPosition(centerX + buttonSpacing, row_height * 3);
        Level3.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundImage, 0, 0, worldWidth, worldHeight);

        float desiredWidth = worldWidth * 0.5f;
        float desiredHeight = desiredWidth * (SelectLevelSprite.getHeight() / SelectLevelSprite.getWidth());

        float xPos = (worldWidth - desiredWidth) / 2;
        float yPos = (worldHeight - desiredHeight) / 1.1f;

        SelectLevelSprite.setSize(desiredWidth, desiredHeight);
        SelectLevelSprite.setPosition(xPos, yPos);
        SelectLevelSprite.draw(spriteBatch);

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
        backgroundImage.dispose();
        SelectLevelTexture.dispose();
        stage.dispose();
        skin.dispose();
    }
}
