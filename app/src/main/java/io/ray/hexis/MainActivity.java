package io.ray.hexis;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get current view to pass to navigation adapter object
        View v = findViewById(android.R.id.content);

        // Initialize toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize navigation Adapter to handle item selection
        NavigationAdapter nAdapter = new NavigationAdapter(v);

        // Initialize drawer layout object
        // This adds the three horizontal navigation drawer bars in the toolbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();

        // Initialize view pager object that hand one time matrix
        ViewPager mViewPager = (ViewPager) findViewById(R.id.quadrant_view_pager);
        mViewPager.setAdapter(new QuadrantFragmentPagerAdapter(getSupportFragmentManager()));

        // Set the view pager up with a row of tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.quadrant_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        // Set the FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new AddItemOnClickListener(mViewPager));
    }

    // Close navigation drawer when back button is pressed
    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
