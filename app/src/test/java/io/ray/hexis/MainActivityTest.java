package io.ray.hexis;

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
  }
}