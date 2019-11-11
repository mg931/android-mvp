package com.marius.droidmvpframework.test_utils.core_utils;

import android.app.Activity;
import android.content.Context;

import static org.mockito.Mockito.mock;

public abstract class AndroidUnitTest {
    private Context context = mock(Context.class);

    private Activity activity = mock(Activity.class);

    protected Context getContext() {
        return context;
    }

    protected Activity getActivity() {
        return activity;
    }
}
