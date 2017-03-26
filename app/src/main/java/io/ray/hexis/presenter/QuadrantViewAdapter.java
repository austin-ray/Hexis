package io.ray.hexis.presenter;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.QuadrantItemWriter;
import io.ray.hexis.util.SqlLiteHelper;
import io.ray.hexis.view.EditItemDialogFragment;
import io.ray.hexis.view.QuadrantItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter for a QuadrantFragment.
 */
public class QuadrantViewAdapter extends RecyclerView.Adapter<QuadrantItemViewHolder>
    implements EditItemDialogFragment.Listener {

  private List<QuadrantItem> data;
  private Context context;
  private QuadrantItemViewHolder holder;

  /**
   * Factory method for constructing a QuadrantViewAdapter without a data set.
   *
   * @return QuadrantViewAdapter without a data set
   */
  public static QuadrantViewAdapter newInstance() {
    return new QuadrantViewAdapter();
  }

  /**
   * Factory method for constructing a QuadrantViewAdapter with a data set.
   *
   * @param data Data set for the QuadrantViewAdapter
   * @return QuadrantViewAdapter set up with a data set
   */
  public static QuadrantViewAdapter newInstance(List<QuadrantItem> data) {
    return new QuadrantViewAdapter(data);
  }

  private QuadrantViewAdapter() {
    data = new ArrayList<>();
  }

  /**
   * Constructor passing a data set.
   *
   * @param data Data set
   */
  private QuadrantViewAdapter(List<QuadrantItem> data) {
    this.data = new ArrayList<>(data);
  }

  @Override
  public QuadrantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Inflate the layout
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.view_quadrant_item, null);

    // Return a view holder
    return new QuadrantItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(QuadrantItemViewHolder holder, int position) {

    // Set the holder text to item message
    holder.setTextView(data.get(position).getMessage());

    // Handle longclick of item
    holder.itemView.setOnLongClickListener((View v) -> {

      // Set holder to current holder to be used by listener
      this.holder = holder;

      // Set context to holder itemView context
      this.context = holder.itemView.getContext();

      // Set FragmentManager to current fragment
      FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();

      // Get a new instance of the AddItemDialogFragment
      DialogFragment dialog =
          EditItemDialogFragment.newInstance(data.get(position).getUid(), this);
      dialog.show(manager, "Edit Item");
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
   *
   * @param message message that will be updated in QuadrantItem
   * @param itemUid item UID that will be used to update item message in QuadrantItem
   */
  @Override
  public void updateItem(String message, long itemUid) {
    // Initialize sqlLiteHelper
    SqlLiteHelper sqlLiteHelper = new SqlLiteHelper(context);

    // Initialize quadrantItemWriter
    QuadrantItemWriter quadrantItemWriter = new QuadrantItemWriter(sqlLiteHelper);

    // Update item message where item UID matches passed itemUid
    quadrantItemWriter.updateItemText(itemUid, message);

    // Update QuadrantItem data array item with new message
    data.get(holder.getAdapterPosition()).setMessage(message);

    // Notify that change to an item has been made
    notifyItemChanged(holder.getAdapterPosition());
  }
}
