package com.example.e_blood_bank_app.ModelClass;

public class DonationModel {

    private String donor;
    private String amount;

    public DonationModel(String donor, String amount) {
        this.donor = donor;
        this.amount = amount;
    }

    public String getDonor() {
        return donor;
    }

    public String getAmount() {
        return amount;
    }
}
