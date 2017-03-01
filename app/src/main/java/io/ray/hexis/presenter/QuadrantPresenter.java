package io.ray.hexis.presenter;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.view.abs.IQuadrantFragment;

/**
 * Implementation of a IQuadrantPresenter
 */
public class QuadrantPresenter implements IQuadrantPresenter {

  // Fragment reference
  private IQuadrantFragment fragment;

  // Model reference
  private IQuadrantModel model;

  /**
   * Constructor
   * @param fragment  Fragment managed by presenter
   * @param model     Model manageed by presenter
     */
  public QuadrantPresenter(IQuadrantFragment fragment, IQuadrantModel model) {
    this.fragment = fragment;
    this.model = model;
  }

  /**
   * Add a QuadrantItem to the model
   * @param message   Message to construct a QuadrantItem with
     */
  @Override
  public void addItem(String message) {
    model.addItem(new QuadrantItem(message));

    // Update the fragment if data set has been changed
    updateFragment();
  }

    /**
     * Add a QuadrantItem with itemUID to the model
     * @param message
     * @param itemUID
     */
  @Override
  public void addItem(String message, long itemUID){
    model.addItem(new QuadrantItem(message, itemUID));

    // Update the fragment if data set has been changed
    updateFragment();
  }

  /**
   * Update the fragment on data set changes
   */
  @Override
  public void updateFragment() {
    fragment.setData(model.getData());
  }
}
