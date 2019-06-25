package com.marius.droidmvpframework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface MvpOps {
    interface BaseRequiredViewOps {
        Context context();

        Activity activity();

        String getStringResource(int resourceId);

        Intent getActivityIntent();

        void showToast(String msg, int length);

        void showDialog(String title, String message, int logo, int style);

        void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment,
                         @NonNull String fragmentTag);

        void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment,
                             @NonNull String fragmentTag, @Nullable String backStackStateName);
    }

    interface BaseProvidedPresenterOps {
        void setModel(MvpOps.BaseProvidedModelOps mModel);

        void setView(MvpOps.BaseRequiredViewOps view);

        void onStart();

        void onStop();

        void onCreate(Bundle savedInstanceState);

        void onPause();

        void onResume();

        void onRestart();

        void onDestroy(boolean changingConfigurations);

        void onSaveInstanceState(Bundle outState);
    }

    interface BaseRequiredPresenterOps {
        Activity activity() throws NullPointerException;

        Context context() throws NullPointerException;
    }

    interface BaseProvidedModelOps {

        void onStart();

        void onStop();

        void onCreate(Bundle savedInstanceState);

        void onPause();

        void onResume();

        void onRestart();

        void onDestroy(boolean changingConfigurations);

        void onSaveInstanceState(Bundle outState);
    }
}
