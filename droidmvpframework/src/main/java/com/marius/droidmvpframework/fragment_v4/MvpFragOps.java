package com.marius.droidmvpframework.fragment_v4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public interface MvpFragOps {
    interface BaseRequiredViewOps {
        Context context() throws NullPointerException;

        Activity activity() throws NullPointerException;

        Bundle getFragmentArgs();
    }

    interface BaseProvidedPresenterOps {
        void setModel(MvpFragOps.BaseProvidedModelOps mModel);

        void setView(MvpFragOps.BaseRequiredViewOps view);

        void onCreate(Bundle savedInstanceState);

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();

        void onDetach();

        void onSaveInstanceState(Bundle outState);

        void onActivityCreated(Bundle inState);

        void onDestroyView();
    }

    interface BaseRequiredPresenterOps {
        Activity activity() throws NullPointerException;

        Context context() throws NullPointerException;
    }

    interface BaseProvidedModelOps {

        void onCreate(Bundle savedInstanceState);

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();

        void onDetach();

        void onSaveInstanceState(Bundle outState);

        void onActivityCreated(Bundle inState);

        void onDestroyView();
    }
}
