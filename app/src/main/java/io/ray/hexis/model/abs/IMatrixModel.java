package io.ray.hexis.model.abs;

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
}
