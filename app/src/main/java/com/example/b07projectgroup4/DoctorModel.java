package com.example.b07projectgroup4;

public class DoctorModel implements Contract.Model{

    private Doctor doctor;
    private boolean is_found;

    @Override
    public void find(String username) {

    }

    @Override
    public boolean getIs_Found() {
        return is_found;
    }

    @Override
    public Helper getField() {
        return doctor;
    }

    @Override
    public boolean getIs_Called() {
        return true;
    }

    @Override
    public boolean validatePassword(String password) {
        if(doctor != null){
            return doctor.getPassword().equals(password);
        }
        return false;
    }
}
