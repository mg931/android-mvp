package com.marius.droidmvpframework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public interface MvpOps {
    interface BaseRequiredViewOps {
        Context context();

        Activity activity();

        String getStringResource(int resourceId);

        Intent getActivityIntent();

        void showToast(String msg, int length);

        void showDialog(String title, String message, int logo, int style);
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
