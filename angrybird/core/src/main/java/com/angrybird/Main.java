package com.angrybird;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        this.setScreen(new FirstScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
