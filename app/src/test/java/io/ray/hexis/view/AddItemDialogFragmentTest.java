package io.ray.hexis.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.ray.hexis.BuildConfig;
import io.ray.hexis.MainActivity;
import io.ray.hexis.R;
import io.ray.hexis.presenter.AddItemOnClickListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    listener.addItem("Test item", 0);

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
    dialog = AddItemDialogFragment.newInstance(null, null);
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

  @Test
  public void validateInput() throws Exception {
    fab.performClick();
    dialog = getDialog();
    assertNotNull(dialog);

    clickButton(AlertDialog.BUTTON_POSITIVE);

    ((TextView) dialog.getDialog().findViewById(R.id.add_item)).setText("TEST");
    LayoutInflater inflater = activity.getLayoutInflater();
    View root = inflater.inflate(R.layout.fragment_add_item_dialog, null);
    TextView tv = (TextView) root.findViewById(R.id.add_item);
    tv.setText("TEST");
    dialog.getDialog().setContentView(root);
    ((AlertDialog) dialog.getDialog())
        .getButton(AlertDialog.BUTTON_POSITIVE)
        .performClick();
  }

  /**
   * Helper class to return the abstract of AddItemDialogFragment.
   *
   * @return Abstract of AddItemDialogFragment
   */
  private DialogFragment getDialog() {
    return (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("Add Item");
  }

}