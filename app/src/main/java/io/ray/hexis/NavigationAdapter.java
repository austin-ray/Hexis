package io.ray.hexis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class to set navigation listeners
 */

public class NavigationAdapter  implements NavigationView.OnNavigationItemSelectedListener {
    Context c;
    @BindView(R.id.nav_view) NavigationView nView;
    @BindView(R.id.drawer_layout) DrawerLayout dLayout;

    public NavigationAdapter(View v){
        // Bind butterknife before attempting to set Navigation Listeners
        ButterKnife.bind(this, v);

        // Set navigationView object to passed view
        NavigationView navigationView = nView;

        // Set navigation view
        navigationView.setNavigationItemSelectedListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_goal) {
            // Handle add goal action here
        } else if (id == R.id.nav_goal1) {
            // Handle open goal action here
        } else if (id == R.id.nav_goal2) {
            // Handle open goal action here
        }

        // Close the navigation drawer after item is selected
        DrawerLayout drawer = dLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
