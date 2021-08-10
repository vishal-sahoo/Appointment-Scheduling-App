package com.example.b07projectgroup4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientPresenterTest {

    @Mock
    Contract.PatientModel model;

    @Mock
    Contract.PatientView view;

    @Test
    public void invalidUsername(){
        when(view.getUsername()).thenReturn("invalid/char");
        PatientPresenter presenter = new PatientPresenter(model, view);
        presenter.login();
        verify(view).setUsernameError("Invalid Username Entered");
    }

    @Test
    public void emptyPassword(){
        when(view.getUsername()).thenReturn("valid_username");
        when(view.getPassword()).thenReturn("");
        PatientPresenter presenter = new PatientPresenter(model, view);
        presenter.login();
        verify(view).setPasswordError("Password Cannot Be Empty");
    }

    @Test
    public void UsernameNotFound(){
        when(view.getUsername()).thenReturn("new_user");
        when(view.getPassword()).thenReturn("password");
        when(model.isFound("new_user")).thenReturn(false);
        PatientPresenter presenter = new PatientPresenter(model, view);
        presenter.login();
        verify(view).setUsernameError("Username Not Found");
    }

    @Test
    public void wrongPassword(){
        when(view.getUsername()).thenReturn("new_user");
        when(view.getPassword()).thenReturn("password");
        when(model.isFound("new_user")).thenReturn(true);
        when(model.validatePassword("password")).thenReturn(false);
        PatientPresenter presenter = new PatientPresenter(model, view);
        presenter.login();
        verify(view).setPasswordError("Incorrect Password");
    }

    @Test
    public void rightPassword(){
        when(view.getUsername()).thenReturn("new_user");
        when(view.getPassword()).thenReturn("password");
        when(model.isFound("new_user")).thenReturn(true);
        when(model.validatePassword("password")).thenReturn(true);
        PatientPresenter presenter = new PatientPresenter(model, view);
        presenter.login();
        verify(view).startNextActivity(anyObject());
    }

}
