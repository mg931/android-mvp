package com.marius.droidmvpframework;

import android.os.Bundle;

public abstract class MvpInteractor implements MvpOps.BaseProvidedModelOps {

    public MvpInteractor(MvpOps.BaseRequiredPresenterOps presenter) {
    }

    /**
     * Lifecycle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("MVP MODEL: onCreate");
    }

    @Override
    public void onStart() {
        System.out.println("MVP MODEL: onStart");
    }

    @Override
    public void onStop() {
        System.out.println("MVP MODEL: onStop");
    }

    @Override
    public void onPause() {
        System.out.println("MVP MODEL: onPause");
    }

    @Override
    public void onResume() {
        System.out.println("MVP MODEL: onResume");
    }

    @Override
    public void onRestart() {
        System.out.println("MVP MODEL: onRestart");
    }

    @Override
    public void onDestroy() {
        System.out.println("MVP MODEL: onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("MVP MODEL: onSaveInstanceState");
    }
}
