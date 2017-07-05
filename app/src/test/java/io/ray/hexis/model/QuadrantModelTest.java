package io.ray.hexis.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuadrantModelTest {
  @Test
  public void addItem() throws Exception {
    QuadrantModel model = new QuadrantModel();
    assertEquals(0, model.getData().size());
    model.addItem(new QuadrantItem("TEST"));
    assertEquals(1, model.getData().size());
  }
}