package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.e_blood_bank_app.Adapter.RecycleViewAdapter1;
import com.example.e_blood_bank_app.Adapter.RecyclerViewAdapter;
import com.example.e_blood_bank_app.ModelClass.BloodBankModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BloodBankActivity extends AppCompatActivity {
    RecyclerView recyclerViewBank;
    RecycleViewAdapter1 recyclerViewAdapter;
    ArrayList<BloodBankModel> bloodBankArrayList;
    FirebaseDatabase database;
    DatabaseReference reference;
    LottieAnimationView lottieAnimationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("Blood Banks");
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerViewBank = findViewById(R.id.recyclerView2);
        lottieAnimationView = findViewById(R.id.lottieAnimationView1);
        bloodBankArrayList = new ArrayList<>();
        recyclerViewAdapter = new RecycleViewAdapter1();
        lottieAnimationView.setVisibility(View.VISIBLE);
        recyclerViewBank.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/");
        reference = database.getReference();
        reference.child("BloodBank").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                  BloodBankModel bloodBankModel = dataSnapshot.getValue(BloodBankModel.class);
                  bloodBankArrayList.add(bloodBankModel);
                }
                recyclerViewAdapter.setBloodBankModels(bloodBankArrayList);
                recyclerViewBank.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                lottieAnimationView.setVisibility(View.GONE); // Hide Lottie
                recyclerViewBank.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lottieAnimationView.setVisibility(View.GONE);
                recyclerViewBank.setVisibility(View.VISIBLE);

            }
        });
    }
}