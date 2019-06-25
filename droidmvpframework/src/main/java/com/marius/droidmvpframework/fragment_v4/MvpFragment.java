package com.marius.droidmvpframework.fragment_v4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class MvpFragment extends Fragment implements MvpFragOps.BaseRequiredViewOps {
    private MvpFragOps.BaseProvidedPresenterOps mPresenter;

    /**
     * Setup
     */
    protected abstract MvpFragOps.BaseProvidedPresenterOps setUpComponent();

    private void setupMVP() {
        setUpPresenter();
        componentInitialized(mPresenter);
    }

    private void setUpPresenter() {
        if (mPresenter == null)
            initializse();
        else
            reinitialize();
    }

    private void initializse() {
        mPresenter = setUpComponent();
    }

    private void reinitialize() {
        mPresenter.setView(this);
    }

    protected abstract void componentInitialized(MvpFragOps.BaseProvidedPresenterOps pres);

    /**
     * Lifecycle
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupMVP();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle inState) {
        super.onActivityCreated(inState);
        mPresenter.onActivityCreated(inState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    /**
     * Accessors
     */
    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

}
