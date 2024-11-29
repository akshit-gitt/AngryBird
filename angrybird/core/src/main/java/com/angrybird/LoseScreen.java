package com.angrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoseScreen implements Screen {
    Music losemusic=Gdx.audio.newMusic(Gdx.files.internal("lose.mp3"));
    private final Game game;
    private Stage stage;
    private Skin skin;
    private Image examboard;
    private TextButton nextLevelButton;
    private TextButton restartButton;
    private Label youLoseLabel;

    public LoseScreen(Game game, Level level) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        losemusic.play();

        examboard = new Image(new Texture("examboard.png"));
        stage.addActor(examboard);

        youLoseLabel = new Label("You Lose!", skin);
        youLoseLabel.setFontScale(2);
        stage.addActor(youLoseLabel);

        nextLevelButton = new TextButton("Choose Level", skin);
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen((Main) game));
            }
        });
        stage.addActor(nextLevelButton);

        restartButton = new TextButton("Restart", skin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(level instanceof Level1){
                    game.setScreen(new Level1((Main) game));
                } else if (level instanceof Level2) {
                    game.setScreen((new Level2((Main) game) ));
                }
                else if(level instanceof Level3){
                    game.setScreen(new Level3((Main) game));
                }
            }
        });
        stage.addActor(restartButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!MusicManager.isMuted){
            MusicManager.play();
        }
        else{
            MusicManager.pause();
        }
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        float examboardWidth = width * 0.6f;
        float examboardHeight = height * 0.4f;
        examboard.setSize(examboardWidth, examboardHeight);
        examboard.setPosition((width - examboardWidth) / 2, (height - examboardHeight) / 2);
        float buttonWidth = width * 0.25f;
        float buttonHeight = height * 0.08f;
        youLoseLabel.setPosition(width / 2 - youLoseLabel.getWidth(), (height / 2 + examboardHeight / 2)  - examboardHeight/8);
        nextLevelButton.setSize(buttonWidth, buttonHeight);
        nextLevelButton.setPosition(width / 2 - nextLevelButton.getWidth() / 2, height / 2 - examboardHeight/4);
        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(width / 2 - restartButton.getWidth() / 2, height / 2  );
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
