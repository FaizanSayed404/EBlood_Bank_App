package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_blood_bank_app.ModelClass.SeekerModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestBloodActivity extends AppCompatActivity {

    TextInputEditText editTextPatientName, editTextAge, editTextDiseaseName, editTextAddress, editTextContact;
    Spinner spinnerBloodGroup;

    MaterialButton requestBtn;

    DatabaseReference reference;

    MaterialAlertDialogBuilder builder;

    AlertDialog alertDialog;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar profileToolbar = findViewById(R.id.toolbar);
        builder = new MaterialAlertDialogBuilder(this);
        profileToolbar.setTitle("Request Blood");
        profileToolbar.setNavigationOnClickListener(v -> finish());
        alertDialog = builder
                .setView(getLayoutInflater().inflate(R.layout.custom_dialog, null))
                .setCancelable(false)
                .create();
        reference = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/").getReference("Seekers");
        editTextPatientName = findViewById(R.id.patientNameEt);
        editTextDiseaseName = findViewById(R.id.diseaseNameEt);
        editTextContact = findViewById(R.id.phoneNumberEt);
        editTextAddress = findViewById(R.id.addressEt);
        editTextAge = findViewById(R.id.patientAgeEt);
        requestBtn = findViewById(R.id.requestBtn);
        spinnerBloodGroup = findViewById(R.id.bloodGroup);
        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);
        requestBtn.setOnClickListener(v -> {
            alertDialog.show();
            String patientName = String.valueOf(editTextPatientName.getText());
            String diseaseName = String.valueOf(editTextDiseaseName.getText());
            String phoneNumber = String.valueOf(editTextContact.getText());
            String address = String.valueOf(editTextAddress.getText());
            String age = String.valueOf(editTextAge.getText());
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
            if (patientName.isEmpty() || diseaseName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || age.isEmpty() || bloodGroup.isEmpty() || bloodGroup.equals("Select Blood Group")) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            } else {
                SeekerModel seekerModel = new SeekerModel(patientName, diseaseName, phoneNumber, address, age, bloodGroup);
                reference.child(phoneNumber).setValue(seekerModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RequestBloodActivity.this, "Request Created", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(RequestBloodActivity.this, "Try Again, There Was Some Issue", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}