package io.ray.hexis.presenter;

import android.support.v4.view.ViewPager;

import io.ray.hexis.BuildConfig;
import io.ray.hexis.MainActivity;
import io.ray.hexis.R;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.presenter.abs.IQuadrantPresenter;
import io.ray.hexis.view.QuadrantFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = BuildConfig.BASE_APP_ID, sdk = 25)
public class AddItemOnClickListenerTest {
  @Test
  public void updateItem() throws Exception {
    QuadrantFragment mockFragment = Mockito.mock(QuadrantFragment.class);
    IQuadrantPresenter mockPresenter = Mockito.mock(IQuadrantPresenter.class);

    Mockito.when(mockFragment.getPresenter()).thenReturn(mockPresenter);

    ViewPager mockPager = Mockito.mock(ViewPager.class);
    QuadrantFragmentPagerAdapter mockAdapter = Mockito.mock(QuadrantFragmentPagerAdapter.class);
    Mockito.when(mockAdapter.getItem(Mockito.anyInt())).thenReturn(mockFragment);
    Mockito.when(mockPager.getAdapter()).thenReturn(mockAdapter);

    AddItemOnClickListener listener = new AddItemOnClickListener(mockPager);
    listener.updateItem(new QuadrantItem("TEST"));
  }

  @Test public void addItem() throws Exception {
    MainActivity activity = Robolectric.setupActivity(MainActivity.class);

    activity.findViewById(R.id.floating_action_button).performClick();
    ViewPager pager = (ViewPager) activity.findViewById(R.id.quadrant_view_pager);

    AddItemOnClickListener listener = new AddItemOnClickListener(pager);
    listener.addItem("Test add", 0);
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