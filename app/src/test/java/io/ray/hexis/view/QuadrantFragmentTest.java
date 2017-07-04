package io.ray.hexis.view;

import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.ray.hexis.BuildConfig;
import io.ray.hexis.model.QuadrantModel;
import io.ray.hexis.presenter.QuadrantPresenter;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.QuadrantFragment;
import io.ray.hexis.view.abs.IQuadrantFragment;

import static org.junit.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class QuadrantFragmentTest {
  @Test
  public void newInstance() throws Exception {
    // Get an instance of the fragment
    IQuadrantFragment fragment = QuadrantFragment.newInstance();

    // Make sure it's not null
    assertNotNull(fragment);
  }

  /**
   * This test doesn't have any assert statements because it's only here to check that the
   * function doesn't crash when all the lines are hit.
   *
   * @throws Exception Throw an exception to indicate that there was a failure
   */
  @Test
  public void onCreateView() throws Exception {
    // Get an instance of the fragment
    QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();
    fragment.setPresenter(new QuadrantPresenter(0, fragment, new QuadrantModel(), null));

    // Start its lifecycle
    startFragment(fragment);

    assertNotNull(fragment.getView());
  }

  @Test
  public void onSaveInstanceState() throws Exception {
    // Get a new instance of the fragment
    QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();

    IMatrixPresenter presenter = Mockito.mock(IMatrixPresenter.class);

    fragment.setPresenter(new QuadrantPresenter(0, fragment, new QuadrantModel(), presenter));

    // Assert that the presenter has been set correctly
    assertNotNull(fragment.getPresenter());

    // Start the fragment
    startFragment(fragment);

    // Add an item
    fragment.getPresenter().addItem("TEST");

    // Create a bundle and get a saved instance of the fragment
    Bundle out = new Bundle();
    fragment.onSaveInstanceState(out);

    // Assert that the instance isn't null i.e. data has been saved.
    assertNotNull(out.getParcelableArrayList("data"));
  }
}