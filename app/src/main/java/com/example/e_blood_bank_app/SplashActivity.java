package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent intent = new Intent();
            if (user == null) {
                intent.setClass(this, LoginActivity.class);
            } else {
                intent.setClass(this, HomeActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);

    }
}