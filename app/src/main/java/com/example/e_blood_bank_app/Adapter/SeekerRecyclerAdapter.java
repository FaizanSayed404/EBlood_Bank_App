package com.example.e_blood_bank_app.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.ModelClass.SeekerModel;
import com.example.e_blood_bank_app.R;

import java.util.ArrayList;
import java.util.List;

public class SeekerRecyclerAdapter extends RecyclerView.Adapter<SeekerRecyclerAdapter.ViewHolder> {
    List<SeekerModel> seekerModelList = new ArrayList<>();

    public void setSeekerModelList(List<SeekerModel> seekerModelList) {
        this.seekerModelList = seekerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seekers_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.patientNameTv.setText("Name: " + seekerModelList.get(position).patientName);
        holder.contactNumberTv.setText("Contact: " + seekerModelList.get(position).phoneNumber);
        holder.bloodGroupTv.setText("Blood Group: " + seekerModelList.get(position).bloodGroup);
        holder.ageTv.setText("Age: " + seekerModelList.get(position).age);
        holder.addressTv.setText("Address: " +seekerModelList.get(position).address);
        holder.diseaseNameTv.setText("Disease: " + seekerModelList.get(position).diseaseName);
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
        return seekerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTv;
        TextView ageTv;
        TextView diseaseNameTv;
        TextView addressTv;
        TextView contactNumberTv;
        TextView bloodGroupTv;
        LinearLayout expandableView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientNameTv = itemView.findViewById(R.id.patientNameTv);
            ageTv = itemView.findViewById(R.id.ageTv);
            diseaseNameTv = itemView.findViewById(R.id.diseaseNameTv);
            addressTv = itemView.findViewById(R.id.addressTv);
            contactNumberTv = itemView.findViewById(R.id.contactNumberTv);
            bloodGroupTv = itemView.findViewById(R.id.bloodGroupTv);
            expandableView = itemView.findViewById(R.id.expandableLayout);

        }
    }
}
