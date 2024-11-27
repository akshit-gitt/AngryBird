package com.angrybird;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scoretest {

    @Test
    void testInitialScore() {
        Main game = new Main();
        Level level = new Level(game);
        int result = level.score;
        assertEquals(0, result);
    }
}
