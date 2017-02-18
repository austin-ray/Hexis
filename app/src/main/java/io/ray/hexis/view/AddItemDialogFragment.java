package io.ray.hexis.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;

/**
 * DialogFragment that appears when a user adds an item to a QuadrantFragment
 */
public class AddItemDialogFragment extends DialogFragment {

    @BindView(R.id.add_item)
    EditText newItem;

    @BindView(R.id.btn_QI)
    ToggleButton qI;

    @BindView(R.id.btn_QII)
    ToggleButton qII;

    @BindView(R.id.btn_QIII)
    ToggleButton qIII;

    @BindView(R.id.btn_QIV)
    ToggleButton qIV;

    private int selectedQuadrant = 0;
    private Listener listener;


    /**
     * Listener for the AddItemDialogFragment
     */
    public interface Listener {
        /**
         * Add a QuadrantItem to the current fragment
         * @param message   Message that will be used to construct a QuadrantItem
         */
        void addItem(String message);

        /**
         * Add a QuadrantItem to the current fragment
         * @param message   Message that will be used to construct a QuadrantItem
         * @param quadrantId the quadrant id
         */
        void addItemSpecific(String message, int quadrantId);

        /**
         * @return id of current quadrant
         */
        int getQuadrant();
    }

    /**
     * Factory method for creating the DialogFragment with a listener
     * @param listener  Listener for passing back the information to create a QuadrantItem
     * @return          New AddItemDialogFragment instance
     */
    public static DialogFragment newInstance(Listener listener) {
        DialogFragment dialog = new AddItemDialogFragment();

        // Set the listener
        ((AddItemDialogFragment) dialog).setListener(listener);

        // Return the dialog
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v =  inflater.inflate(R.layout.fragment_add_item_dialog, null);

        ButterKnife.bind(this, v);

        // Get the current quadrant
        selectedQuadrant = listener.getQuadrant();

        // Select the Quadrant
        selectQuadrant(selectedQuadrant);

        // Set the click listeners for all the ToggleButtons
        qI.setOnClickListener(e -> selectQuadrant(0));
        qII.setOnClickListener(e -> selectQuadrant(1));
        qIII.setOnClickListener(e -> selectQuadrant(2));
        qIV.setOnClickListener(e -> selectQuadrant(3));

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
            // Add action buttons
            .setPositiveButton("Add Item", (dialog, id) -> {
                newItem = (EditText) getDialog().findViewById(R.id.add_item);
                listener.addItemSpecific(newItem.getText().toString(), selectedQuadrant);
            })
            .setNegativeButton("Cancel", (dialog, id) -> AddItemDialogFragment.this.getDialog()
                    .cancel());
        return builder.create();
    }

    /**
     * Select a Quadrant, and update the ToggleButtons to reflect user choice.
     * @param n     Selected Quadrant
     */
    private void selectQuadrant(int n) {
        // Set selectedQuadrant to what the user chose
        selectedQuadrant = n;

        // Create an array of the ToggleButtons for easy updating
        ToggleButton[] arr = {qI, qII, qIII, qIV};

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
     * Set the listener for the Dialog
     * @param listener  Listener reference for the DialogFragment
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Get the Listener used by the AddItemDialogFragment
     * @return  Listener
     */
    public Listener getListener() {
        return listener;
    }
}
