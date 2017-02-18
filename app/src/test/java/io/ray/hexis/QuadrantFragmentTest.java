package io.ray.hexis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.view.QuadrantFragment;

import static org.junit.Assert.*;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;


@RunWith(RobolectricTestRunner.class)
@Config(constants =  BuildConfig.class, sdk = 22)
public class QuadrantFragmentTest {
    @Test
    public void newInstance() throws Exception {
        // Get an instance of the fragment
        Fragment fragment = QuadrantFragment.newInstance();

        // Make sure it's not null
        assertNotNull(fragment);
    }

    /**
     * This test doesn't have any assert statements because it's only here to check that the
     * function doesn't crash when all the lines are hit.
     * @throws Exception    Throw an exception to indicate that there was a failure
     */
    @Test
    public void onCreateView() throws Exception {
        // Get an instance of the fragment
        QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();

        // Start its lifecycle
        startFragment(fragment);

        // Add an item
        fragment.addItem(new QuadrantItem("TEST"));

        // Create a bundle get a saved state of the fragment
        Bundle out = new Bundle();
        fragment.onSaveInstanceState(out);

        // Get the layout inflater
        LayoutInflater inflater = fragment.getLayoutInflater(null);

        // Create the view again, but this time with saved data.
        fragment.onCreateView(inflater, null, out);
    }

    @Test
    public void onSaveInstanceState() throws Exception {
        // Get a new instance of the fragment
        QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();

        // Start the fragment
        startFragment(fragment);

        // Add an item
        fragment.addItem(new QuadrantItem("TEST"));

        // Create a bundle and get a saved instance of the fragment
        Bundle out = new Bundle();
        fragment.onSaveInstanceState(out);

        // Assert that the instance isn't null i.e. data has been saved.
        assertNotNull(out.getParcelableArrayList("data"));
    }

    @Test
    public void addItem() throws Exception {
        // Get an instance of the fragment.
        QuadrantFragment fragment = (QuadrantFragment) QuadrantFragment.newInstance();

        // Start its lifecycle
        startFragment(fragment);

        // Add an item to the list
        fragment.addItem(new QuadrantItem("TEST"));

        // Create a bundle and get its saved state
        Bundle out = new Bundle();
        fragment.onSaveInstanceState(out);

        // Get the ArrayList from the bundle
        ArrayList<QuadrantItem> list = out.getParcelableArrayList("data");

        // Check that there is in fact, an item.
        assert list != null;
        assertEquals(1, list.size());
    }

}