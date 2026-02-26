
# 🩸 E-Blood Bank Application

**E-Blood Bank** is a mobile application built using **Android** and **Blockchain** technologies to streamline the process of **blood donation, searching, and management**. This app connects **blood donors, seekers, and hospitals** on a single platform, offering **real-time updates** and **secure transactions** through blockchain integration.

---

## 🚀 Features

* 🏥 **Hospital Listings**: Browse a list of hospitals and blood banks across Maharashtra with blood availability and contact details.
* 🔍 **Search & Filter**: Find blood by type, hospital, or location with smart filters for easier access.
* 🔄 **Real-Time Data Sync**: Integrated with **Firebase Realtime Database** for instant updates on blood availability and hospital data.
* 🔐 **User Authentication**: Secure login and registration for donors, seekers, and hospitals using **Firebase Authentication**.
* 📄 **Donation History**: View your previous blood donations, track ETH donations, and check transaction status in-app.
* ⛓️ **Blockchain Integration**: Uses **Ethereum blockchain** to record donations, ensuring transparency and security.
* 🗺️ **Location-Based Search**: Integrated with **OpenStreetMap (OSMDroid)** to show hospital locations and routes in real time.

---

## 🛠 Technologies Used

| Component      | Technology Used                                               |
| -------------- | ------------------------------------------------------------- |
| **Frontend**   | Android (Java + XML)                                          |
| **Backend**    | Firebase Realtime Database, Firebase Authentication           |
| **Blockchain** | Ethereum Smart Contracts (Sepolia Testnet), Web3j for Android |
| **Maps**       | OSMDroid, OpenStreetMap                                       |

---

## 📈 How It Works

* **Sign Up / Sign In**: Users register or log in using Firebase Authentication.
* **Search for Blood**: Seekers can search for available blood by type, location, or hospital.
* **Donate Blood**: Donors can view their donation history and contribute via blockchain integration.
* **Blockchain Transparency**: All donation records are stored on the **Ethereum blockchain** for security and transparency.
* **Hospital Management**: Hospitals can update their available blood stock, which syncs in real time across all users.

---

## ⚡ Setup Instructions

To run this project locally:

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/e-blood-bank.git
   ```

2. **Open the project** in **Android Studio**.

3. **Connect to Firebase**:

   * Set up Firebase Authentication and Firebase Realtime Database via the Firebase Console.
   * Download the `google-services.json` file and add it to your `app/` directory.

4. **Add Ethereum Smart Contract Details**:

   * Deploy your smart contract to **Sepolia Testnet** using Remix or Hardhat.
   * Get your **Infura RPC URL**, **contract address**, and **ABI**, and add them to your Java Web3 integration.

5. **Run the app** on an emulator or physical Android device.

## 📲 Download & Install
To use the Smart Blood Bank app on your Android device, download the latest version below:

[![Download APK](https://img.shields.io/badge/Download-APK-green?style=for-the-badge&logo=android)](https://github.com/FaizanSayed404/EBlood_Bank_App/releases/latest/download/app-release.apk)

> **Note:** Since this is a direct installation, you may need to allow "Install from Unknown Sources" in your phone settings.
