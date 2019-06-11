package com.marius.droidmvp;

import android.os.Bundle;
import android.widget.TextView;

import com.marius.droidmvpframework.MvpActivity;
import com.marius.droidmvpframework.MvpOps;

public class MainActivity extends MvpActivity implements MainMvpOps.BaseRequiredViewOps {
    private MainMvpOps.BaseProvidedPresenterOps mPresenter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        mPresenter.performSetUp();
    }

    @Override
    protected MvpOps.BaseProvidedPresenterOps setUpComponent() {
        MainPresenter presenter = new MainPresenter(this);
        MainInteractor model = new MainInteractor(presenter);
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected void componentInitialized(MvpOps.BaseProvidedPresenterOps pres) {
        mPresenter = (MainMvpOps.BaseProvidedPresenterOps) pres;
    }

    @Override
    public void updateCountDownTv(String duration) {
        textView.setText(duration);
    }
}
