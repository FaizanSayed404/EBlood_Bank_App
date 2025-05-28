package com.example.e_blood_bank_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_blood_bank_app.ModelClass.DonationModel;
import com.example.e_blood_bank_app.R;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder>{
    private final List<DonationModel> donations;

    public DonationAdapter(List<DonationModel> donations) {
        this.donations = donations;
    }

    @NonNull
    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonationModel donation = donations.get(position);
        holder.donorText.setText("Donor: " + donation.getDonor());
        holder.amountText.setText("Amount: " + donation.getAmount() + " ETH");
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView donorText, amountText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            donorText = itemView.findViewById(R.id.txtDonor);
            amountText = itemView.findViewById(R.id.txtAmount);
        }
    }
}
