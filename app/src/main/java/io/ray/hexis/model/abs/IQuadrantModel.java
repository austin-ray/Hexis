package io.ray.hexis.model.abs;

import io.ray.hexis.model.QuadrantItem;

import java.util.List;

public interface IQuadrantModel {

  /**
   * Add an item to the IQuadrantModel.
   * @param newItem   New item
   */
  void addItem(QuadrantItem newItem);

  /**
   * Get the actual data from a IQuadrantModel. This is necessary for updating RecyclerViews
   * @return      Actual data from IQuadrantModel
   */
  List<QuadrantItem> getData();

  void setData(List<QuadrantItem> list);
}
