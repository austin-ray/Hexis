package io.ray.hexis.model;

import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.model.abs.IQuadrantModel;

public class MatrixModel implements IMatrixModel {
    private IQuadrantModel[] quadrantModels;

    public MatrixModel() {
        quadrantModels = new QuadrantModel[4];

        for (int i = 0; i < quadrantModels.length; i++) {
            quadrantModels[i] = new QuadrantModel();
        }
    }

    @Override
    public IQuadrantModel getQuadrant(int quadrant) {
        return quadrantModels[quadrant];
    }
}
