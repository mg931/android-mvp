package com.marius.droidmvpframework.test_utils.test_frag_mod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marius.droidmvpframework.fragment_v4.MvpFragOps;
import com.marius.droidmvpframework.fragment_v4.MvpFragment;

public class TestFragment extends MvpFragment implements TestFragmentOps.BaseRequiredViewOps {
    private TestFragmentOps.BaseProvidedPresenterOps mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle args) {
        return null;
    }

    @Override
    protected TestFragmentOps.BaseProvidedPresenterOps setUpComponent() {
        TestFragmentPresenter presenter = new TestFragmentPresenter(this);
        TestFagmentInteractor model = new TestFagmentInteractor(presenter);
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected void componentInitialized(MvpFragOps.BaseProvidedPresenterOps pres) {
        mPresenter = (TestFragmentOps.BaseProvidedPresenterOps) pres;
    }
}
