package io.ray.hexis.view.abs;

import android.support.v4.app.Fragment;

public interface IFragment<T> {
  /**
   * Convert an interface to a Fragment object.
   *
   * @return Fragment representation of interface
   */
  Fragment toFragment();

  T getPresenter();

  void setPresenter(T presenter);
}
