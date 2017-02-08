package io.ray.hexis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Viewer class that displays one quadrant of a time matrix
 */
public class QuadrantFragment extends Fragment {

    /**
     * Create a new instance of a QuadrantFragment
     * @return New instance of a Quadrant Fragment
     */
    public static Fragment newInstance() {
        return new QuadrantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quadrant, container, false);
    }
}
