package com.angrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static Music music;
    public static boolean isMuted = false;
    public static void initialize(String musicFilePath) {
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal(musicFilePath));
            music.setLooping(true); // Loop the music
        }
    }

    public static void play() {
        if (music != null && !music.isPlaying()) {
            music.play();
        }
    }

    public static void pause() {
        if (music != null && music.isPlaying()) {
            music.pause();
        }
    }

    public static void stop() {
        if (music != null) {
            music.stop();
        }
    }

    public static void dispose() {
        if (music != null) {
            music.dispose();
        }
    }

        public static void setVolume(float volume) {
        if (music != null) {
            music.setVolume(volume);
        }
    }
    public static void toggleMute() {
        if (isMuted) {
            isMuted = false;
            
        } else {
            isMuted = true;
        }
    }
}
