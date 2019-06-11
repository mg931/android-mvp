package com.marius.droidmvpframework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public abstract class MvpPresenter implements MvpOps.BaseProvidedPresenterOps, MvpOps.BaseRequiredPresenterOps {
    private MvpOps.BaseProvidedModelOps baseModel;
    private WeakReference<MvpOps.BaseRequiredViewOps> baseView;

    public MvpPresenter(MvpOps.BaseRequiredViewOps view) {
        baseView = new WeakReference<>(view);
    }

    /**
     * Setup
     */
    @Override
    public void setModel(MvpOps.BaseProvidedModelOps model) {
        baseModel = model;
    }

    @Override
    public void setView(MvpOps.BaseRequiredViewOps view) {
        baseView = new WeakReference<>(view);
    }

    private MvpOps.BaseRequiredViewOps getBaseView() throws NullPointerException {
        if (baseView != null)
            return baseView.get();
        else
            throw new NullPointerException();
    }

    /**
     * Accessors
     */
    @Override
    public Activity activity() throws NullPointerException {
        return getBaseView().activity();
    }

    @Override
    public Context context() throws NullPointerException {
        return getBaseView().activity();
    }

    /**
     * Lifecycle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        baseModel.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        baseModel.onStart();
    }

    @Override
    public void onStop() {
        baseModel.onStop();
    }

    @Override
    public void onPause() {
        baseModel.onPause();
    }

    @Override
    public void onResume() {
        baseModel.onResume();
    }

    @Override
    public void onRestart() {
        baseModel.onRestart();
    }

    @Override
    public void onDestroy() {
        baseModel.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        baseModel.onSaveInstanceState(outState);
    }
}
