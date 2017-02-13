package io.ray.hexis;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class MainActivityTest {
    @Test
    public void onBackPressed() throws Exception {
        // Setup the activity via Robolectric
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        // Find the drawer layout
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);

        // Open the drawer
        drawer.openDrawer(GravityCompat.START);

        // Close the drawer
        drawer.closeDrawer(GravityCompat.START);

        // Click the button
        activity.onBackPressed();
    }

}