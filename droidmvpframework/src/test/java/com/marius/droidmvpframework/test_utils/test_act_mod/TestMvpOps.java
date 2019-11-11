package com.marius.droidmvpframework.test_utils.test_act_mod;

import com.marius.droidmvpframework.activity.MvpOps;

public interface TestMvpOps {
    interface BaseRequiredViewOps extends MvpOps.BaseRequiredViewOps {

    }

    interface BaseProvidedPresenterOps extends MvpOps.BaseProvidedPresenterOps {

        boolean isFirstTimeIn();

        void performSetup();
    }

    interface BaseRequiredPresenterOps extends MvpOps.BaseRequiredPresenterOps {

    }

    interface BaseProvidedModelOps extends MvpOps.BaseProvidedModelOps {

    }
}
