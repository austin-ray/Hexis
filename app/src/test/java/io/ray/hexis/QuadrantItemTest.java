package io.ray.hexis;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuadrantItemTest {
    @Test
    public void getMessage() throws Exception {
        QuadrantItem test = new QuadrantItem("TEST");
        assertEquals("TEST", test.getMessage());
    }

}