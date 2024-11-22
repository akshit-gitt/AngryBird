package com.angrybird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FirstScreen implements Screen {
    World world;
    private Main game;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Texture introimage;
    private FitViewport viewport;
    // private Music music;

    public FirstScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(0.5f);
    }

    @Override
    public void show() {
        introimage = new Texture("intro.png");
        viewport = new FitViewport(160, 120);
        // music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        // music.setLooping(true);
        // music.setVolume(.5f);
        // music.play();
     
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        if(!MusicManager.isMuted){
            MusicManager.play();
        }
        else{
            MusicManager.pause();
        }
        spriteBatch.begin();
        spriteBatch.draw(introimage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.end();
        if (Gdx.input.isTouched()) {
           
            game.setScreen(new LevelSelectScreen(game));
            MusicManager.pause();
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
