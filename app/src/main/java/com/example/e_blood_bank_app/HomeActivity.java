package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.Adapter.HomeRecycleViewAdapter;
import com.example.e_blood_bank_app.ModelClass.HomeModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeRecycleViewAdapter recycleViewAdapterH;


    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerViewHome);
        recycleViewAdapterH = new HomeRecycleViewAdapter();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        System.out.println("Username: " + user.getDisplayName() + "\n" + user.getEmail() + "\n" + user.getUid() + "\n" + user.getPhoneNumber());
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("Home");
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationIcon(null);
        List<HomeModel> homeModelList = new ArrayList<>();
        homeModelList.add(new HomeModel("Hospitals", R.drawable.hospital_cross));
        homeModelList.add(new HomeModel("Profile", R.drawable.user_ic));
        homeModelList.add(new HomeModel("Blood Banks", R.drawable.blood_bank_ic));
        homeModelList.add(new HomeModel("Donors", R.drawable.donor_ic));
        homeModelList.add(new HomeModel("Map", R.drawable.map_ic));
        homeModelList.add(new HomeModel("Seekers", R.drawable.seeker_ic));
        homeModelList.add(new HomeModel("Request", R.drawable.request_ic));
        homeModelList.add(new HomeModel("Donation", R.drawable.donor_ic));
        homeModelList.add(new HomeModel("About Us", R.drawable.about_us_ic));

        recycleViewAdapterH.setHomeModelList(homeModelList);
        recyclerView.setAdapter(recycleViewAdapterH);
        recycleViewAdapterH.notifyDataSetChanged();




    }

}