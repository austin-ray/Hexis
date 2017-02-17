package io.ray.hexis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * DialogFragment that appears when a user adds an item to a QuadrantFragment
 */
public class AddItemDialogFragment extends DialogFragment{
    private EditText newItem;
    private ToggleButton qI;
    private ToggleButton qII;
    private ToggleButton qIII;
    private ToggleButton qIV;
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

        void addItemSpecific(String message, int quadrantId);
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

        qI = (ToggleButton) v.findViewById(R.id.btn_QI);
        qII = (ToggleButton) v.findViewById(R.id.btn_QII);
        qIII = (ToggleButton) v.findViewById(R.id.btn_QIII);
        qIV = (ToggleButton) v.findViewById(R.id.btn_QIV);
        qI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedQuadrant = 0;
                qII.setChecked(false);
                qIII.setChecked(false);
                qIV.setChecked(false);
            }
        });
        qII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedQuadrant = 1;
                qI.setChecked(false);
                qIII.setChecked(false);
                qIV.setChecked(false);
            }
        });
        qIII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedQuadrant = 2;
                qI.setChecked(false);
                qII.setChecked(false);
                qIV.setChecked(false);
            }
        });
        qIV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedQuadrant = 3;
                qII.setChecked(false);
                qIII.setChecked(false);
                qI.setChecked(false);
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

    /**
     * Set the listener for the Dialog
     * @param listener  Listener reference for the DialogFragment
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
