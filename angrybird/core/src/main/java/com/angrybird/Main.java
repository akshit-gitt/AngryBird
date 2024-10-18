package com.angrybird;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        this.setScreen(new FirstScreen(this)); // Set StartScreen as the initial screen
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of all resources used by the game
    }
}
