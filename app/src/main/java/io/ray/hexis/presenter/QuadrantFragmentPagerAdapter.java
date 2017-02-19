package io.ray.hexis.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.QuadrantFragment;
import io.ray.hexis.view.abs.IQuadrantFragment;

/**
 * View pager object that handles four quadrant fragments to form a
 * time management matrix.
 */
public class QuadrantFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private IQuadrantFragment[] fragments = new QuadrantFragment[4];
    private String[] titles = new String[4];

    /**
     * Default constructor that initializes four blank matrix quadrants and
     * sets their titles to QI, QII, QIII, QIV, respectively
     * @param fm FragmentManager object that handles the transitions.
     */
    public QuadrantFragmentPagerAdapter(FragmentManager fm, IMatrixPresenter presenter) {
        super(fm);

        // Create the four QuadrantFragments
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = QuadrantFragment.newInstance();
            fragments[i].setPresenter(new QuadrantPresenter(fragments[i], presenter.getQuadrantData(i)));
        }

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
        return fragments[position].toFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
