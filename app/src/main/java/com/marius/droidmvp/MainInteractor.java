package com.marius.droidmvp;

import android.os.Handler;

import com.marius.droidmvpframework.MvpInteractor;

public class MainInteractor extends MvpInteractor implements MainMvpOps.BaseProvidedModelOps {
    private MainMvpOps.BaseRequiredPresenterOps mPresenter;
    private boolean countdownRunning = false;
    private int countDown = 10;
    private static final long INTERVAL = 1000;

    public MainInteractor(MainMvpOps.BaseRequiredPresenterOps presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public void performSetUp() {
        mPresenter.countUpdated(countDown);
        if (!countdownRunning) onCountDown();
    }

    private void stopCountDown() {
        countdownRunning = false;
    }

    private void onCountDown() {
        if (countDown == 0)
            stopCountDown();
        else
            runCountInterval();
    }

    private void runCountInterval() {
        countdownRunning = true;
        new Handler().postDelayed(() -> {
            countDown = (countDown - 1);
            mPresenter.countUpdated(countDown);
            onCountDown();
        }, INTERVAL);
    }
}
