package io.ray.hexis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter for a QuadrantFragment
 */
public class QuadrantViewAdapter extends RecyclerView.Adapter<QuadrantItemViewHolder> {

    private List<QuadrantItem> data;

    /**
     * Factory method for constructing a QuadrantViewAdapter without a data set
     * @return  QuadrantViewAdapter without a data set
     */
    public static QuadrantViewAdapter newInstance() {
        return new QuadrantViewAdapter();
    }

    /**
     * Factory method for constructing a QuadrantViewAdapter with a data set
     * @param data  Data set for the QuadrantViewAdapter
     * @return      QuadrantViewAdapter set up with a data set
     */
    public static QuadrantViewAdapter newInstance(List<QuadrantItem> data) {
        return new QuadrantViewAdapter(data);
    }

    private QuadrantViewAdapter() {
        data = new ArrayList<>();
    }

    /**
     * Constructor passing a data set
     * @param data  Data set
     */
    private QuadrantViewAdapter(List<QuadrantItem> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public QuadrantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_quadrant_item, null);

        // Return a view holder
        return new QuadrantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuadrantItemViewHolder holder, int position) {
        holder.setTextView(data.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Add an item to the data set
     * @param item  Add an item to the data set
     */
    public void addItem(QuadrantItem item) {
        data.add(item);
        this.notifyDataSetChanged();
    }

    /**
     * Return the data set that the ViewAdapter has
     * @return  ViewAdapter data set
     */
    public List<QuadrantItem> getData() {
        return data;
    }
}
