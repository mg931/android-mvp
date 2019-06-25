package com.marius.droidmvp;

import com.marius.droidmvpframework.activity.MvpOps;
import com.marius.droidmvpframework.activity.MvpPresenter;

import java.lang.ref.WeakReference;

public class MainPresenter extends MvpPresenter implements MainMvpOps.BaseProvidedPresenterOps,
        MainMvpOps.BaseRequiredPresenterOps  {
    private WeakReference<MainMvpOps.BaseRequiredViewOps> mView;
    private MainMvpOps.BaseProvidedModelOps mModel;

    public MainPresenter(MainMvpOps.BaseRequiredViewOps view) {
        super(view);
        mView = new WeakReference<>(view);
    }

    @Override
    public void performSetUp() {
        mModel.performSetUp();
    }

    @Override
    public void countUpdated(int countDownDuration) {
        try {
             getView().updateCountDownTv(String.valueOf(countDownDuration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mvp Setup
     */
    @Override
    public void setView(MvpOps.BaseRequiredViewOps view) {
        super.setView(view);
        mView = new WeakReference<>((MainMvpOps.BaseRequiredViewOps) view);
    }

    @Override
    public void setModel(MvpOps.BaseProvidedModelOps model) {
        super.setModel(model);
        mModel = (MainMvpOps.BaseProvidedModelOps) model;
    }

    private MainMvpOps.BaseRequiredViewOps getView() throws NullPointerException {
        if (mView.get() == null)
            throw new NullPointerException();
        else
            return mView.get();
    }
}
