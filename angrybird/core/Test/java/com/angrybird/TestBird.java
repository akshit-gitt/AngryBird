package com.angrybird;
mport org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import com.angrybird.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestBird {
    @Test
    void TestBirdNumber(){
        Main game = new Main();
        Level1 level1=new Level1(game);
        int result=level1.getBirds().size();
        assertEquals(3,result);
    }
}
