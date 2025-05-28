package com.example.e_blood_bank_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class RequestActivity extends AppCompatActivity {

    LinearLayout requestBtn, donateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar profileToolbar = findViewById(R.id.toolbar);
        profileToolbar.setTitle("Requests");
        profileToolbar.setNavigationOnClickListener(v -> finish());
        requestBtn = findViewById(R.id.requestBtn);
        donateBtn = findViewById(R.id.donateBtn);
        requestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RequestActivity.this, RequestBloodActivity.class);
            startActivity(intent);
        });
        donateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RequestActivity.this, DonateBloodActivity.class);
            startActivity(intent);
        });
    }
}