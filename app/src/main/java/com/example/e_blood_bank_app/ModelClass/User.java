package com.example.e_blood_bank_app.ModelClass;

public class User {
    public String uid;

    public String name;
    public String email;
    public String phone;
    public String bloodGroup;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String name, String email, String phone, String bloodGroup) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }
}
