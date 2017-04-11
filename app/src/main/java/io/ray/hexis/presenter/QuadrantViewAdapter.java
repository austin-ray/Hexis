package io.ray.hexis.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckedTextView;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.view.QuadrantItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter for a QuadrantFragment.
 */
public class QuadrantViewAdapter extends RecyclerView.Adapter<QuadrantItemViewHolder> {

  private final Listener listener;
  private List<QuadrantItem> data;

  public interface Listener {
    void onItemLongClick(QuadrantItem item);
    void onItemClick(QuadrantItem item, CheckedTextView textView);
  }

  /**
   * Factory method for constructing a QuadrantViewAdapter without a data set.
   *
   * @return QuadrantViewAdapter without a data set
   */
  public static QuadrantViewAdapter newInstance(Listener listener) {
    return new QuadrantViewAdapter(listener);
  }

  /**
   * Factory method for constructing a QuadrantViewAdapter with a data set.
   *
   * @param data Data set for the QuadrantViewAdapter
   * @return QuadrantViewAdapter set up with a data set
   */
  public static QuadrantViewAdapter newInstance(Listener listener, List<QuadrantItem> data) {
    return new QuadrantViewAdapter(listener, data);
  }

  private QuadrantViewAdapter(Listener listener) {
    this.listener = listener;
    this.data = new ArrayList<>();
  }

  /**
   * Constructor passing a data set.
   *
   * @param data Data set
   */
  private QuadrantViewAdapter(Listener listener, List<QuadrantItem> data) {
    this.listener = listener;
    this.data = new ArrayList<>(data);
  }

  @Override
  public QuadrantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Inflate the layout
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_quadrant_item, parent, false);

    // Return a view holder
    return new QuadrantItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(QuadrantItemViewHolder holder, int position) {

    // Set the holder text to item message
    holder.setTextView(data.get(position).getMessage());

    // Handle click of item
    holder.itemView.setOnClickListener((View v) -> {
      listener.onItemClick(data.get(position), (CheckedTextView) holder.itemView.findViewById(R.id.quadrant_item_text));
    });

    // Handle longclick of item
    holder.itemView.setOnLongClickListener(v -> {
      listener.onItemLongClick(data.get(position));
      return true;
    });
  }

  /**
   * Return the item count.
   * @return size of QuadrantItem array list
   */
  @Override
  public int getItemCount() {
    return data.size();
  }

  /**
   * Set the data for the RecyclerView.
   *
   * @param data New data set
   */
  public void setData(List<QuadrantItem> data) {
    this.data = new ArrayList<>(data);
    notifyDataSetChanged();
  }

  /**
   * Return the data set that the ViewAdapter has.
   *
   * @return ViewAdapter data set
   */
  public List<QuadrantItem> getData() {
    return data;
  }

  /**
   * Update item message.
   * @param message message that will be updated in QuadrantItem
   * @param item    Object being manipulated
   */
  public void updateItem(String message, QuadrantItem item) {
    // Update QuadrantItem data array item with new message
    data.get(data.indexOf(item)).setMessage(message);

    // Notify that change to an item has been made
    notifyDataSetChanged();
  }

  /**
   * Delete item based on item id.
   */
  public void removeItem(QuadrantItem item) {
    // Remove item from adapter
    //data.remove(holder.getAdapterPosition());
    data.remove(item);

    // Notify that change to an item has been made
    notifyDataSetChanged();
  }
}