package com.marius.droidmvpframework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public abstract class MvpActivity extends AppCompatActivity implements MvpOps.BaseRequiredViewOps {
    private final MvpStateManager mStateManager = new MvpStateManager(getFragmentManager(),
            MvpActivity.class.getName());
    private static final String PRESENTER_KEY = "pres";
    private MvpOps.BaseProvidedPresenterOps mPresenter;

    /**
     * Lifecycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMVP();
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    /**
     * Setup
     */
    protected abstract MvpOps.BaseProvidedPresenterOps setUpComponent();

    private void setupMVP() {
        setUpPresenter();
        componentInitialized(mPresenter);
    }

    private void setUpPresenter() {
        if (mStateManager.firstTimeIn())
            initializse();
        else
            reinitialize();
    }

    private void initializse() {
        mPresenter = setUpComponent();
        mStateManager.put(PRESENTER_KEY, mPresenter);
    }

    private void reinitialize() {
        if (mStateManager.get(PRESENTER_KEY) == null)
            initializse();
        else {
            mPresenter = mStateManager.get(PRESENTER_KEY);
            mPresenter.setView(this);
        }
    }

    protected abstract void componentInitialized(MvpOps.BaseProvidedPresenterOps pres);

    /**
     * Accessors
     */
    @Override
    public Context context() {
        return this;
    }

    @Override
    public Activity activity() {
        return this;
    }

    @Override
    public String getStringResource(int resourceId) {
        return getResources().getString(resourceId);
    }

    @Override
    public Intent getActivityIntent() {
        return getIntent();
    }

    /**
     * Helpers
     */
    @Override
    public void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment,
                            @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    @Override
    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment,
                                @NonNull String fragmentTag, @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public void showToast(String msg, int length) {
        Toast.makeText(this, msg, length).show();
    }

    @Override
    public void showDialog(String title, String message, int logo, int style) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, style);
        builder.setIcon(logo);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("ok", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
