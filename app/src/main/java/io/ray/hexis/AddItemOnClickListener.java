package io.ray.hexis;

import android.support.v4.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AddItemOnClickListener implements FloatingActionButton.OnClickListener {

    private ViewPager pager;

    public AddItemOnClickListener(ViewPager pager) {
        this.pager = pager;
    }

    @Override
    public void onClick(View v) {
        int currentItem = pager.getCurrentItem();
        QuadrantFragmentPagerAdapter adapter = (QuadrantFragmentPagerAdapter) pager.getAdapter();

        QuadrantFragment currentFragment = (QuadrantFragment)adapter.getItem(currentItem);
        currentFragment.addItem(new QuadrantItem("ADDED ITEM"));

        DialogFragment newFragment = new AddItemDialogFragment();
        newFragment.show(currentFragment.getFragmentManager(), "Add Item");
    }
}
