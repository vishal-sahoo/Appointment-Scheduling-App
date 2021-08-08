package com.example.b07projectgroup4;

public interface Contract {
    public interface Model{
        public void find(String username);
        public boolean getIs_Found();
        public Helper getField();
        public boolean getIs_Called();
        public boolean validatePassword(String password);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public void setUsernameError(String error);
        public void setPasswordError(String error);
        public void startNextActivity(Helper object);
    }

    public interface Presenter{
        public void login();
    }
}
