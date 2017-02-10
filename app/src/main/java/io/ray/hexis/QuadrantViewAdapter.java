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
     * Constructor passing a data set
     * @param data  Data set
     */
    public QuadrantViewAdapter(List<QuadrantItem> data) {
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
}
