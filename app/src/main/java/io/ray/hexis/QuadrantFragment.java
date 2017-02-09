package io.ray.hexis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Viewer class that displays one quadrant of a time matrix
 */
public class QuadrantFragment extends Fragment {

    @BindView(R.id.quadrant_list)
    RecyclerView quadRecView;

    /**
     * Create a new instance of a QuadrantFragment
     * @return New instance of a Quadrant Fragment
     */
    public static Fragment newInstance() {
        return new QuadrantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Create the root view by inflating the fragmant_quadrant layout
        View root = inflater.inflate(R.layout.fragment_quadrant, container, false);

        // Inject the views
        ButterKnife.bind(root);

        return root;
    }
}
