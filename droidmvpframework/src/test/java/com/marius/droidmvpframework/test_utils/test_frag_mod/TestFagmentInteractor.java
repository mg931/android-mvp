package com.marius.droidmvpframework.test_utils.test_frag_mod;

import com.marius.droidmvpframework.fragment_v4.MvpFragmentInteractor;

public class TestFagmentInteractor extends MvpFragmentInteractor implements
        TestFragmentOps.BaseProvidedModelOps {

    public TestFagmentInteractor(TestFragmentOps.BaseRequiredPresenterOps presenter) {
        super(presenter);
    }
}
