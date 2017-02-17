package io.ray.hexis;

import android.support.v4.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Listener for the FloatingActionButton, and the DialogFragment is spawns.
 */
public class AddItemOnClickListener implements FloatingActionButton.OnClickListener,
        AddItemDialogFragment.Listener {
    private ViewPager pager;

    /**
     * Parameterized constructor that passed a ViewPager object in order to operate
     * @param pager     ViewPager
     */
    public AddItemOnClickListener(ViewPager pager) {
        this.pager = pager;
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
        currentFragment.addItem(new QuadrantItem(message));
    }

    @Override
    public void addItemSpecific(String message, int quadrantId){
        QuadrantFragment specificFragment = getSpecificFragment(quadrantId);

        specificFragment.addItem(new QuadrantItem(message));
    }

    /**
     * Get the currently display fragment in the ViewPager
     * @return  Fragment being displayed
     */
    private QuadrantFragment getCurrentFragment() {
        // Get the number reference of the current item
        int currentItem = pager.getCurrentItem();

        // Get the adapter used by the ViewPager
        QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

        // Return the item.
        return (QuadrantFragment)adapter.getItem(currentItem);
    }

    private QuadrantFragment getSpecificFragment(int quadrantId){

        // Get the adapter used by the ViewPager
        QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

        // Need to set off screen page limit before attempting to access
        pager.setOffscreenPageLimit(4);

        // Return the item.
        return (QuadrantFragment)adapter.getItem(quadrantId);
    }
}
