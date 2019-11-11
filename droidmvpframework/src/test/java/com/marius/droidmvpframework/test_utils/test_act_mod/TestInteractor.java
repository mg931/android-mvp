package com.marius.droidmvpframework.test_utils.test_act_mod;

import com.marius.droidmvpframework.activity.MvpInteractor;

public class TestInteractor extends MvpInteractor implements TestMvpOps.BaseProvidedModelOps {
    private TestMvpOps.BaseRequiredPresenterOps mPresenter;

    public TestInteractor(TestMvpOps.BaseRequiredPresenterOps presenter) {
        super(presenter);
        mPresenter = presenter;
    }
}
