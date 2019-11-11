package com.marius.droidmvpframework.test_utils.test_frag_mod;

import com.marius.droidmvpframework.fragment_v4.MvpFragmenPresenter;

public class TestFragmentPresenter extends MvpFragmenPresenter
        implements TestFragmentOps.BaseProvidedPresenterOps,
        TestFragmentOps.BaseRequiredPresenterOps {

    public TestFragmentPresenter(TestFragmentOps.BaseRequiredViewOps view) {
        super(view);
    }
}
