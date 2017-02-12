package io.ray.hexis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Andrew on 2/12/2017.
 */

public class AddItemDialogFragment extends DialogFragment{
    private EditText newItem;
    private AddItemOnClickListener addItemOnClickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_add_item_dialog, null))
            // Add action buttons
            .setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    newItem = (EditText) getDialog().findViewById(R.id.add_item);

                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    AddItemDialogFragment.this.getDialog().cancel();
                }
            });
        return builder.create();
    }

}
