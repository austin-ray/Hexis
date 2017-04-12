package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;

/**
 * DialogFragment that appears when a user edits an item in a QuadrantFragment.
 */
public class EditItemDialogFragment extends DialogFragment {

  @BindView(R.id.toggleGroup)
  RadioGroup toggleGroup;

  @BindView(R.id.add_item)
  EditText addItemTextView;

  @BindView(R.id.txtDialogTitle)
  TextView dialogTitle;

  private Listener listener;

  /**
   * Listener for the EditItemDialogFragment.
   */
  interface Listener {

    /**
     * Update an item.
     * @param item      Item that will be updated
     */
    void updateItem(QuadrantItem item);

    /**
     * Remove item from the model.
     * @param item    Object of the item being removed
     */
    void removeItem(QuadrantItem item);
  }

  /**
   * Factory method for creating the DialogFragment with a listener.
   * @param item        QuadrantItem being manipulated
   * @param listener    Listener for passing back the information to update item in QuadrantItem
   * @return            New EditItemDialogFragment instance
   */
  public static DialogFragment newInstance(QuadrantItem item, Listener listener) {

    // Initialize new EditItemDialogFragment fragment
    DialogFragment dialog = new EditItemDialogFragment();

    // Pass itemUid to onCreateDialog
    Bundle args = new Bundle();
    args.putParcelable("quadrantItem", item);
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
    View v = inflater.inflate(R.layout.fragment_add_item_dialog, (ViewGroup) getView(), false);

    // Implement ButterKnife
    ButterKnife.bind(this, v);

    // Set title to Edit Item
    dialogTitle.setText(R.string.edit_item);

    // Retrieve itemUID from arguments passed from newInstance
    QuadrantItem item = getArguments().getParcelable("quadrantItem");

    // Hide quadrant buttons
    //toggleGroup.setVisibility(View.INVISIBLE);

    // Set addItemText view to item message
    addItemTextView.setText(item != null ? item.getMessage() : null);

    // Build view
    builder.setView(v)
        // Add action buttons
        .setPositiveButton("Update Item", (dialog, id) -> {
          if (item != null) {
            item.setMessage(addItemTextView.getText().toString());
            listener.updateItem(item);
          }
        })
        .setNeutralButton("Delete Item", (dialog, id) ->
            listener.removeItem(item))
        .setNegativeButton("Cancel",
            (dialog, id) -> EditItemDialogFragment.this.getDialog().cancel());
    return builder.create();
  }

  /**
   * Set the listener for the Dialog.
   * @param listener Listener reference for the DialogFragment
   */
  public void setListener(Listener listener) {
    this.listener = listener;
  }
}
