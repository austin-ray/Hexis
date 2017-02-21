package io.ray.hexis.presenter.abs;

import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.view.abs.IMatrixFragment;

/**
 * Presenter for an IMatrixFragment
 */
public interface IMatrixPresenter extends IPresenter<IMatrixFragment, IMatrixModel> {
    /**
     * Construct a QuadrantItem and add it to the IQuadrantModel for a specified quadrant
     * @param quadrant  Quadrant that QuadrantItem is being added to
     * @param message   Message which will be used to construct a QuadrantItem
     */
    void addItem(int quadrant, String message);

    /**
     * Return the IQuadrantModel for a specified quadrant
     * @param quadrant  Quadrant that data is requested from
     * @return          IQuadrantModel for a quadrant
     */
    IQuadrantModel getQuadrantData(int quadrant);
}

