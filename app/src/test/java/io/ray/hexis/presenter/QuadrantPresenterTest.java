package io.ray.hexis.presenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.QuadrantModel;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.view.abs.IQuadrantFragment;

import static org.junit.Assert.*;

public class QuadrantPresenterTest {
  @Test
  public void updateModel() throws Exception {
    IQuadrantFragment mockFrag = Mockito.mock(IQuadrantFragment.class);
    IQuadrantModel mockModel = new QuadrantModel();
    IMatrixPresenter mockPres = Mockito.mock(IMatrixPresenter.class);

    QuadrantPresenter presenter = new QuadrantPresenter(1, mockFrag, mockModel, mockPres);
    List<QuadrantItem> data = new ArrayList<>();
    presenter.updateModel(data);
  }

  @Test
  public void modifyItemInModel() throws Exception {
    IQuadrantModel mockModel = Mockito.mock(IQuadrantModel.class);

    QuadrantItem item = Mockito.mock(QuadrantItem.class);
    Mockito.when(item.getUid()).thenReturn(1L);
    List<QuadrantItem> items = new ArrayList<>();
    items.add(new QuadrantItem("TEST", 30L));
    items.add(item);
    items.add(new QuadrantItem("TEST", 50L));
    Mockito.when(mockModel.getData()).thenReturn(items);

    IMatrixPresenter mockPres = Mockito.mock(IMatrixPresenter.class);
    IQuadrantFragment mockFrag = Mockito.mock(IQuadrantFragment.class);

    QuadrantPresenter presenter = new QuadrantPresenter(1, mockFrag, mockModel, mockPres);
    presenter.updateModel(items);
    presenter.modifyItemInModel(item);
  }

  @Test
  public void removeItemLocally() throws Exception {
    IQuadrantFragment mockFrag = Mockito.mock(IQuadrantFragment.class);
    IQuadrantModel mockModel = new QuadrantModel();
    IMatrixPresenter mockPres = Mockito.mock(IMatrixPresenter.class);

    QuadrantPresenter presenter = new QuadrantPresenter(1, mockFrag, mockModel, mockPres);
    QuadrantItem item = new QuadrantItem("TEST");
    List<QuadrantItem> items = new ArrayList<>();
    items.add(item);

    presenter.updateModel(items);
    presenter.removeItemLocally(item);
  }

}