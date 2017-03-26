package io.ray.hexis.view.abs;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;

import java.util.List;

/**
 * Abstract implementation of a QuadrantFragment.
 */
public interface IQuadrantFragment extends IFragment<IQuadrantPresenter> {
  /**
   * Set the presenter for a IQuadrantFragment.
   *
   * @param presenter Presenter reference
   */
  void setPresenter(IQuadrantPresenter presenter);

  /**
   * Set the data for a fragment. This should be passed down the stack to where it is needed.
   *
   * @param data Data
   */
  void setData(List<QuadrantItem> data);
}
