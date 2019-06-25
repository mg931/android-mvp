package com.marius.droidmvpframework.fragment_v4;

import android.os.Bundle;

public abstract class MvpFragmentInteractor implements MvpFragOps.BaseProvidedModelOps {
    private MvpFragOps.BaseRequiredPresenterOps mPresenter;

    public MvpFragmentInteractor(MvpFragOps.BaseRequiredPresenterOps presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onActivityCreated(Bundle inState) {

    }

    @Override
    public void onDestroyView() {

    }
}
