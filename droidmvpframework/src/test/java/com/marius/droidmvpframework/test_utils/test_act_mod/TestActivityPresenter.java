package com.marius.droidmvpframework.test_utils.test_act_mod;

import com.marius.droidmvpframework.activity.MvpOps;
import com.marius.droidmvpframework.activity.MvpPresenter;

import java.lang.ref.WeakReference;

public class TestActivityPresenter extends MvpPresenter implements TestMvpOps.
        BaseProvidedPresenterOps, TestMvpOps.BaseRequiredPresenterOps {
    private WeakReference<TestMvpOps.BaseRequiredViewOps> mView;
    private TestMvpOps.BaseProvidedModelOps mModel;
    private boolean firstTimeIn = true;

    public TestActivityPresenter(TestMvpOps.BaseRequiredViewOps view) {
        super(view);
        mView = new WeakReference<>(view);
    }

    @Override
    public void performSetup() {
        if (firstTimeIn)
            firstTimeIn = false;
    }

    @Override
    public boolean isFirstTimeIn() {
        return firstTimeIn;
    }

    /**
     * Mvp Setup
     */
    @Override
    public void setView(MvpOps.BaseRequiredViewOps view) {
        super.setView(view);
        mView = new WeakReference<>((TestMvpOps.BaseRequiredViewOps) view);
    }

    @Override
    public void setModel(MvpOps.BaseProvidedModelOps model) {
        super.setModel(model);
        mModel = (TestMvpOps.BaseProvidedModelOps) model;
    }

    private TestMvpOps.BaseRequiredViewOps getView() throws NullPointerException {
        if (mView.get() == null)
            throw new NullPointerException();
        else
            return mView.get();
    }
}
