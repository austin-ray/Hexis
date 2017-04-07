package io.ray.hexis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.QuadrantViewAdapter;
import io.ray.hexis.view.EditItemDialogFragment;
import io.ray.hexis.view.QuadrantItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
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
    DialogFragment dialog = EditItemDialogFragment.newInstance(null, null);
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

    vh.itemView.performLongClick();

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

    vh.itemView.performLongClick();

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

    vh.itemView.performLongClick();

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

    vh.itemView.performLongClick();

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
}
