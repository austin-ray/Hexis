package io.ray.hexis;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Viewer class that displays one quadrant of a time matrix
 */
public class QuadrantFragment extends Fragment {

    @BindView(R.id.quadrant_list)
    RecyclerView quadRecView;

    private QuadrantViewAdapter quadrantViewAdapter;

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
        // Create the root view by inflating the fragment_quadrant layout
        View root = inflater.inflate(R.layout.fragment_quadrant, container, false);

        // Inject the views
        ButterKnife.bind(this, root);

        // Set the layout manager
        quadRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ViewAdapter based on if there's a data set or not
        if (savedInstanceState == null) {
            quadrantViewAdapter = QuadrantViewAdapter.newInstance();
        } else {
            List<QuadrantItem> items = savedInstanceState.getParcelableArrayList("data");
            quadrantViewAdapter = QuadrantViewAdapter.newInstance(items);
        }

        quadRecView.setAdapter(quadrantViewAdapter);

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put the data set into the OutState
        outState.putParcelableArrayList("data",
                new ArrayList<Parcelable>(quadrantViewAdapter.getData()));
    }

    /**
     * Add an item to the Quadrant
     * @param item  New Item
     */
    public void addItem(QuadrantItem item) {
        quadrantViewAdapter.addItem(item);
    }
}
