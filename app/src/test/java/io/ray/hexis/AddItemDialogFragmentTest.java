package io.ray.hexis;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import org.apache.tools.ant.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class AddItemDialogFragmentTest {

    @Test public void onClick() throws Exception{
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));
        DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
            .findFragmentByTag("Add Item");

        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.setListener(listener);
        assertNotNull(addItemDialogFragment);

        // Test add item
        listener.addItem("Test item");

        // Test dialog cancel
        dialogFragment.getDialog().cancel();

        dialogFragment.show(activity.getSupportFragmentManager(), "Add Item");

        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
        assertSame(dialog, dialogFragment.getDialog());
        assertNotNull(dialogFragment.getDialog().findViewById(R.id.add_item));
    }

    @Test public void onCreateDialog() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));
        DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
            .findFragmentByTag("Add Item");

        assertNotNull(dialogFragment.onCreateDialog(null));

    }

    @Test public void setListener() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));
        DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
            .findFragmentByTag("Add Item");

        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.setListener(listener);
        assertNotNull(addItemDialogFragment);
    }

    @Test
    public void newInstance() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));

        DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
            .findFragmentByTag("Add Item");
                assertNotNull(dialogFragment);
    }

    @Test
    public void positiveButtonClick() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager()
                .findFragmentByTag("Add Item");

        ((AlertDialog)fragment.getDialog()).getButton(DialogInterface.BUTTON_POSITIVE)
                .performClick();
    }

    @Test
    public void negativeButtonClick() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();

        DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager()
                .findFragmentByTag("Add Item");

        ((AlertDialog) fragment.getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE)
                .performClick();
    }
}
