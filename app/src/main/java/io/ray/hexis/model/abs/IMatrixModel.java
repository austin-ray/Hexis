package io.ray.hexis.model.abs;

import java.util.List;

import io.ray.hexis.model.QuadrantItem;

/**
 * Abstract of a Matrix's data model
 */
public interface IMatrixModel {

    /**
     * Get a specified IQuadrantModel
     * @param quadrant  Quadrant of requested IQuadrantModel
     * @return      IQuadrantModel for specified quadrant
     */
    IQuadrantModel getQuadrant(int quadrant);

    void setQuadrantModel(int quadrant, List<QuadrantItem> data);
}
