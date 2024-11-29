package com.angrybird;

import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import com.angrybird.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestScore {

    @Test
    void testInitialScore() {
        Main game = new Main();
        Level level = new Level(game);
        int result = level.score;
        assertEquals(0, result);
    }
}
