package com.marius.droidmvp;

import com.marius.droidmvpframework.activity.MvpOps;

public interface MainMvpOps {
    interface BaseRequiredViewOps extends MvpOps.BaseRequiredViewOps {

        void updateCountDownTv(String valueOf);
    }

    interface BaseProvidedPresenterOps extends MvpOps.BaseProvidedPresenterOps {

        void performSetUp();
    }

    interface BaseRequiredPresenterOps extends MvpOps.BaseRequiredPresenterOps {

        void countUpdated(int countDownDuration);
    }

    interface BaseProvidedModelOps extends MvpOps.BaseProvidedModelOps {

        void performSetUp();
    }
}
