package io.ray.hexis.model;

import java.util.List;

import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.model.abs.IQuadrantModel;

/**
 * Implementation of an IMatrixModel
 */
public class MatrixModel implements IMatrixModel {

    // Data for all the Quadrants
    private IQuadrantModel[] quadrantModels;

    /**
     * Constructor which sets up four empty IQuadrantModel objects in an array
     */
    public MatrixModel() {
        quadrantModels = new QuadrantModel[4];

        for (int i = 0; i < quadrantModels.length; i++) {
            quadrantModels[i] = new QuadrantModel();
        }
    }

    /**
     * Get the IQuadrantModel from a specified Quadrant
     * @param quadrant  Quadrant of requested IQuadrantModel
     * @return IQuadrantModel for specified quadrant
     */
    @Override
    public IQuadrantModel getQuadrant(int quadrant) {
        return quadrantModels[quadrant];
    }

    @Override
    public void setQuadrantModel(int quadrant, List<QuadrantItem> data) {
        getQuadrant(quadrant).setData(data);
    }
}
