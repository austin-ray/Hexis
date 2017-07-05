package io.ray.hexis.presenter.abs;

import io.ray.hexis.model.QuadrantItem;

import java.util.List;

/**
 * Interface for a QuadrantPresenter.
 */
public interface IQuadrantPresenter {

  /**
   * Construct a QuadrantItem from a given message and add it to the IQuadrantModel.
   * @param message   Message to construct a QuadrantItem with
   */
  void addItem(String message);

  void updateModel(List<QuadrantItem> items);

  void modifyItemInModel(QuadrantItem item);

  void removeItemFromModel(QuadrantItem item);

  void removeItemLocally(QuadrantItem item);

  /**
   * Update the Fragment.
   */
  void updateFragment();

  /**
   * Get the presenter for one level up in the program heirarchy.
   */
  IMatrixPresenter getMatrixPresenter();

  int getQuadrant();
}
