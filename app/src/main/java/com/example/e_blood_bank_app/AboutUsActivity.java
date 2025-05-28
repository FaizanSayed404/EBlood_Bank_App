package com.example.e_blood_bank_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class AboutUsActivity extends AppCompatActivity {
    TextView email,phone,website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("About Us");
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationOnClickListener(v -> finish());


        email = findViewById(R.id.emailAboutUs);
        email.setOnClickListener(view -> sendEmail());
        phone = findViewById(R.id.phoneAboutUs);
        phone.setOnClickListener(view -> makePhoneCall());
        website = findViewById(R.id.websiteAboutUs);
        website.setOnClickListener(view -> openWebsite());

    }
    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"faizansayed675@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Blood Donation Inquiry");

        try {
            startActivity(Intent.createChooser(intent, "Choose an Email Client"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9833903994"));
        startActivity(intent);
    }

    private void openWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ebloodbank.com"));
        startActivity(intent);
    }


}

