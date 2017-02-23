package io.ray.hexis.presenter;

import android.support.v4.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;

import io.ray.hexis.util.SQLiteHelper;
import io.ray.hexis.util.WriteQuadrantItems;
import io.ray.hexis.view.AddItemDialogFragment;
import io.ray.hexis.view.QuadrantFragment;

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
        QuadrantFragment currentFragment = getCurrentFragment();

        // Get a new instance of the AddItemDialogFragment
        DialogFragment dialog = AddItemDialogFragment.newInstance(this);

        // Show the dialog fragment
        dialog.show(currentFragment.getFragmentManager(), "Add Item");
    }

    /**
     * Adds a new QuadrantItem to the current QuadrantFragment
     * @param message   Message that will be used to construct a QuadrantItem
     */
    @Override
    public void addItem(String message) {
        // Get the current fragment
        QuadrantFragment currentFragment = getCurrentFragment();

        // Add a new QuadrantItem to the fragment
        currentFragment.addItem(message);
    }

    /**
     * @param message   Message that will be used to construct a QuadrantItem
     * @param quadrantId the quadrant id
     */
    @Override
    public void addItem(String message, int quadrantId){
        QuadrantFragment specificFragment = getFragment(quadrantId);

        specificFragment.addItem(message);
        WriteQuadrantItems writeQuadrantItems = new WriteQuadrantItems(sqLiteHelper);
        writeQuadrantItems.insertNewItem(1,quadrantId,message);
    }

    /**
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
    private QuadrantFragment getCurrentFragment() {
        return getFragment(pager.getCurrentItem());
    }

    /**
     * @param quadrantId The requested quadrant fragment
     * @return Fragment that holds the requested quadrant
     */
    private QuadrantFragment getFragment(int quadrantId){
        // Get the adapter used by the ViewPager
        QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

        // Return the item.
        return (QuadrantFragment)adapter.getItem(quadrantId);
    }
}
