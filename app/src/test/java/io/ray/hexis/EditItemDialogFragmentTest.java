package io.ray.hexis;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.QuadrantModel;
import io.ray.hexis.presenter.QuadrantPresenter;
import io.ray.hexis.presenter.QuadrantViewAdapter;
import io.ray.hexis.view.QuadrantFragment;
import io.ray.hexis.view.QuadrantItemViewHolder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class EditItemDialogFragmentTest {

  private MainActivity activity;
  private RecyclerView recyclerView;
  private DialogFragment dialog;
  private View root;
  @Nullable
  ViewGroup container;
  private QuadrantViewAdapter quadrantViewAdapter;
  private TextView addItem;


  @Before
  public void setup() {
    activity = Robolectric.setupActivity(MainActivity.class);
    recyclerView = (RecyclerView) activity.findViewById(R.id.quadrant_list);

    // Get an instance of the fragment
    QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();
    fragment.setPresenter(new QuadrantPresenter(fragment, new QuadrantModel()));

    // Start its lifecycle
    startFragment(fragment);

    // Add an item
    fragment.addItem("TEST");

    // Create a bundle get a saved state of the fragment
    Bundle out = new Bundle();
    fragment.onSaveInstanceState(out);

    // Get the layout inflater
    LayoutInflater inflater = fragment.getLayoutInflater(null);

    // Create the view again, but this time with saved data.
    fragment.onCreateView(inflater, null, out);

    // Create the root view by inflating the fragment_quadrant layout
    root = inflater.inflate(R.layout.fragment_quadrant, container, false);

    // Set the layout manager
    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

    // Initialize the ViewAdapter
    quadrantViewAdapter = QuadrantViewAdapter.newInstance();

    recyclerView.setAdapter(quadrantViewAdapter);

    List<QuadrantItem> items = new ArrayList<>();
    items.add(new QuadrantItem("TEST"));
    quadrantViewAdapter.setData(items);

  }

  @Test
  public void onLongClick() throws Exception {

    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "TEST");
    QuadrantItemViewHolder holder = (QuadrantItemViewHolder) recyclerView
        .findViewHolderForAdapterPosition(0);
    holder.itemView.performLongClick();
    dialog = getDialog();
    assertNotNull(dialog);
    dialog.getDialog().dismiss();
  }

  @Test
  public void editItem() throws Exception {

    QuadrantItemViewHolder holder = (QuadrantItemViewHolder) recyclerView
        .findViewHolderForAdapterPosition(0);
    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "TEST");
    holder.itemView.performLongClick();
    dialog = getDialog();
    addItem = (TextView) dialog.getDialog().findViewById(R.id.add_item);
    addItem.setText("Updated");
    positiveButtonClick();
    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "Updated");
  }

  @Test
  public void cancelEditItem() throws Exception {

    QuadrantItemViewHolder holder = (QuadrantItemViewHolder) recyclerView
        .findViewHolderForAdapterPosition(0);
    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "TEST");
    holder.itemView.performLongClick();
    dialog = getDialog();
    addItem = (TextView) dialog.getDialog().findViewById(R.id.add_item);
    addItem.setText("Updated");
    negativeButtonClick();
    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "TEST");
  }

  @Test
  public void deleteItem() throws Exception {
    QuadrantItemViewHolder holder = (QuadrantItemViewHolder) recyclerView
        .findViewHolderForAdapterPosition(0);
    assertEquals(quadrantViewAdapter.getData().get(0).getMessage(), "TEST");
    holder.itemView.performLongClick();
    dialog = getDialog();
    addItem = (TextView) dialog.getDialog().findViewById(R.id.add_item);
    addItem.setText("Updated");
    neutralButtonClick();
    assertEquals(quadrantViewAdapter.getData().size(), 0);
  }

  /**
   * Helper class to simulate positive button click in dialog
   */
  public void positiveButtonClick() {
    clickButton(DialogInterface.BUTTON_POSITIVE);
  }

  /**
   * Helper class to simulate negative button click in dialog
   */
  public void negativeButtonClick() {
    clickButton(DialogInterface.BUTTON_NEGATIVE);
  }

  /**
   * Helper method to simulate neutral button click in dialog
   */
  public void neutralButtonClick() { clickButton(DialogInterface.BUTTON_NEUTRAL); }

  /**
   * Helper class to click defined button in dialog
   */
  private void clickButton(int button) {
    dialog = getDialog();

    ((AlertDialog) dialog.getDialog()).getButton(button).performClick();
  }

  /**
   * Helper class to return the abstract of AddItemDialogFragment
   *
   * @return Abstract of AddItemDialogFragment
   */
  private DialogFragment getDialog() {
    return (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("Edit Item");
  }
}
