package io.ray.hexis.model;

import java.util.ArrayList;
import java.util.List;

import io.ray.hexis.model.abs.IQuadrantModel;


public class QuadrantModel implements IQuadrantModel {

    private List<QuadrantItem> data;

    public QuadrantModel() {
        data = new ArrayList<>();
    }

    @Override
    public void addItem(QuadrantItem newItem) {
        data.add(newItem);
    }
}
