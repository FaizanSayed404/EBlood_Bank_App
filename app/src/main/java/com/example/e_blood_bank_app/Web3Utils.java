package com.example.e_blood_bank_app;

public class Web3Utils {
    public static final String INFURA_URL = "https://sepolia.infura.io/v3/a1ad28183af54a089f9a96ab375fc0c0";
    public static final String CONTRACT_ADDRESS = "0xbb6C439ae22212C06549bDb2496D229504052611";
    public static final String PRIVATE_KEY = "ab09d963509aac328082ea806af058e9e70788f5e2c3417b812f515350691911";


    public static final String ABI_JSON = "[\n" +
            "  {\n" +
            "    \"inputs\": [],\n" +
            "    \"stateMutability\": \"nonpayable\",\n" +
            "    \"type\": \"constructor\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"anonymous\": false,\n" +
            "    \"inputs\": [],\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"event\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"inputs\": [],\n" +
            "    \"name\": \"donate\",\n" +
            "    \"outputs\": [],\n" +
            "    \"stateMutability\": \"payable\",\n" +
            "    \"type\": \"function\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"inputs\": [\n" +
            "      {\n" +
            "        \"internalType\": \"uint256\",\n" +
            "        \"name\": \"index\",\n" +
            "        \"type\": \"uint256\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"name\": \"getDonation\",\n" +
            "    \"outputs\": [\n" +
            "      {\n" +
            "        \"internalType\": \"address\",\n" +
            "        \"name\": \"\",\n" +
            "        \"type\": \"address\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"internalType\": \"uint256\",\n" +
            "        \"name\": \"\",\n" +
            "        \"type\": \"uint256\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"stateMutability\": \"view\",\n" +
            "    \"type\": \"function\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"inputs\": [],\n" +
            "    \"name\": \"getDonationsCount\",\n" +
            "    \"outputs\": [\n" +
            "      {\n" +
            "        \"internalType\": \"uint256\",\n" +
            "        \"name\": \"\",\n" +
            "        \"type\": \"uint256\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"stateMutability\": \"view\",\n" +
            "    \"type\": \"function\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"inputs\": [],\n" +
            "    \"name\": \"owner\",\n" +
            "    \"outputs\": [\n" +
            "      {\n" +
            "        \"internalType\": \"address\",\n" +
            "        \"name\": \"\",\n" +
            "        \"type\": \"address\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"stateMutability\": \"view\",\n" +
            "    \"type\": \"function\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"inputs\": [],\n" +
            "    \"name\": \"totalDonations\",\n" +
            "    \"outputs\": [\n" +
            "      {\n" +
            "        \"internalType\": \"uint256\",\n" +
            "        \"name\": \"\",\n" +
            "        \"type\": \"uint256\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"stateMutability\": \"view\",\n" +
            "    \"type\": \"function\"\n" +
            "  }\n" +
            "]";
}
