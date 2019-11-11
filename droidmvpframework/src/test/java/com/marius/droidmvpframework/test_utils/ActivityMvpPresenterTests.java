package com.marius.droidmvpframework.test_utils;

import android.os.Bundle;

import com.marius.droidmvpframework.test_utils.core_utils.AndroidUnitTest;
import com.marius.droidmvpframework.test_utils.test_act_mod.TestActivityPresenter;
import com.marius.droidmvpframework.test_utils.test_act_mod.TestMvpOps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ActivityMvpPresenterTests extends AndroidUnitTest {
    private TestActivityPresenter presenter;
    @Mock
    private TestMvpOps.BaseRequiredViewOps viewMock;
    @Mock
    private TestMvpOps.BaseProvidedModelOps modelMock;
    @Mock
    private Bundle mockInState;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TestActivityPresenter(viewMock);
        presenter.setModel(modelMock);
    }

    @Test
    public void on_create_delegated_to_model() {
        presenter.onCreate(mockInState);
        verify(modelMock).onCreate(mockInState);
    }

    @Test
    public void on_start_delegated_to_model() {
        presenter.onStart();
        verify(modelMock).onStart();
    }

    @Test
    public void on_stop_delegated_to_model() {
        presenter.onStop();
        verify(modelMock).onStop();
    }

    @Test
    public void on_pause_delegated_to_model() {
        presenter.onPause();
        verify(modelMock).onPause();
    }

    @Test
    public void on_resume_delegated_to_model() {
        presenter.onResume();
        verify(modelMock).onResume();
    }

    @Test
    public void on_restart_delegated_to_model() {
        presenter.onRestart();
        verify(modelMock).onRestart();
    }

    @Test
    public void on_destroy_delegated_to_model() {
        presenter.onDestroy(true);
        verify(modelMock).onDestroy(true);
    }

    @Test
    public void on_saved_instance_delegated_to_model() {
        presenter.onSaveInstanceState(mockInState);
        verify(modelMock).onSaveInstanceState(mockInState);
    }
}
