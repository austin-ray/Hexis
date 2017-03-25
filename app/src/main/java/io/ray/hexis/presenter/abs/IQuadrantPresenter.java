package io.ray.hexis.presenter.abs;

/**
 * Interface for a QuadrantPresenter
 */
public interface IQuadrantPresenter {
    /**
     * Construct a QuadrantItem from a given message and add it to the IQuadrantModel
     * @param message   Message to construct a QuadrantItem with
     */
    void addItem(String message);

    /**
     * Construct a QuadrantItem from a given message and itemUID and it to the IQuadrantModel
     * @param message
     * @param itemUID
     */
    void addItem(String message, long itemUID);

    /**
     * Update the Fragment.
     */
    void updateFragment();
}
