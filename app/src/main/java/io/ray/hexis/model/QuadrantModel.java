package io.ray.hexis.model;

import java.util.ArrayList;
import java.util.List;

import io.ray.hexis.model.abs.IQuadrantModel;

/**
 * Implementation of IQuadrantModel
 */
public class QuadrantModel implements IQuadrantModel {

    // Data set
    private List<QuadrantItem> data;

    /**
     * Constructor specifying an empty data set
     */
    public QuadrantModel() {
        data = new ArrayList<>();
    }

    /**
     * Add an item to the data set
     * @param newItem   New QuadrantItem being added
     */
    @Override
    public void addItem(QuadrantItem newItem) {
        data.add(newItem);
    }

    /**
     * Return the dataset of the IQuadrantModel implementation
     * @return Data
     */
    @Override
    public List<QuadrantItem> getData() {
        return data;
    }

    @Override
    public void setData(List<QuadrantItem> list) {
        data = new ArrayList<>(list);
    }
}
