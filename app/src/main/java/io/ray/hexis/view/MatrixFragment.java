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

import butterknife.BindView;
import butterknife.ButterKnife;

import io.ray.hexis.R;
import io.ray.hexis.model.MatrixModel;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.presenter.AddItemOnClickListener;
import io.ray.hexis.presenter.MatrixPresenter;
import io.ray.hexis.presenter.QuadrantFragmentPagerAdapter;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.abs.IMatrixFragment;

import java.util.ArrayList;
import java.util.List;

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

  /**
   * Creates a new instance of a MatrixFragment given an already existing data set.
   *
   * @param list Pre-existing data set for the fragment.
   * @return Matrix fragment with data already in it.
   */
  public static IMatrixFragment newInstance(List<List<QuadrantItem>> list) {
    IMatrixFragment fragment = new MatrixFragment();

    Bundle args = new Bundle();
    for (int i = 0; i < list.size(); i++) {
      args.putParcelableArrayList(String.valueOf(i), (ArrayList<QuadrantItem>) list.get(i));
    }

    ((Fragment) fragment).setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();

    if (args != null) {
      IMatrixModel model = new MatrixModel();

      for (int i = 0; i < 4; i++) {
        model.setQuadrantModel(i, args.getParcelableArrayList(String.valueOf(i)));
      }

      presenter = new MatrixPresenter(this, model);
    } else {
      presenter = new MatrixPresenter(this, new MatrixModel());
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
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
   * Return this as a fragment. Saves casting elsewhere.
   *
   * @return This, but as a fragment.
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

  @Override
  public ViewPager getPager() {
    return pager;
  }
}
