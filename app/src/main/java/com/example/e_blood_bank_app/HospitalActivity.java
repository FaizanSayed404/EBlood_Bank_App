package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.widget.SearchView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.e_blood_bank_app.Adapter.RecyclerViewAdapter;
import com.example.e_blood_bank_app.ModelClass.HospitalModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HospitalActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<HospitalModel> hospitalArrayList;
    FirebaseDatabase database;
    DatabaseReference reference;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("Hospitals");
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recyclerView);
        hospitalArrayList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter();
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance("https://e-blood-bank-807ed-default-rtdb.firebaseio.com/");
        reference = database.getReference();
        reference.child("Hospital").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalArrayList.clear(); // Clear previous data
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    HospitalModel hospitalModel=snapshot1.getValue(HospitalModel.class);
                    hospitalArrayList.add(hospitalModel);
                }
                Log.d("HospitalActivity", "Fetched hospitals: " + hospitalArrayList.size());


                recyclerViewAdapter.setHospitalModels(hospitalArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                lottieAnimationView.setVisibility(View.GONE); // Hide Lottie
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lottieAnimationView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to show the search icon
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Handle search icon functionality
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search hospitals or address...");

        // Set up SearchView listeners for filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("HospitalActivity", "Search query: " + query);
                recyclerViewAdapter.filter(query);  // Implement filter in the adapter
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("HospitalActivity", "Search query change: " + newText);
                recyclerViewAdapter.filter(newText);  // Implement filter in the adapter
                return true;
            }
        });

        return true;
    }

    // For handling the back button press
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    // Sample method to get hospital data (replace with actual data fetch)
//    private List<HospitalModel> getHospitalData() {
//        List<HospitalModel> hospitals = new ArrayList<>();
//        // Add sample data
//        hospitals.add(new HospitalModel("Hospital A", "Address A", "12345"));
//        hospitals.add(new HospitalModel("Hospital B", "Address B", "67890"));
//        return hospitals;
//    }


}