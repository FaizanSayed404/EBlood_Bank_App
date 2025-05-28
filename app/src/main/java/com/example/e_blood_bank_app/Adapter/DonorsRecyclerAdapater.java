package com.example.e_blood_bank_app.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.ModelClass.DonorModel;
import com.example.e_blood_bank_app.R;

import java.util.ArrayList;
import java.util.List;

public class DonorsRecyclerAdapater extends RecyclerView.Adapter<DonorsRecyclerAdapater.VHolder> {
    List<DonorModel> donorModelList = new ArrayList<>();

    public void setDonorsModelList(List<DonorModel> donorsModelList) {
        this.donorModelList = donorsModelList;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.donors_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.donorNameTv.setText("Name: " + donorModelList.get(position).donorName);
        holder.contactNumberTv.setText("Contact: " + donorModelList.get(position).phoneNumber);
        holder.bloodGroupTv.setText("Blood Group: " + donorModelList.get(position).bloodGroup);
        holder.ageTv.setText("Age: " + donorModelList.get(position).age);
        holder.addressTv.setText("Address: " +donorModelList.get(position).address);
        holder.diseaseNameTv.setText("Disease: " + donorModelList.get(position).diseaseName);
        holder.itemView.setOnClickListener(v -> {
            if (holder.expandableView.getVisibility() == View.GONE) {
                holder.expandableView.setVisibility(View.VISIBLE);
            } else {
                holder.expandableView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorModelList.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView donorNameTv;
        TextView ageTv;
        TextView diseaseNameTv;
        TextView addressTv;
        TextView contactNumberTv;
        TextView bloodGroupTv;
        LinearLayout expandableView;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            donorNameTv = itemView.findViewById(R.id.donorNameTv);
            ageTv = itemView.findViewById(R.id.ageTv);
            diseaseNameTv = itemView.findViewById(R.id.diseaseNameTv);
            addressTv = itemView.findViewById(R.id.addressTv);
            contactNumberTv = itemView.findViewById(R.id.contactNumberTv);
            bloodGroupTv = itemView.findViewById(R.id.bloodGroupTv);
            expandableView = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
