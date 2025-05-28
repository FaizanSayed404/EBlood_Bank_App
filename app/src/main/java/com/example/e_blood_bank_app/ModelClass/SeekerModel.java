package com.example.e_blood_bank_app.ModelClass;

public class SeekerModel {
    public String patientName;
    public String diseaseName;
    public String phoneNumber;
    public String address;
    public String age;
    public String bloodGroup;

    public SeekerModel() {
    }

    public SeekerModel(String patientName, String diseaseName, String phoneNumber, String address, String age, String bloodGroup) {
        this.patientName = patientName;
        this.diseaseName = diseaseName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
        this.bloodGroup = bloodGroup;
    }
}
