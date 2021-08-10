package com.example.b07projectgroup4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientPresenter implements Contract.Presenter{

    private Contract.PatientModel model;
    private Contract.PatientView view;

    public PatientPresenter(Contract.PatientModel model, Contract.PatientView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        Pattern valid_username = Pattern.compile("\\w+");
        Matcher username_matcher = valid_username.matcher(username);

        if(!username_matcher.matches()) {
            view.setUsernameError("Invalid Username Entered");
            return;
        }
        if(password.isEmpty()){
            view.setPasswordError("Password Cannot Be Empty");
            return;
        }

        if(model.find(username)){
            if(model.validatePassword(password)){
                view.startNextActivity(model.getPatient());
                return;
            }
            view.setPasswordError("Incorrect Password");
            return;
        }
        view.setUsernameError("Username Not Found");
    }
}
