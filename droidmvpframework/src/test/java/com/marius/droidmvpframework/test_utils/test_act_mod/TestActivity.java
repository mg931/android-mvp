package com.marius.droidmvpframework.test_utils.test_act_mod;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.marius.droidmvpframework.activity.MvpActivity;
import com.marius.droidmvpframework.activity.MvpOps;

public class TestActivity extends MvpActivity implements TestMvpOps.BaseRequiredViewOps {
    private TestMvpOps.BaseProvidedPresenterOps mPresenter;
    private boolean firstTimeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this));
        firstTimeIn = mPresenter.isFirstTimeIn();
        mPresenter.performSetup();
    }

    @Override
    protected MvpOps.BaseProvidedPresenterOps setUpComponent() {
        TestActivityPresenter presenter = new TestActivityPresenter(this);
        TestInteractor model = new TestInteractor(presenter);
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected void componentInitialized(MvpOps.BaseProvidedPresenterOps pres) {
        mPresenter = (TestMvpOps.BaseProvidedPresenterOps) pres;
    }

    public boolean getPresenterFirstTimeIn() {
        return firstTimeIn;
    }
}
