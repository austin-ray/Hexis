package io.ray.hexis.model.abs;

import io.ray.hexis.model.QuadrantItem;

import java.util.List;

/**
 * Abstract of a Matrix's data model.
 */
public interface IMatrixModel {

  /**
   * Get a specified IQuadrantModel.
   * @param quadrant  Quadrant of requested IQuadrantModel
   * @return      IQuadrantModel for specified quadrant
   */
  IQuadrantModel getQuadrant(int quadrant);

  void setQuadrantModel(int quadrant, List<QuadrantItem> data);
}
