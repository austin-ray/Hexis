package io.ray.hexis;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize view pager object that hand one time matrix
        ViewPager mViewPager = (ViewPager) findViewById(R.id.quadrant_view_pager);
        mViewPager.setAdapter(new QuadrantFragmentPagerAdapter(getSupportFragmentManager()));

        // Set the view pager up with a row of tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.quadrant_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
