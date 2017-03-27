package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.util.QuadrantItemReader;
import io.ray.hexis.util.SqlLiteHelper;

/**
 * DialogFragment that appears when a user edits an item in a QuadrantFragment.
 */
public class EditItemDialogFragment extends DialogFragment {

  @BindView(R.id.toggleGroup)
  RadioGroup toggleGroup;

  @BindView(R.id.add_item)
  EditText addItemTextView;

  private Listener listener;

  /**
   * Listener for the EditItemDialogFragment.
   */
  public interface Listener {

    /**
     * Update item message.
     *
     * @param message message that will be updated in QuadrantItem
     * @param itemUID item UID that will be used to update item message in QuadrantItem
     */
    void updateItem(String message, long itemUID);

    /**
     * Delete item.
     *
     * @param itemUID id of item to be deleted
     */
    void deleteItem(long itemUID);
  }

  /**
   * Factory method for creating the DialogFragment with a listener.
   *
   * @param itemUID  Item UID of item to be updated
   * @param listener Listener for passing back the information to update item in QuadrantItem
   * @return New EditItemDialogFragment instance
   */
  public static DialogFragment newInstance(long itemUID, Listener listener) {

    // Initialize new EditItemDialogFragment fragment
    DialogFragment dialog = new EditItemDialogFragment();

    // Pass itemUid to onCreateDialog
    Bundle args = new Bundle();
    args.putLong("itemUID", itemUID);
    dialog.setArguments(args);

    // Set the listener
    ((EditItemDialogFragment) dialog).setListener(listener);

    // Return the dialog
    return dialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    View v = inflater.inflate(R.layout.fragment_add_item_dialog, null);

    // Implement ButterKnife
    ButterKnife.bind(this, v);

    // Retrieve itemUID from arguments passed from newInstance
    long itemUid = getArguments().getLong("itemUID");

    // Initialize sql database helper
    SqlLiteHelper sqlLiteHelper = new SqlLiteHelper(this.getContext());
    QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqlLiteHelper);

    // Hide quadrant buttons
    toggleGroup.setVisibility(View.INVISIBLE);

    // Set addItemText view to item message
    addItemTextView.setText(quadrantItemReader.getItemByUid(itemUid));

    // Build view
    builder.setView(v)
        // Add action buttons
        .setPositiveButton("Update Item", (dialog, id) ->
            listener.updateItem(addItemTextView.getText().toString(), itemUid))
        .setNeutralButton("Delete Item", (dialog, id) ->
            listener.deleteItem(itemUid))
        .setNegativeButton("Cancel",
            (dialog, id) -> EditItemDialogFragment.this.getDialog().cancel());
    return builder.create();
  }

  /**
   * Set the listener for the Dialog.
   *
   * @param listener Listener reference for the DialogFragment
   */
  public void setListener(Listener listener) {
    this.listener = listener;
  }
}
