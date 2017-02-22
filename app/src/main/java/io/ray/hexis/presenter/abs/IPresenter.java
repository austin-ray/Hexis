package io.ray.hexis.presenter.abs;

public interface IPresenter<T, A> {
    T getFragment();
    A getModel();
}
