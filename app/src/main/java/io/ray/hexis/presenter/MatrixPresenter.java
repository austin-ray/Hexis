package io.ray.hexis.presenter;

import io.ray.hexis.model.MatrixModel;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.abs.IMatrixFragment;

import java.util.List;

/**
 * Implementation of a IMatrixPresenter.
 */
public class MatrixPresenter implements IMatrixPresenter {

  // Reference to the fragment
  private final IMatrixFragment fragment;

  // Reference to the model
  private IMatrixModel model;

  /**
   * Constructor taking the fragment and model.
   *
   * @param fragment Fragment handled by presenter
   * @param model    Model handled by presenter
   */
  public MatrixPresenter(IMatrixFragment fragment, IMatrixModel model) {
    this.fragment = fragment;
    this.model = model;
  }

  /**
   * Add an item to a specific quadrant.
   *
   * @param quadrant Quadrant that QuadrantItem is being added to
   * @param message  Message which will be used to construct a QuadrantItem
   */
  @Override
  public void addItem(int quadrant, String message) {
    model.getQuadrant(quadrant).addItem(new QuadrantItem(message));
  }

  public void addItem(int quadrant, QuadrantItem item) {
    model.getQuadrant(quadrant).addItem(item);
  }

  /**
   * Return the IQuadrantModel for a specified quadrant.
   *
   * @param quadrant Quadrant that data is requested from
   * @return IQuadrantModel for a quadrant
   */
  @Override
  public IQuadrantModel getQuadrantData(int quadrant) {
    return model.getQuadrant(quadrant);
  }

  @Override
  public IMatrixFragment getFragment() {
    return fragment;
  }

  @Override
  public IMatrixModel getModel() {
    return model;
  }

  @Override
  public void setQuadrantData(int quadrant, List<QuadrantItem> data) {
    if (model != null) {
      getModel().setQuadrantModel(quadrant, data);
    } else {
      model = new MatrixModel();
      model.setQuadrantModel(quadrant, data);
    }
  }
}
