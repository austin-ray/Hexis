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
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.ModifyItemListener;

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

  @BindView(R.id.btn_QI)
  ToggleButton quadOne;
  @BindView(R.id.btn_QII)
  ToggleButton quadTwo;
  @BindView(R.id.btn_QIII)
  ToggleButton quadThree;
  @BindView(R.id.btn_QIV)
  ToggleButton quadFour;

  private ModifyItemListener listener;
  private int selectedQuadrant;


  /**
   * Factory method for creating the DialogFragment with a listener.
   * @param item        QuadrantItem being manipulated
   * @param listener    Listener for passing back the information to update item in QuadrantItem
   * @return            New EditItemDialogFragment instance
   */
  public static DialogFragment newInstance(QuadrantItem item, ModifyItemListener listener) {

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

    selectQuadrant(listener.getQuadrant());

    quadOne.setOnClickListener(e -> selectQuadrant(0));
    quadTwo.setOnClickListener(e -> selectQuadrant(1));
    quadThree.setOnClickListener(e -> selectQuadrant(2));
    quadFour.setOnClickListener(e -> selectQuadrant(3));

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
            listener.updateItem(item, selectedQuadrant);
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
  public void setListener(ModifyItemListener listener) {
    this.listener = listener;
  }

  public void selectQuadrant(int n) {
    // Set selectedQuadrant to what the user chose
    selectedQuadrant = n;

    // Create an array of the ToggleButtons for easy updating
    ToggleButton[] arr = {quadOne, quadTwo, quadThree, quadFour};

    // Update the ToggleButtons based on if they were selected or not
    for (int i = 0; i < arr.length; i++) {
      if (i == selectedQuadrant) {
        arr[i].setChecked(true);
      } else {
        arr[i].setChecked(false);
      }
    }
  }
}
