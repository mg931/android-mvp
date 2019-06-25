package com.marius.droidmvpframework.activity;

import android.os.Bundle;

public abstract class MvpInteractor implements MvpOps.BaseProvidedModelOps {

    public MvpInteractor(MvpOps.BaseRequiredPresenterOps presenter) {
    }

    /**
     * Lifecycle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onDestroy(boolean changingConfigurations) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
