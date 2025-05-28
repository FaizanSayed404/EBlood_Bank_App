package com.example.e_blood_bank_app;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteFunctionCall;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DonationContractWrapper extends Contract {

    private static final String BINARY = "0x"; // Not needed for interaction, only for deployment

    public DonationContractWrapper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, credentials, gasProvider);
    }

    public static DonationContractWrapper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return new DonationContractWrapper(contractAddress, web3j, credentials, gasProvider);
    }

    public RemoteFunctionCall<BigInteger> getDonationsCount() {
        final Function function = new Function(
                "getDonationsCount",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List<Type>> getDonation(int index) {
        final Function function = new Function(
                "getDonation",
                Arrays.asList(new Uint256(BigInteger.valueOf(index))),
                Arrays.asList(
                        new TypeReference<org.web3j.abi.datatypes.Address>() {},
                        new TypeReference<Uint256>() {}
                )
        );
        return executeRemoteCallMultipleValueReturn(function);
    }
}
