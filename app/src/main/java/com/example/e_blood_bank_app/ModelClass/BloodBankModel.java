package com.example.e_blood_bank_app.ModelClass;

import android.annotation.SuppressLint;

public class BloodBankModel {
    public String BankName;
    public String BankAdd;
    public String BankPhn;

    public BloodBankModel() {

    }

    @SuppressLint("NotConstructor")
    public BloodBankModel(String bankName, String bankAddress, String bankPhone) {
        BankName = bankName;
        BankAdd = bankAddress;
        BankPhn = bankPhone;
    }

    public String getAddress() {
        return BankAdd;
    }

    public String getName() {
        return BankName;
    }

    public String getPhone() {
        return BankPhn;
    }
}
