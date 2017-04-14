package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.ModifyItemListener;

/**
 * DialogFragment that appears when a user edits an item in a QuadrantFragment.
 */
public class EditItemDialogFragment extends ModifyItemDialogFragment {

  /**
   * Factory method for creating the DialogFragment with a listener.
   * @param item        QuadrantItem being manipulated
   * @param listener    Listener for passing back the information to update item in QuadrantItem
   * @return            New EditItemDialogFragment instance
   */
  public static DialogFragment newInstance(QuadrantItem item, ModifyItemListener listener,
                                           String title) {

    // Initialize new EditItemDialogFragment fragment
    DialogFragment dialog = new EditItemDialogFragment();

    // Pass itemUid to onCreateDialog
    Bundle args = new Bundle();
    args.putParcelable("quadrantItem", item);
    dialog.setArguments(args);

    // Set the listener
    ((EditItemDialogFragment) dialog).setListener(listener);
    ((ModifyItemDialogFragment) dialog).setTitle(title);

    // Return the dialog
    return dialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = super.initializeDialog(savedInstanceState);

    QuadrantItem item = getArguments().getParcelable("quadrantItem");

    // Build view
    builder.setPositiveButton("Update Item", null)
        .setNeutralButton("Delete Item", (dialog, id) -> listener.removeItem(item));

    Dialog dialog = builder.create();
    dialog = setPositiveButtonListener(dialog);

    return dialog;
  }

  @Override
  protected void validateInput() {
    QuadrantItem item = getArguments().getParcelable("quadrantItem");

    if (super.inputNotEmpty() && item != null) {
      item.setMessage(getInput());
      listener.updateItem(item, selectedQuadrant);
      dismiss();
    }
  }
}
