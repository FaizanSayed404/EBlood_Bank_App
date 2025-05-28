package com.example.e_blood_bank_app.Adapter;

import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.ModelClass.HospitalModel;
import com.example.e_blood_bank_app.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<HospitalModel> hospitalModels = new ArrayList<>();
    private List<HospitalModel> filteredHospitalModels = new ArrayList<>();

    public void setHospitalModels(List<HospitalModel> hospitalModels) {
        this.hospitalModels = hospitalModels;
        this.filteredHospitalModels = new ArrayList<>(hospitalModels);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredHospitalModels.clear();
        if (TextUtils.isEmpty(query)) {
            filteredHospitalModels.addAll(hospitalModels);
        } else {
            String queryLower = query.toLowerCase();

            for (HospitalModel hospital : hospitalModels) {
                String hospitalName = hospital.HosName.toLowerCase();
                String hospitalAddress = hospital.HosAdd.toLowerCase();

                if (hospitalName.startsWith(queryLower) || hospitalAddress.startsWith(queryLower)) {
                    filteredHospitalModels.add(hospital);
                }
            }
        }
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HospitalModel hospital = filteredHospitalModels.get(position);  // Use filtered list for binding data
        holder.hospitalName.setText(hospital.HosName);
        holder.hospitalAdd.setText(hospital.HosAdd);
        holder.hospitalPhno.setText(hospital.HosPhn);
    }

    @Override
    public int getItemCount() {
        return filteredHospitalModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalName;
        TextView hospitalAdd;
        TextView hospitalPhno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             hospitalName = itemView.findViewById(R.id.HospitalName);
             hospitalAdd = itemView.findViewById(R.id.Address);
             hospitalPhno = itemView.findViewById(R.id.Contact);
        }
    }
}
