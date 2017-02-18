package io.ray.hexis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        qI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectQuadrant(0);
            }
        });
        qII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectQuadrant(1);
            }
        });
        qIII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               selectQuadrant(2);
            }
        });
        qIV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               selectQuadrant(3);
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
            // Add action buttons
            .setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    newItem = (EditText) getDialog().findViewById(R.id.add_item);
                    listener.addItemSpecific(newItem.getText().toString(), selectedQuadrant);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    AddItemDialogFragment.this.getDialog().cancel();
                }
            });
        return builder.create();
    }

    private void selectQuadrant(int n) {
        selectedQuadrant = n;
        ToggleButton[] arr = {qI, qII, qIII, qIV};

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

    public Listener getListener() {
        return listener;
    }

}
