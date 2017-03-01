package io.ray.hexis.presenter;

import android.support.v4.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import io.ray.hexis.util.SQLiteHelper;
import io.ray.hexis.util.QuadrantItemWriter;
import io.ray.hexis.view.AddItemDialogFragment;
import io.ray.hexis.view.QuadrantFragment;
import io.ray.hexis.view.abs.IQuadrantFragment;

/**
 * Listener for the FloatingActionButton, and the DialogFragment is spawns.
 */
public class AddItemOnClickListener implements FloatingActionButton.OnClickListener,
        AddItemDialogFragment.Listener {
    private final ViewPager pager;
    private final SQLiteHelper sqLiteHelper;

    /**
     * Parameterized constructor that passed a ViewPager object in order to operate
     * @param pager     ViewPager
     */
    public AddItemOnClickListener(ViewPager pager) {
        this.pager = pager;
        this.sqLiteHelper = new SQLiteHelper(pager.getContext());
        pager.setOffscreenPageLimit(this.pager.getAdapter().getCount());
    }

    /**
     * On clicking the FloatingActionButton
     * @param v     View being clicked
     */
    @Override
    public void onClick(View v) {
        // Get the current fragment being displayed
        IQuadrantFragment currentFragment = getCurrentFragment();

        // Get a new instance of the AddItemDialogFragment
        DialogFragment dialog = AddItemDialogFragment.newInstance(this);

        // Show the dialog fragment
        dialog.show(((Fragment)currentFragment).getFragmentManager(), "Add Item");
    }

    /**
     * Adds a new QuadrantItem to the current QuadrantFragment
     * @param message   Message that will be used to construct a QuadrantItem
     */
    @Override
    public void addItem(String message) {
        // Get the current fragment
        IQuadrantFragment currentFragment = getCurrentFragment();

        // Add a new QuadrantItem to the fragment
        currentFragment.getPresenter().addItem(message);
    }

    /**
     * Add an item to a quadrant with specified message
     * @param message   Message that will be used to construct a QuadrantItem
     * @param quadrantId the quadrant id
     */
    @Override
    public void addItem(String message, int quadrantId){
        IQuadrantFragment specificFragment = getFragment(quadrantId);

        // Initialize QuadrantItemWriter to access database
        QuadrantItemWriter writeQuadrantItems = new QuadrantItemWriter(sqLiteHelper);

        // Insert new item into QuadrantItems table
        long itemUID = writeQuadrantItems.insertNewItem(0, quadrantId, message);

        // The item through the presenter.
        specificFragment.getPresenter().addItem(message, itemUID);

    }

    /**
     * Get the current quadrant id
     * @return quadrant id of current quadrant
     */
    @Override
    public int getQuadrant(){
        return pager.getCurrentItem();
    }

    /**
     * Get the currently display fragment in the ViewPager
     * @return  Fragment being displayed
     */
    private IQuadrantFragment getCurrentFragment() {
        return getFragment(pager.getCurrentItem());
    }

    /**
     * Return a QuadrantFragment based on an ID
     * @param quadrantId The requested quadrant fragment
     * @return Fragment that holds the requested quadrant
     */
    private IQuadrantFragment getFragment(int quadrantId){
        // Get the adapter used by the ViewPager
        QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

        // Return the item.
        return (IQuadrantFragment)adapter.getItem(quadrantId);
    }
}
