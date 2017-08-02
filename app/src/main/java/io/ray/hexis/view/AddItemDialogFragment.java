package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import io.ray.hexis.presenter.abs.IModifyItemListener;

/**
 * DialogFragment that appears when a user adds an item to a QuadrantFragment.
 */
public class AddItemDialogFragment extends ModifyItemDialogFragment
    implements View.OnClickListener {

  /**
   * Factory method for creating the DialogFragment with a listener.
   * @param listener Listener for passing back the information to create a QuadrantItem
   * @return New AddItemDialogFragment instance
   */
  public static DialogFragment newInstance(IModifyItemListener listener, String title) {
    DialogFragment dialog = new AddItemDialogFragment();

    // Set the listener
    ((ModifyItemDialogFragment) dialog).setListener(listener);
    ((ModifyItemDialogFragment) dialog).setTitle(title);

    // Return the dialog
    return dialog;
  }

  // Prevent null exception from occurring after device rotation
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  // Ensure that dialog remains open after device rotation
  @Override
  public void onDestroyView() {
    if (getDialog() != null && getRetainInstance()) {
      getDialog().setDismissMessage(null);
    }
    super.onDestroyView();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = super.initializeDialog(savedInstanceState);

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setPositiveButton("Add Item", null);

    Dialog dialog = builder.create();
    dialog = setPositiveButtonListener(dialog);

    return dialog;
  }

  @Override
  protected void validateInput() {
    if (inputNotEmpty()) {
      listener.addItem(getInput(), selectedQuadrant);
      dismiss();
    }
  }
}
