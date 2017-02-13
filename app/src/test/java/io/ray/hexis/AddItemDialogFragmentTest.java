package io.ray.hexis;


import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import org.apache.tools.ant.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 2/12/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class AddItemDialogFragmentTest {

    @Test
    public void newInstance() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));

        DialogFragment dialogFragment = (DialogFragment) activity.getSupportFragmentManager()
            .findFragmentByTag("Add Item");
                assertNotNull(dialogFragment);
    }
}
