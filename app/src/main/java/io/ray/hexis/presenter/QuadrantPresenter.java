package io.ray.hexis.presenter;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.view.abs.IQuadrantFragment;

import java.util.List;

/**
 * Implementation of a IQuadrantPresenter.
 */
public class QuadrantPresenter implements IQuadrantPresenter {

  // Fragment reference
  private final IQuadrantFragment fragment;

  // Model reference
  private final IQuadrantModel model;

  // Reference to the Matrix presenter so it can do SQL interactions
  private final IMatrixPresenter matrixPresenter;

  // Number reference to the quadrant
  private final int quadrant;

  /**
   * Constructor.
   *
   * @param fragment  Fragment managed by presenter
   * @param model     Model managed by presenter
   */
  public QuadrantPresenter(int quadrant, IQuadrantFragment fragment, IQuadrantModel model,
                           IMatrixPresenter matrixPresenter) {
    this.quadrant = quadrant;
    this.fragment = fragment;
    this.model = model;
    this.matrixPresenter = matrixPresenter;
  }

  /**
   * Add a QuadrantItem to the model.
   *
   * @param message Message to construct a QuadrantItem with
   */
  @Override
  public void addItem(String message) {
    matrixPresenter.addItem(quadrant, new QuadrantItem(message));
    // Update the fragment if data set has been changed
    updateFragment();
  }

  /**
   * Add a QuadrantItem with itemUID to the model.
   *
   * @param message     Message to be displayed.
   * @param itemUid     UID from the database.
   */
  @Override
  public void addItem(String message, long itemUid) {
    matrixPresenter.addItem(quadrant, new QuadrantItem(message, itemUid));
    // Update the fragment if data set has been changed
    updateFragment();
  }

  @Override public void updateModel(List<QuadrantItem> items) {
    model.setData(items);
  }

  /**
   * Update the fragment on data set changes.
   */
  @Override
  public void updateFragment() {
    fragment.setData(model.getData());
  }

  @Override
  public IMatrixPresenter getMatrixPresenter() {
    return matrixPresenter;
  }

  @Override
  public void modifyItemInModel(QuadrantItem item) {
    List<QuadrantItem> data = model.getData();

    // TODO: Clean this code up so it's not O(n)
    for (QuadrantItem quadrantItem : data) {
      if (quadrantItem.getUid() == item.getUid()) {
        data.remove(quadrantItem);
        break;
      }
    }

    data.add(item);
    model.setData(data);
    matrixPresenter.notifyItemModified(item, quadrant);
    updateFragment();
  }

  @Override
  public void removeItemFromModel(QuadrantItem item) {
    model.getData().remove(item);
    matrixPresenter.notifyItemRemoved(item);
    updateFragment();
  }

  /**
   * Remove an item from a Fragment without touching the database
   * @param item    Item to be removed
   */
  public void removeItemLocally(QuadrantItem item) {
    model.getData().remove(item);
    updateFragment();
  }

  @Override
  public int getQuadrant() {
    return quadrant;
  }
}
