package io.ray.hexis.view.abs;

import android.support.v4.app.Fragment;

public interface IFragment {
    /**
     * Convert an interface to a Fragment object
     * @return  Fragment representation of interface
     */
    Fragment toFragment();
}
