package com.example.e_blood_bank_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.AboutUsActivity;
import com.example.e_blood_bank_app.BloodBankActivity;
import com.example.e_blood_bank_app.DonationActivity;
import com.example.e_blood_bank_app.DonorsActivity;
import com.example.e_blood_bank_app.HospitalActivity;
import com.example.e_blood_bank_app.MapsActivity;
import com.example.e_blood_bank_app.ModelClass.HomeModel;
import com.example.e_blood_bank_app.ProfileActivity;
import com.example.e_blood_bank_app.R;
import com.example.e_blood_bank_app.RequestActivity;
import com.example.e_blood_bank_app.SeekersActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.ViewHolder> {
    Context context;
    List<HomeModel> homeModelList = new ArrayList<>();

    public void setHomeModelList(List<HomeModel> homeModelList) {
        this.homeModelList = homeModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(homeModelList.get(position).title);
        holder.imageView.setImageResource(homeModelList.get(position).image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                String title = homeModelList.get(position).title;
                if(title.contains("Hospital")){
                     intent = new Intent(context, HospitalActivity.class);
                }else if(title.contains("Profile")){
                     intent = new Intent(context, ProfileActivity.class);
                } else if (title.contains("Blood Bank")) {
                    intent = new Intent(context, BloodBankActivity.class);
                } else if (title.contains("Donor")) {
                    intent = new Intent(context, DonorsActivity.class);
                } else if (title.contains("Map")) {
                    intent = new Intent(context, MapsActivity.class);
                }else if (title.contains("Seeker")) {
                    intent = new Intent(context, SeekersActivity.class);
                }else if (title.contains("Request")) {
                    intent = new Intent(context, RequestActivity.class);
                }else if (title.contains("Donation")) {
                        intent = new Intent(context, DonationActivity.class);
                }else if (title.contains("About Us")) {
                    intent = new Intent(context, AboutUsActivity.class);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.homename);
            imageView =itemView.findViewById(R.id.imageViewhome);
        }
    }
}
