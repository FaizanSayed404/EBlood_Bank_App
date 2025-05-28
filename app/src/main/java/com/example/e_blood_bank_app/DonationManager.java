package com.example.e_blood_bank_app;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.io.IOException;
import java.math.BigInteger;

import android.content.Context;
import android.util.Log;

public class DonationManager {
    private Web3j web3;
    private Credentials credentials;
    private TransactionManager txManager;
    private ContractGasProvider gasProvider;

    public DonationManager(Context context) {
        web3 = Web3j.build(new HttpService(Web3Utils.INFURA_URL));
        credentials = Credentials.create(Web3Utils.PRIVATE_KEY);
        txManager = new RawTransactionManager(web3, credentials);
        gasProvider = new DefaultGasProvider();
    }

    public void donate(BigDecimal amountInEther, DonationCallback callback) {
        new Thread(() -> {
            try {
                EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                        credentials.getAddress(), DefaultBlockParameterName.LATEST).send();

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
                BigInteger value = Convert.toWei(amountInEther, Convert.Unit.ETHER).toBigInteger();

                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                        nonce, gasProvider.getGasPrice(), gasProvider.getGasLimit(), Web3Utils.CONTRACT_ADDRESS, value);

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                String hexValue = Numeric.toHexString(signedMessage);
                EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();

                if (ethSendTransaction.hasError()) {
                    callback.onError(ethSendTransaction.getError().getMessage());
                } else {
                    callback.onSuccess(ethSendTransaction.getTransactionHash());
                }

            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
}
