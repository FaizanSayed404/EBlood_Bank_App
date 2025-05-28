package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_blood_bank_app.ModelClass.DonorModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DonateBloodActivity extends AppCompatActivity {

    Spinner spinnerBloodGroup;

    TextInputEditText editTextDonorName, editTextAge, editTextDiseaseName, editTextAddress, editTextContact;

    TextInputLayout ageInputLayout, diseaseInputLayout;
    MaterialButton donateBtn;

    DatabaseReference reference;
    MaterialAlertDialogBuilder builder;
    AlertDialog alertDialog;


    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar profileToolbar = findViewById(R.id.toolbar);
        profileToolbar.setTitle("Donate Blood");
        profileToolbar.setNavigationOnClickListener(v -> finish());
        builder = new MaterialAlertDialogBuilder(this);
        alertDialog = builder
                .setView(getLayoutInflater().inflate(R.layout.custom_dialog, null))
                .setCancelable(false)
                .create();
        reference = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/").getReference("Donors");
        spinnerBloodGroup = findViewById(R.id.bloodGroup);
        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);
        editTextDonorName = findViewById(R.id.donorNameEt);
        editTextDiseaseName = findViewById(R.id.diseaseNameEt);
        diseaseInputLayout = findViewById(R.id.diseaseNameEtLayout);
        editTextContact = findViewById(R.id.phoneNumberEt);
        editTextAddress = findViewById(R.id.addressEt);
        editTextAge = findViewById(R.id.donorAgeEt);
        ageInputLayout = findViewById(R.id.donorAgeEtLayout);
        donateBtn = findViewById(R.id.donateBtn);
        editTextAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int age = Integer.parseInt(String.valueOf(editTextAge.getText()));
                    if (!(age >= 18)) {
                        ageInputLayout.setError("Donors Age Must Be Greater Than 18.");
                        donateBtn.setClickable(false);
                    } else if (!(age < 65)) {
                        ageInputLayout.setError("Donors Age Must Be Less Than 65.");
                        donateBtn.setClickable(false);
                    } else {
                        ageInputLayout.setError(null);
                        donateBtn.setClickable(true);
                    }
                } catch (NumberFormatException e) {
                    ageInputLayout.setError("Invalid Age");
                    donateBtn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextDiseaseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        donateBtn.setOnClickListener(v -> {
            alertDialog.show();
            ArrayList<String> notAllowedDiseases = new ArrayList<>();
            notAllowedDiseases.add("hiv");
            notAllowedDiseases.add("aids");
            notAllowedDiseases.add("diabetes");
            notAllowedDiseases.add("blood pressure");
            notAllowedDiseases.add("malaria");
            String donorName = String.valueOf(editTextDonorName.getText());
            String diseaseName = String.valueOf(editTextDiseaseName.getText());
            String phoneNumber = String.valueOf(editTextContact.getText());
            String address = String.valueOf(editTextAddress.getText());
            String age = String.valueOf(editTextAge.getText());
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
            if (donorName.isEmpty() || diseaseName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || age.isEmpty() || bloodGroup.isEmpty() || bloodGroup.equals("Select Blood Group")) {
                Toast.makeText(DonateBloodActivity.this, "Please Fill All Details First", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            } else if (notAllowedDiseases.contains(diseaseName.toLowerCase())) {
                diseaseInputLayout.setError(String.format("Patient With %s Is Not Eligible As Donor", diseaseName));
                alertDialog.dismiss();
            } else {
                DonorModel donor = new DonorModel(donorName, diseaseName, phoneNumber, address, age, bloodGroup);
                reference.child(phoneNumber).setValue(donor).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DonateBloodActivity.this, "Thanks For Becoming Donor", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(DonateBloodActivity.this, "Try Again, There Was Some Issue", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}