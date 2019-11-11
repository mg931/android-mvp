package com.marius.droidmvpframework.fragment_v4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public abstract class MvpFragmenPresenter implements MvpFragOps.BaseProvidedPresenterOps,
        MvpFragOps.BaseRequiredPresenterOps {
    private WeakReference<MvpFragOps.BaseRequiredViewOps> mView;
    private MvpFragOps.BaseProvidedModelOps mModel;

    public MvpFragmenPresenter(MvpFragOps.BaseRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void setModel(MvpFragOps.BaseProvidedModelOps model) {
        mModel = model;
    }

    @Override
    public void setView(MvpFragOps.BaseRequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    private MvpFragOps.BaseRequiredViewOps getBaseView() throws NullPointerException {
        if (mView.get() == null)
            throw new NullPointerException();
        else
            return mView.get();
    }

    /**
     * Accessors
     */
    @Override
    public Context context() {
        return getBaseView().context();
    }

    @Override
    public Activity activity() {
        return getBaseView().activity();
    }

    /**
     * Life cycle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mModel.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        mModel.onStart();
    }

    @Override
    public void onResume() {
        mModel.onResume();
    }

    @Override
    public void onPause() {
        mModel.onPause();
    }

    @Override
    public void onStop() {
        mModel.onStop();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    @Override
    public void onDetach() {
        mModel.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mModel.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle inState) {
        mModel.onActivityCreated(inState);
    }

    @Override
    public void onDestroyView() {
        mModel.onDestroyView();
    }
}
