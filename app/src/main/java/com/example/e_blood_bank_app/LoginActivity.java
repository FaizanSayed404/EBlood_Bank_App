package com.example.e_blood_bank_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPass;
    Button buttonSubmit;
    TextView registerText, forgotPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    MaterialButton btnSendResetLink;

    TextInputEditText etConfirmEmail;

    MaterialTextView textViewStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.email);
        editTextPass = findViewById(R.id.password);
        registerText = findViewById(R.id.Registertext);
        buttonSubmit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressBar);
        forgotPassword = findViewById(R.id.forgotPassword);
        View forgotPassAlertView = LayoutInflater.from(this).inflate(R.layout.forgot_pass_dialog, null);
        ImageView btnCloseDialog = forgotPassAlertView.findViewById(R.id.btnCloseDialog);
        etConfirmEmail = forgotPassAlertView.findViewById(R.id.etConfirmEmail);
        btnSendResetLink = forgotPassAlertView.findViewById(R.id.btnSendResetLink);
        textViewStatus = forgotPassAlertView.findViewById(R.id.textViewStatus);
        AlertDialog forgotPassDialog = new MaterialAlertDialogBuilder(this)
                .setView(forgotPassAlertView)
                .setCancelable(false)
                .create();
        btnCloseDialog.setOnClickListener(view -> {
            etConfirmEmail.setText(null);
            etConfirmEmail.clearFocus();
            textViewStatus.setText(null);
            textViewStatus.setTextColor(Color.WHITE);
            textViewStatus.setVisibility(View.GONE);
            forgotPassDialog.dismiss();
        });
        forgotPassword.setOnClickListener(view -> forgotPassDialog.show());
        btnSendResetLink.setOnClickListener(view -> {
            String emailAddress = String.valueOf(etConfirmEmail.getText());
            if (emailAddress.isEmpty() || emailAddress.isBlank()) {
                textViewStatus.setTextColor(Color.RED);
                textViewStatus.setVisibility(View.VISIBLE);
                textViewStatus.setText("Email Cannot Be Blank");
            } else {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                textViewStatus.setTextColor(Color.YELLOW);
                                textViewStatus.setVisibility(View.VISIBLE);
                                textViewStatus.setText("Password Reset Link Has Been Sent To Your Email.");
                                etConfirmEmail.setText(null);
                            }
                        }).addOnFailureListener(e -> {
                            textViewStatus.setTextColor(Color.RED);
                            textViewStatus.setVisibility(View.VISIBLE);
                            textViewStatus.setText(e.getLocalizedMessage());
                        });
            }
        });
        mAuth = FirebaseAuth.getInstance();
        buttonSubmit.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String pass = editTextPass.getText().toString().trim();
            if (!email.isEmpty() && !pass.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                loginUserWithEmailAndPassword(email, pass);
            } else {
                Toast.makeText(LoginActivity.this, "Password And Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
            }
        });

        registerText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUserWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish(); // Close the login activity
                        }
                    } else {
                        // If login fails
                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
