package io.ray.hexis.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.ray.hexis.BuildConfig;
import io.ray.hexis.MainActivity;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.QuadrantViewAdapter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = BuildConfig.BASE_APP_ID, sdk = 25)
public class EditItemDialogFragmentTest {

  private MainActivity activity;
  private RecyclerView recyclerView;
  private DialogFragment dialog;
  private QuadrantViewAdapter adapter;

  /**
   * Pre-test setup.
   */
  @Before
  public void setup() {
    activity = Robolectric.setupActivity(MainActivity.class);
    recyclerView = (RecyclerView) activity.findViewById(R.id.quadrant_list);
    adapter = (QuadrantViewAdapter) recyclerView.getAdapter();
  }

  @Test
  public void newInstance() throws Exception {
    DialogFragment dialog = EditItemDialogFragment.newInstance(null, null, null);
    assertNotNull(dialog);
  }

  @Test
  public void onLongClick() throws Exception {
    List<QuadrantItem> tempList = new ArrayList<>();
    tempList.add(new QuadrantItem("Test"));

    adapter.setData(tempList);
    adapter.notifyDataSetChanged();

    assertEquals(1, adapter.getItemCount());

    QuadrantItemViewHolder vh =
        (QuadrantItemViewHolder) recyclerView.findViewHolderForAdapterPosition(0);

    vh.getTextView().performLongClick();

    assertNotNull(getDialog());
  }

  @Test
  public void editItem() throws Exception {
    adapter.setData(new ArrayList<>());

    List<QuadrantItem> tempList = new ArrayList<>();
    tempList.add(new QuadrantItem("TEST"));

    adapter.setData(tempList);
    adapter.notifyDataSetChanged();

    QuadrantItemViewHolder vh =
        (QuadrantItemViewHolder) recyclerView.findViewHolderForAdapterPosition(0);

    vh.getTextView().performLongClick();

    assertNotNull(getDialog());

    TextView addItem = (TextView) getDialog().getDialog().findViewById(R.id.add_item);
    addItem.setText("UPDATED");

    positiveButtonClick();

    assertEquals("UPDATED", adapter.getData().get(0).getMessage());
  }

  @Test
  public void cancelEditItem() throws Exception {
    adapter.setData(new ArrayList<>());

    List<QuadrantItem> tempList = new ArrayList<>();
    tempList.add(new QuadrantItem("TEST"));

    adapter.setData(tempList);
    adapter.notifyDataSetChanged();

    QuadrantItemViewHolder vh =
        (QuadrantItemViewHolder) recyclerView.findViewHolderForAdapterPosition(0);

    vh.getTextView().performLongClick();

    assertNotNull(getDialog());

    negativeButtonClick();

    assertEquals(null, getDialog());
  }

  @Test
  public void deleteItem() throws Exception {
    adapter.setData(new ArrayList<>());

    List<QuadrantItem> tempList = new ArrayList<>();
    tempList.add(new QuadrantItem("TEST"));

    adapter.setData(tempList);
    adapter.notifyDataSetChanged();

    QuadrantItemViewHolder vh =
        (QuadrantItemViewHolder) recyclerView.findViewHolderForAdapterPosition(0);

    vh.getTextView().performLongClick();

    assertNotNull(getDialog());

    neutralButtonClick();

    assertEquals(0, adapter.getData().size());
  }

  /**
   * Helper class to simulate positive button click in dialog.
   */
  public void positiveButtonClick() {
    clickButton(DialogInterface.BUTTON_POSITIVE);
  }

  /**
   * Helper class to simulate negative button click in dialog.
   */
  public void negativeButtonClick() {
    clickButton(DialogInterface.BUTTON_NEGATIVE);
  }

  /**
   * Helper method to simulate neutral button click in dialog.
   */
  public void neutralButtonClick() {
    clickButton(DialogInterface.BUTTON_NEUTRAL);
  }

  /**
   * Helper class to click defined button in dialog.
   */
  private void clickButton(int button) {
    dialog = getDialog();

    ((AlertDialog) dialog.getDialog()).getButton(button).performClick();
  }

  /**
   * Helper class to return the abstract of AddItemDialogFragment.
   *
   * @return Abstract of AddItemDialogFragment
   */
  private DialogFragment getDialog() {
    return (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("Edit Item");
  }

  @Test
  public void validateInput() throws Exception {
    onLongClick();

    dialog = getDialog();
    assertNotNull(dialog);

    LayoutInflater inflater = activity.getLayoutInflater();
    View root = inflater.inflate(R.layout.fragment_add_item_dialog, null);
    TextView tv = (TextView) root.findViewById(R.id.add_item);
    tv.setText("");

    clickButton(AlertDialog.BUTTON_POSITIVE);

    onLongClick();

    dialog = getDialog();
    assertNotNull(dialog);

    root = inflater.inflate(R.layout.fragment_add_item_dialog, null);
    tv = (TextView) root.findViewById(R.id.add_item);
    tv.setText("TEST");

    clickButton(AlertDialog.BUTTON_POSITIVE);
  }
}
