package io.ray.hexis;

import android.support.v4.view.ViewPager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class AddItemOnClickListenerTest {
    @Test public void addItem() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.addItem("Test add");
    }

    @Test
    public void onClick() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.floating_action_button).performClick();
        ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

        AddItemOnClickListener listener = new AddItemOnClickListener(pager);
        listener.onClick(activity.findViewById(R.id.floating_action_button));
    }

}