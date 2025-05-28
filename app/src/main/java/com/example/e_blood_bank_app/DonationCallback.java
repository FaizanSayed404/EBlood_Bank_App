package com.example.e_blood_bank_app;

public interface DonationCallback {
    void onSuccess(String txHash);
    void onError(String error);
}
