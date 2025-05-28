package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_blood_bank_app.ModelClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnKeyListener {
    TextInputEditText emailEt, userNameEt, phoneNumberEt, rePasswordEt;
    MaterialTextView bloodGroupTv, userNameTv;
    LinearLayout confirmPasswordLayout;
    TextInputLayout emailLayout, userNameLayout, phoneNumberEtLayout, rePasswordEtLayout;
    MaterialButton updateBtn, logoutBtn;
    DatabaseReference userDbReference, userReference;
    FirebaseUser user;
    ProgressBar progressIndicator;
    MaterialAlertDialogBuilder builder;
    AlertDialog alertDialog, alertDialogUpdatePass;

    TextInputEditText etCurrentPassword, etNewPassword;
    ImageView btnCloseDialog;
    MaterialButton btnUpdatePasswordDialog, btnUpdatePassword;
    MaterialTextView textViewStatus;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        MaterialToolbar profileToolbar = findViewById(R.id.toolbar);
        profileToolbar.setTitle("Profile");
        profileToolbar.setNavigationOnClickListener(v -> finish());
        builder = new MaterialAlertDialogBuilder(this);
        alertDialog = builder
                .setView(getLayoutInflater().inflate(R.layout.custom_dialog, null))
                .setCancelable(false)
                .create();
        alertDialog.show();
        View updatePassAlertView = LayoutInflater.from(this).inflate(R.layout.update_pass_dialog, null);
        textViewStatus = updatePassAlertView.findViewById(R.id.textViewStatus);
        etCurrentPassword = updatePassAlertView.findViewById(R.id.etCurrentPassword);
        etNewPassword = updatePassAlertView.findViewById(R.id.etNewPassword);
        btnCloseDialog = updatePassAlertView.findViewById(R.id.btnCloseDialog);
        btnUpdatePasswordDialog = updatePassAlertView.findViewById(R.id.btnUpdatePasswordDialog);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        alertDialogUpdatePass = new MaterialAlertDialogBuilder(this)
                .setView(updatePassAlertView)
                .setCancelable(false)
                .create();
        btnCloseDialog.setOnClickListener(view -> {
            etCurrentPassword.clearFocus();
            etNewPassword.clearFocus();
            etNewPassword.setText(null);
            etCurrentPassword.setText(null);
            textViewStatus.setVisibility(View.GONE);
            textViewStatus.setText(null);
            textViewStatus.setTextColor(Color.WHITE);
            alertDialogUpdatePass.dismiss();
        });
        btnUpdatePassword.setOnClickListener(view -> alertDialogUpdatePass.show());
        btnUpdatePasswordDialog.setOnClickListener(view -> {
            String currentPassword = String.valueOf(etCurrentPassword.getText());
            String newPassword = String.valueOf(etNewPassword.getText());
            if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                textViewStatus.setTextColor(Color.RED);
                textViewStatus.setText("Current Password Or New Password Cannot Be Empty");
                textViewStatus.setVisibility(View.VISIBLE);
            } else {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(Objects.requireNonNull(user.getEmail()), currentPassword);
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword).addOnCompleteListener(task3 -> {
                            if (task3.isSuccessful()) {
                                textViewStatus.setTextColor(Color.YELLOW);
                                textViewStatus.setText("Password Successfully Updated");
                                textViewStatus.setVisibility(View.VISIBLE);
                                etNewPassword.setText(null);
                                etCurrentPassword.setText(null);
                            }
                        }).addOnFailureListener(e -> {
                            textViewStatus.setTextColor(Color.RED);
                            textViewStatus.setText(e.getLocalizedMessage());
                            textViewStatus.setVisibility(View.VISIBLE);
                        });
                    }
                }).addOnFailureListener(e -> {
                    textViewStatus.setTextColor(Color.RED);
                    textViewStatus.setText(e.getLocalizedMessage());
                    textViewStatus.setVisibility(View.VISIBLE);
                });
            }
        });
        emailEt = findViewById(R.id.emailEt);
        userNameEt = findViewById(R.id.userNameEt);
        rePasswordEt = findViewById(R.id.reEnterPasswordEt);
        progressIndicator = findViewById(R.id.progressBar);
        rePasswordEtLayout = findViewById(R.id.reEnterPasswordEtLayout);
        confirmPasswordLayout = findViewById(R.id.layoutConfirmPassword);
        phoneNumberEtLayout = findViewById(R.id.phoneNumberEtLayout);
        userNameLayout = findViewById(R.id.userNameEtLayout);
        phoneNumberEt = findViewById(R.id.phoneNumberEt);
        emailLayout = findViewById(R.id.emailEtLayout);
        bloodGroupTv = findViewById(R.id.ProfileBloodG);
        userNameTv = findViewById(R.id.userNameTv);
        updateBtn = findViewById(R.id.updateBtn);
        logoutBtn = findViewById(R.id.logOutBtn);
        emailEt.setText(user.getEmail());
        userDbReference = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/").getReference("Users");
        userReference = userDbReference.child(user.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alertDialog.dismiss();
                userNameEt.setText(String.valueOf(snapshot.child("name").getValue()));
                bloodGroupTv.setText(String.valueOf(snapshot.child("bloodGroup").getValue()));
                phoneNumberEt.setText(String.valueOf(snapshot.child("phone").getValue()));
                userNameTv.setText(String.valueOf(snapshot.child("name").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                alertDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "There Was Some Issue", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        updateBtn.setOnClickListener(view -> {
            if (user != null) {
                String updatedEmail = String.valueOf(emailEt.getText());
                if (!Objects.equals(user.getEmail(), updatedEmail)) {
                    if (confirmPasswordLayout.getVisibility() == View.GONE) {
                        confirmPasswordLayout.setVisibility(View.VISIBLE);
                    } else {
                        String confirmPassword = String.valueOf(rePasswordEt.getText());
                        if (confirmPassword.isEmpty() || confirmPassword.isBlank()) {
                            rePasswordEtLayout.setError("Password Cannot Be Blank");
                        } else {
                            progressIndicator.setVisibility(View.VISIBLE);
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(Objects.requireNonNull(user.getEmail()), confirmPassword);
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            rePasswordEtLayout.setError(null);
                                            confirmPasswordLayout.setVisibility(View.GONE);
                                            user.updateEmail(String.valueOf(emailEt.getText())).addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(String.valueOf(userNameEt.getText()))
                                                            .build();
                                                    user.updateProfile(profileUpdates)
                                                            .addOnCompleteListener(task2 -> {
                                                                if (task2.isSuccessful()) {
                                                                    updateDatabase();
                                                                    progressIndicator.setVisibility(View.GONE);
                                                                }
                                                            });
                                                }
                                            }).addOnFailureListener(e -> {
                                                progressIndicator.setVisibility(View.GONE);
                                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                System.out.println(e.getMessage());
                                            });
                                        } else if (!task.isSuccessful()) {
                                            if (Objects.requireNonNull(Objects.requireNonNull(task.getException()).getLocalizedMessage()).contains("INVALID_LOGIN_CREDENTIALS")) {
                                                rePasswordEtLayout.setError("Invalid Password");
                                            } else {
                                                rePasswordEtLayout.setError(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                                            }
                                            progressIndicator.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    }
                } else {
                    confirmPasswordLayout.setVisibility(View.GONE);
                    progressIndicator.setVisibility(View.VISIBLE);
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(String.valueOf(userNameEt.getText()))
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    updateDatabase();
                                    progressIndicator.setVisibility(View.GONE);
                                }
                            });
                }
            }

        });
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = String.valueOf(emailEt.getText());
                if (email.isBlank() || email.isEmpty()) {
                    emailLayout.setError("Email Cannot Be Empty");
                    updateBtn.setClickable(false);
                } else if (!email.matches("\\w{3,}@\\w{2,}\\.\\w{2,}")) {
                    emailLayout.setError("Invalid Email Format");
                    updateBtn.setClickable(false);
                } else {
                    emailLayout.setError(null);
                    updateBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userName = String.valueOf(userNameEt.getText());
                if (userName.isBlank() || userName.isEmpty()) {
                    userNameLayout.setError("Username Cannot Be Empty");
                    updateBtn.setClickable(false);
                } else if (!userName.matches("[a-zA-Z0-9_\\s]{3,}")) {
                    userNameLayout.setError("Invalid Username Format");
                    updateBtn.setClickable(false);
                } else {
                    userNameLayout.setError(null);
                    updateBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = String.valueOf(phoneNumberEt.getText());
                if (phoneNumber.isBlank() || phoneNumber.isEmpty()) {
                    phoneNumberEtLayout.setError("Username Cannot Be Empty");
                    updateBtn.setClickable(false);
                } else if (!phoneNumber.matches("^\\d{10}$")) {
                    phoneNumberEtLayout.setError("Invalid Username Format");
                    updateBtn.setClickable(false);
                } else {
                    phoneNumberEtLayout.setError(null);
                    updateBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void updateDatabase() {
        String email = user.getEmail();
        String userName = String.valueOf(userNameEt.getText());
        String phone = String.valueOf(phoneNumberEt.getText());
        String bloodG = String.valueOf(bloodGroupTv.getText());
        User updateUser = new User(user.getUid(), userName, email, phone, bloodG);
        userReference.setValue(updateUser);
        Toast.makeText(ProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v.getId() == R.id.emailEt) {
            String email = String.valueOf(emailEt.getText());
            if (email.isBlank() || email.isEmpty()) {
                emailLayout.setError("Email Cannot Be Empty");
                updateBtn.setClickable(false);
            } else if (!email.matches("\\w{3,}@\\w{2,}\\.\\w{2,}")) {
                emailLayout.setError("Invalid Email Format");
                updateBtn.setClickable(false);
            } else {
                emailLayout.setError(null);
                updateBtn.setClickable(true);
            }
        }
        return false;
    }
}