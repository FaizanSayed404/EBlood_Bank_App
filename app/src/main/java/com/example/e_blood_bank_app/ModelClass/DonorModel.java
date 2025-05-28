package com.example.e_blood_bank_app.ModelClass;

public class DonorModel {
    public String donorName;
    public String diseaseName;
    public String phoneNumber;
    public String address;
    public String age;

    public String bloodGroup;


    public DonorModel() {
    }

    public DonorModel(String donorName, String diseaseName, String phoneNumber, String address, String age, String bloodGroup) {
        this.donorName = donorName;
        this.diseaseName = diseaseName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
        this.bloodGroup = bloodGroup;
    }
}
