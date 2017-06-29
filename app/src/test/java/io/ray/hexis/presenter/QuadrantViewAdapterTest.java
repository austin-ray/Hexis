package io.ray.hexis.presenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.ray.hexis.model.QuadrantItem;

import static org.junit.Assert.*;

public class QuadrantViewAdapterTest {
  @Test
  public void newInstance() throws Exception {
    QuadrantViewAdapter.Listener mockListener = Mockito.mock(QuadrantViewAdapter.Listener.class);
    List<QuadrantItem> data = new ArrayList<>();
    QuadrantViewAdapter adapter = QuadrantViewAdapter.newInstance(mockListener, data);

    assertEquals(data, adapter.getData());
  }
}