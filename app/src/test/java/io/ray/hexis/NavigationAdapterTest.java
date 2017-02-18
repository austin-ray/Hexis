package io.ray.hexis;

import android.view.Menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.ray.hexis.presenter.NavigationAdapter;

@RunWith(RobolectricTestRunner.class)
@Config(constants =  BuildConfig.class, sdk = 22)
public class NavigationAdapterTest {
    @Test
    public void onNavigationItemSelected() throws Exception {
        // Setup the activity
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        // Get the adapter from the activity
        NavigationAdapter adapter = new NavigationAdapter(
                activity.findViewById(android.R.id.content));

        // Get the menu from the navigation view
        Menu menu = adapter.getNavigationView().getMenu();

        // Iterate through the possible menu items
        for (int i = 0; i < menu.size(); i++) {
            adapter.onNavigationItemSelected(menu.getItem(i));
        }
    }

}