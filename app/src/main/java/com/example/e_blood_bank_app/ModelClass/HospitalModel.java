package com.example.e_blood_bank_app.ModelClass;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class HospitalModel {
    public String HosName;
    public String HosAdd;
    public String HosPhn;

    public HospitalModel() {

    }

    public HospitalModel(String hosName, String hosAddress, String hosPhone) {
        HosName = hosName;
        HosAdd = hosAddress;
        HosPhn = hosPhone;
    }
}
