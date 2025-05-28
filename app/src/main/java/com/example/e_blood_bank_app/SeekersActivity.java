package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.Adapter.SeekerRecyclerAdapter;
import com.example.e_blood_bank_app.ModelClass.HospitalModel;
import com.example.e_blood_bank_app.ModelClass.SeekerModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeekersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SeekerRecyclerAdapter adapter;
    List<SeekerModel> seekerModelList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("Seekers");
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        seekerModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.seekersRecyclerView);
        adapter = new SeekerRecyclerAdapter();
        reference = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/").getReference("Seekers");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SeekerModel seekerModel = snapshot1.getValue(SeekerModel.class);
                    seekerModelList.add(seekerModel);
                }
                adapter.setSeekerModelList(seekerModelList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}