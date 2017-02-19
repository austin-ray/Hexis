package io.ray.hexis.presenter;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.abs.IMatrixFragment;


public class MatrixPresenter implements IMatrixPresenter {

    private IMatrixFragment fragment;
    private IMatrixModel model;

    public MatrixPresenter(IMatrixFragment fragment, IMatrixModel model) {
        this.fragment = fragment;
        this.model = model;
    }

    @Override
    public void addItem(int quadrant, String message) {
        model.getQuadrant(quadrant).addItem(new QuadrantItem(message));
    }
}
