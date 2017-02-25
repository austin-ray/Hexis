package io.ray.hexis.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ray.hexis.R;
import io.ray.hexis.model.MatrixModel;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.presenter.AddItemOnClickListener;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.presenter.MatrixPresenter;
import io.ray.hexis.presenter.QuadrantFragmentPagerAdapter;
import io.ray.hexis.view.abs.IMatrixFragment;

public class MatrixFragment extends Fragment implements IMatrixFragment {
    @BindView(R.id.floating_action_button)
    FloatingActionButton fab;

    @BindView(R.id.quadrant_tab_layout)
    TabLayout tabs;

    @BindView(R.id.quadrant_view_pager)
    ViewPager pager;

    private IMatrixPresenter presenter;

    public static IMatrixFragment newInstance() {
        return new MatrixFragment();
    }

    public static IMatrixFragment newInstance(List<List<QuadrantItem>> list) {
        IMatrixFragment fragment = new MatrixFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("0", (ArrayList<QuadrantItem>)list.get(0));
        args.putParcelableArrayList("1", (ArrayList<QuadrantItem>)list.get(1));
        args.putParcelableArrayList("2", (ArrayList<QuadrantItem>)list.get(2));
        args.putParcelableArrayList("3", (ArrayList<QuadrantItem>)list.get(3));

        ((Fragment) fragment).setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            IMatrixModel model = new MatrixModel();

            model.setQuadrantModel(0, args.getParcelableArrayList("0"));
            model.setQuadrantModel(1, args.getParcelableArrayList("1"));
            model.setQuadrantModel(2, args.getParcelableArrayList("2"));
            model.setQuadrantModel(3, args.getParcelableArrayList("3"));

            presenter = new MatrixPresenter(this, model);
        } else {
            presenter = new MatrixPresenter(this, new MatrixModel());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matrix, container, false);

        ButterKnife.bind(this, root);

        // Initialize view pager object that hand one time matrix
        pager.setAdapter(new QuadrantFragmentPagerAdapter(getFragmentManager(), presenter));

        // Set the view pager up with a row of tabs
        tabs.setupWithViewPager(pager);

        // Set the FloatingActionButton
        fab.setOnClickListener(new AddItemOnClickListener(pager));

        return root;
    }

    /**
     * Return this as a fragment. Saves some typing elsewhere.
     * @return  This, but as a fragment
     */
    @Override
    public Fragment toFragment() {
        return this;
    }

    @Override
    public IMatrixPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IMatrixPresenter presenter) {
        this.presenter = presenter;
    }
}
