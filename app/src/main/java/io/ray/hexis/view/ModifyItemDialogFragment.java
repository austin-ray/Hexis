package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.ModifyItemListener;

public class ModifyItemDialogFragment extends DialogFragment
    implements View.OnClickListener {

  protected int selectedQuadrant;
  protected String title;

  protected ModifyItemListener listener;

  @BindView(R.id.add_item)
  EditText inputTextView;

  @BindView(R.id.btn_QI)
  ToggleButton quadOne;
  @BindView(R.id.btn_QII)
  ToggleButton quadTwo;
  @BindView(R.id.btn_QIII)
  ToggleButton quadThree;
  @BindView(R.id.btn_QIV)
  ToggleButton quadFour;
  @BindView(R.id.txtDialogTitle)
  TextView dialogTitle;

  /**
   * Initialize the Dialog.
   * @param savedInstanceState    Saved state
   * @return                      Builder for the dialog
   */
  protected AlertDialog.Builder initializeDialog(Bundle savedInstanceState) {
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View root = inflater.inflate(R.layout.fragment_add_item_dialog, (ViewGroup) getView(), false);

    ButterKnife.bind(this, root);

    // Set title to Add Item
    dialogTitle.setText(title);

    // Get the current and select the current quadrant
    selectQuadrant(listener.getQuadrant());

    if (title.equalsIgnoreCase(getString(R.string.edit_item))) {
      QuadrantItem item = getArguments().getParcelable("quadrantItem");
      inputTextView.setText(item != null ? item.getMessage() : null);
    }

    // Set the click listeners for all the ToggleButtons
    quadOne.setOnClickListener(e -> selectQuadrant(0));
    quadTwo.setOnClickListener(e -> selectQuadrant(1));
    quadThree.setOnClickListener(e -> selectQuadrant(2));
    quadFour.setOnClickListener(e -> selectQuadrant(3));

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(root).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

    return builder;
  }

  /**
   * Set the title of the fragment.
   * @param title   Fragment title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Select a Quadrant, and update the ToggleButtons to reflect user choice.
   *
   * @param n Selected Quadrant
   */
  protected void selectQuadrant(int n) {
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

  /**
   * Set the listener for the Dialog.
   *
   * @param listener Listener reference for the DialogFragment
   */
  public void setListener(ModifyItemListener listener) {
    this.listener = listener;
  }

  /**
   * Get the Listener used by the AddItemDialogFragment.
   *
   * @return Listener
   */
  public ModifyItemListener getListener() {
    return listener;
  }

  /**
   * Check if the input text view is empty.
   * @return    If the input text view is empty
   */
  protected boolean inputNotEmpty() {
    return !inputTextView.getText().toString().isEmpty();
  }

  /**
   * Get the input.
   * @return    String value of the input.
   */
  protected String getInput() {
    return inputTextView.getText().toString();
  }

  /**
   * Set the listener of the positive button.
   * @param dialog  Dialog to set the listener of
   * @return        Dialog with listener added.
   */
  protected Dialog setPositiveButtonListener(Dialog dialog) {
    dialog.setOnShowListener(dialog1 -> {
      Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
      button.setOnClickListener(this);
    });

    return dialog;
  }

  @Override
  public void onClick(View v) {
    validateInput();
  }

  /**
   * Validate the input in the text field.
   */
  protected void validateInput() {
    // DO NOTHING. OVERRIDE THIS METHOD
  }
}
