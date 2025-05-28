package com.example.e_blood_bank_app;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e_blood_bank_app.Adapter.DonationAdapter;
import com.example.e_blood_bank_app.ModelClass.DonationModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import org.web3j.abi.datatypes.Type;


import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;

public class DonationActivity extends AppCompatActivity {

    EditText editAmount;
    Button btnDonate;
    TextView textStatus, textWallet;
    ProgressBar progressBar;
    DonationManager donationManager;
    RecyclerView recyclerView;
    DonationAdapter donationAdapter;
    List<DonationModel> donationList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar profileToolbar = findViewById(R.id.toolbar);
        profileToolbar.setTitle("Donation");
        profileToolbar.setNavigationOnClickListener(v -> finish());

        editAmount = findViewById(R.id.editAmount);
        btnDonate = findViewById(R.id.btnDonate);
        textStatus = findViewById(R.id.textStatus);
        textWallet = findViewById(R.id.textWallet);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.donationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donationAdapter = new DonationAdapter(donationList);
        recyclerView.setAdapter(donationAdapter);

        fetchAllDonations();

        donationManager = new DonationManager(this);

        textWallet.setText("Wallet: " + Web3Utils.CONTRACT_ADDRESS);


        btnDonate.setOnClickListener(v -> {
                    String amtStr = editAmount.getText().toString().trim();
                    if (!amtStr.isEmpty()) {
                        BigDecimal ethAmount = new BigDecimal(amtStr);
                        progressBar.setVisibility(View.VISIBLE);
                        textStatus.setVisibility(View.GONE);

                        donationManager.donate(ethAmount, new DonationCallback() {
                            @Override
                            public void onSuccess(String txHash) {
                                runOnUiThread(() -> {
                                    progressBar.setVisibility(View.GONE);
                                    textStatus.setVisibility(View.VISIBLE);
                                    textStatus.setText("✅ Transaction Sent!\nHash: " + txHash);
                                    editAmount.setText("");
                                    fetchAllDonations();

                                    textStatus.setOnLongClickListener(v -> {
                                        String hash = txHash;
                                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("Transaction Hash", hash);
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(DonationActivity.this, "Hash copied to clipboard!", Toast.LENGTH_SHORT).show();
                                        return true;
                                    });


                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                        fetchAllDonations();
                                    }, 3000);
                                });
                            }

                            @Override
                            public void onError(String error) {
                                runOnUiThread(() -> {
                                    progressBar.setVisibility(View.GONE);
                                    textStatus.setVisibility(View.VISIBLE);
                                    textStatus.setText("❌ Error: " + error);
                                });
                            }
                        });
                    } else {
                        Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    }
                }

        );

    }
    private void fetchAllDonations () {
        new Thread(() -> {
            try {
                Web3j web3 = Web3j.build(new HttpService(Web3Utils.INFURA_URL));
                Credentials credentials = Credentials.create(Web3Utils.PRIVATE_KEY);
                ContractGasProvider gasProvider = new DefaultGasProvider();

                DonationContractWrapper contract = DonationContractWrapper.load(
                        Web3Utils.CONTRACT_ADDRESS,
                        web3,
                        credentials,
                        gasProvider
                );

                BigInteger count = contract.getDonationsCount().send();
                donationList.clear();

                for (int i = 0; i < count.intValue(); i++) {
                    List<Type> donation = contract.getDonation(i).send();

                    String donor = donation.get(0).getValue().toString();
                    BigInteger amountInWei = (BigInteger) donation.get(1).getValue();
                    BigDecimal amountInEth = new BigDecimal(amountInWei).divide(BigDecimal.TEN.pow(18));

                    donationList.add(new DonationModel(donor, amountInEth.toPlainString()));
                }

                runOnUiThread(() -> {donationAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);

                });

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(DonationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }}

