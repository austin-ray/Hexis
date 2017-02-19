package io.ray.hexis.view.abs;

import java.util.List;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;

/**
 * Abstract implementation of a QuadrantFragment
 */
public interface IQuadrantFragment extends IFragment {
    /**
     * Set the presenter for a IQuadrantFragment
     * @param presenter     Presenter reference
     */
    void setPresenter(IQuadrantPresenter presenter);

    /**
     * Set the data for a fragment. This should be passed down the stack to where it is needed.
     * @param data  Data
     */
    void setData(List<QuadrantItem> data);
}
