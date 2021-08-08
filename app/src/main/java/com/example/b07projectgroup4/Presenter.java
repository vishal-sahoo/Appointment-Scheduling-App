package com.example.b07projectgroup4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Presenter implements Contract.Presenter{

    private Contract.Model model;
    private Contract.View view;

    public Presenter(Contract.Model model, Contract.View view){
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

        model.find(username);
        boolean bool = model.getIs_Called();
        while(!bool){
            bool = model.getIs_Called();
        }
        if(model.getIs_Found()){
            if(model.validatePassword(password)){
                view.startNextActivity(model.getField());
                return;
            }
            view.setPasswordError("Incorrect Password");
            return;
        }
        view.setUsernameError("Username Not Found");
    }
}
