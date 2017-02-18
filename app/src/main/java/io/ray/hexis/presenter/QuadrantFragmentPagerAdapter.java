package io.ray.hexis.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.ray.hexis.view.QuadrantFragment;

/**
 * View pager object that handles four quadrant fragments to form a
 * time management matrix.
 */
public class QuadrantFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments = new Fragment[4];
    private String[] titles = new String[4];

    /**
     * Default constructor that initializes four blank matrix quadrants and
     * sets their titles to QI, QII, QIII, QIV, respectively
     * @param fm FragmentManager object that handles the transitions.
     */
    public QuadrantFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        // Create four blank fragments
        fragments[0] = QuadrantFragment.newInstance();
        fragments[1] = QuadrantFragment.newInstance();
        fragments[2] = QuadrantFragment.newInstance();
        fragments[3] = QuadrantFragment.newInstance();

        // Set default titles.
        titles[0] = "QI";
        titles[1] = "QII";
        titles[2] = "QIII";
        titles[3] = "QIV";
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
