package io.ray.hexis.presenter.abs;

import io.ray.hexis.model.QuadrantItem;

public interface ModifyItemListener {

  /**
   * Add a QuadrantItem to the current fragment.
   *
   * @param message Message that will be used to construct a QuadrantItem
   */
  void addItem(String message);

  /**
   * Add a QuadrantItem to the current fragment.
   *
   * @param message Message that will be used to construct a QuadrantItem
   * @param quadrantId the quadrant id
   */
  void addItem(String message, int quadrantId);

  /**
   * Return id of current quadrant.
   *
   * @return id of current quadrant
   */
  int getQuadrant();

  /**
   * Update an item and move it to a new quadrant
   * @param item        Modified item.
   * @param quadrant    Quadrant to write the item.
   */
  void updateItem(QuadrantItem item, int quadrant);

  /**
   * Update an item.
   * @param item      Item that will be updated
   */
  void updateItem(QuadrantItem item);

  /**
   * Remove item from the model.
   * @param item    Object of the item being removed
   */
  void removeItem(QuadrantItem item);
}
