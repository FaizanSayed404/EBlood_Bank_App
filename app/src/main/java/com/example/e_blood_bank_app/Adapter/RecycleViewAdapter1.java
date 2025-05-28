package com.example.e_blood_bank_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.ModelClass.BloodBankModel;
import com.example.e_blood_bank_app.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter1 extends RecyclerView.Adapter<RecycleViewAdapter1.ViewHolder> {
    List<BloodBankModel> bloodBankModels = new ArrayList<>();

    public void setBloodBankModels(List<BloodBankModel> bloodBankModels) {
        this.bloodBankModels = bloodBankModels;
    }
    @NonNull
    @Override
    public RecycleViewAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bloodbank_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter1.ViewHolder holder, int position) {
        holder.bankName.setText(bloodBankModels.get(position).BankName);
        holder.bankAdd.setText(bloodBankModels.get(position).BankAdd);
        holder.bankPhno.setText(bloodBankModels.get(position).BankPhn);

    }

    @Override
    public int getItemCount() {
        return bloodBankModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;
        TextView bankAdd;
        TextView bankPhno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankName = itemView.findViewById(R.id.BankName);
            bankAdd = itemView.findViewById(R.id.BankAddress);
            bankPhno = itemView.findViewById(R.id.BankContact);

        }
    }
}
