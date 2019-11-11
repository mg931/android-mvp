package com.marius.droidmvpframework.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MvpStateManager {
    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private MvpStateManager.StateMngFragment mStateMaintainerFrag;
    private boolean mIsRecreating;

    public MvpStateManager(FragmentManager fragmentManager, String stateMaintainerTAG) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintainerTag = stateMaintainerTAG;
    }

    public boolean firstTimeIn() {
        try {
            mStateMaintainerFrag = (MvpStateManager.StateMngFragment) mFragmentManager.get()
                    .findFragmentByTag(mStateMaintainerTag);
            if (mStateMaintainerFrag == null) {
                mStateMaintainerFrag = new MvpStateManager.StateMngFragment();
                mFragmentManager.get().beginTransaction()
                        .add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                mIsRecreating = false;
                return true;
            } else {
                mIsRecreating = true;
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    public MvpOps.BaseProvidedPresenterOps get(String key)  {
        return mStateMaintainerFrag.get(key);
    }

    public static class StateMngFragment extends Fragment {
        private final HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        public MvpOps.BaseProvidedPresenterOps get(String key) {
            return (MvpOps.BaseProvidedPresenterOps) mData.get(key);
        }
    }
}
