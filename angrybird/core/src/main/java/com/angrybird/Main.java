package com.angrybird;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        MusicManager.initialize("music.mp3");
        MusicManager.play();
        this.setScreen(new FirstScreen(this));
    }

    @Override
    public void dispose() {
        MusicManager.dispose();
        super.dispose();
    }
}
