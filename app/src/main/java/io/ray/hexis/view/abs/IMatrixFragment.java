package io.ray.hexis.view.abs;

import android.support.v4.view.ViewPager;

import io.ray.hexis.presenter.abs.IMatrixPresenter;

/**
 * Abstract implementation of a MatrixFragment.
 */
public interface IMatrixFragment extends IFragment<IMatrixPresenter> {
  ViewPager getPager();
}
