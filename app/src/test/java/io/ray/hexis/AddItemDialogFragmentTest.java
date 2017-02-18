package io.ray.hexis;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.ray.hexis.presenter.AddItemOnClickListener;
import io.ray.hexis.view.AddItemDialogFragment;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class AddItemDialogFragmentTest {
    private MainActivity activity;
    private ViewPager pager;
    private FloatingActionButton fab;
    private DialogFragment dialog;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
        pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);
        fab = (FloatingActionButton) activity.findViewById(R.id.floating_action_button);
    }

    @Test
    public void onClick() throws Exception {
        fab.performClick();

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));

        dialog = getDialog();

        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.setListener(listener);
        assertNotNull(addItemDialogFragment);

        // Test add item
        listener.addItem("Test item");

        // Test dialog cancel
        dialog.getDialog().cancel();

        dialog.show(activity.getSupportFragmentManager(), "Add Item");
    }

    @Test
    public void onCreateDialog() throws Exception {
        fab.performClick();

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));

        dialog = getDialog();

        assertNotNull(dialog.onCreateDialog(null));
    }

    @Test
    public void setListener() throws Exception {
        AddItemOnClickListener listener = new AddItemOnClickListener(pager);

        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.setListener(listener);

        assertNotNull(addItemDialogFragment.getListener());
    }

    @Test
    public void newInstance() throws Exception {
        dialog = AddItemDialogFragment.newInstance(null);
        assertNotNull(dialog);
    }

    @Test
    public void positiveButtonClick() throws Exception {
        clickButton(DialogInterface.BUTTON_POSITIVE);
    }

    @Test
    public void negativeButtonClick() throws Exception {
        clickButton(DialogInterface.BUTTON_NEGATIVE);
    }

    private void clickButton(int button) throws Exception {
        fab.performClick();
        dialog = getDialog();

        ((AlertDialog) dialog.getDialog()).getButton(button).performClick();
    }

    @Test
    public void clickButtons() throws Exception {
        fab.performClick();
        dialog = getDialog();

        dialog.getDialog().findViewById(R.id.btn_QI).performClick();
        dialog.getDialog().findViewById(R.id.btn_QII).performClick();
        dialog.getDialog().findViewById(R.id.btn_QIII).performClick();
        dialog.getDialog().findViewById(R.id.btn_QIV).performClick();
    }

    /**
     * Helper class to return the abstract of AddItemDialogFragment
     * @return Abstract of AddItemDialogFragment
     */
    private DialogFragment getDialog() {
        return (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("Add Item");
    }
}