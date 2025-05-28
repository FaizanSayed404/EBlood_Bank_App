package com.example.e_blood_bank_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_blood_bank_app.ModelClass.User;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    Spinner spinnerBloodGroup;
    MaterialButton buttonRegister;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPhone = findViewById(R.id.phone);
        spinnerBloodGroup = findViewById(R.id.bloodGroup);
        buttonRegister = findViewById(R.id.register);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        // Set up blood group spinner
        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        // Set up button click listener
        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

            // Validate inputs
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(RegisterActivity.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(phone) || phone.length() != 10) {
                Toast.makeText(RegisterActivity.this, "Please enter a valid 10-digit phone number!", Toast.LENGTH_SHORT).show();
            } else if (bloodGroup.equals("Select Blood Group")) {
                Toast.makeText(RegisterActivity.this, "Please select a blood group!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(name, email, password, phone, bloodGroup);
            }
        });
    }

    //    private void registerUser(String name, String email, String password, String phone, String bloodGroup) {
//        // Generate a unique ID for the user
//        String userId = databaseReference.push().getKey();
//
//        // Create a User object
//        User user = new User(userId, name, email, password, phone, bloodGroup);
//
//        // Save user to Firebase
//        assert userId != null;
//        databaseReference.child(userId).setValue(user).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
//
//                // Navigate to LoginActivity after a short delay
//                new android.os.Handler().postDelayed(() -> {
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish(); // Close RegisterActivity
//                }, 1000);
//            } else {
//                Toast.makeText(RegisterActivity.this, "Registration Failed! Try again.", Toast.LENGTH_SHORT).show();
//            }
    private void registerUser(String name, String email, String password, String phone, String bloodGroup) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                assert firebaseUser != null;
                String userId = firebaseUser.getUid();
                User user = new User(userId, name, email, phone, bloodGroup);

                databaseReference.child(userId).setValue(user).addOnCompleteListener(databaseTask -> {
                    if (databaseTask.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        new android.os.Handler().postDelayed(() -> {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }, 1000);
                    } else {
                        String error = databaseTask.getException() != null ? databaseTask.getException().getMessage() : "Unknown Error";
                        Toast.makeText(RegisterActivity.this, "Database Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "Authentication Failed: " + Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}