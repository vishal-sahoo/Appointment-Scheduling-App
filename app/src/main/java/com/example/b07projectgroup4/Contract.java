package com.example.b07projectgroup4;

public interface Contract {
    public interface Model{
        public boolean isFound(String username);
        public boolean validatePassword(String password);
    }

    public interface PatientModel extends Model{
        public Patient getPatient();
    }

    public interface DoctorModel extends Model{
        public Doctor getDoctor();
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public void setUsernameError(String error);
        public void setPasswordError(String error);
    }

    public interface PatientView extends View{
        public void startNextActivity(Patient patient);
    }

    public interface DoctorView extends View{
        public void startNextActivity(Doctor doctor);
    }

    public interface Presenter{
        public void login();
    }
}
