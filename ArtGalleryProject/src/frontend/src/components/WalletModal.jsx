import React, { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axios from "axios";

// 🔐 Authenticated Axios Instance
const axiosInstance = axios.create({
  baseURL: "http://localhost:8085",
});

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// -----------------------------
// Deposit Modal Component
// -----------------------------
const DepositModal = ({ onClose }) => {
  const presetAmounts = [500, 5000, 30000];
  const [selectedAmount, setSelectedAmount] = useState(null);
  const [amount, setAmount] = useState("");

  const handlePresetClick = (amt) => {
    setSelectedAmount(amt);
    setAmount(amt); // auto-fill the input
  };

  const handleSetAmount = () => {
    if (!amount || amount < 500 || amount > 49999) {
      alert("Please enter a valid amount between ₹500 and ₹49,999.");
      return;
    }

    // Redirect
    window.location.href =
      "http://127.0.0.1:5500/ArtGallery/ArtGalleryProject/src/main/resources/templates/orders.html";
  };
  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0 backdrop-blur-3xl flex items-center justify-center z-50"
        onClick={onClose}
      >
        <motion.div
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.9, opacity: 0 }}
          onClick={(e) => e.stopPropagation()}
          className="bg-white text-[#3e2e1e] border-1 rounded-xl p-6 w-full max-w-md shadow-xl space-y-4"
        >
          <div className="flex justify-between items-center mb-2">
            <h2 className="text-xl font-semibold">Deposit</h2>
            <button
              onClick={onClose}
              className="text-[#3e2e1e] text-2xl font-bold"
            >
              ×
            </button>
          </div>

          <div className="bg-[#f0e2d2] rounded-lg p-4">
            <div className="mb-4">
              <h3 className="text-sm font-semibold mb-1">New account</h3>
              <input
                type="text"
                placeholder="UPI ID"
                className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
              />
            </div>

            <div className="grid grid-cols-3 gap-3 mb-4">
              {presetAmounts.map((amt) => (
                <button
                  key={amt}
                  onClick={() => handlePresetClick(amt)}
                  className={`py-2 rounded-md text-sm font-medium ${
                    selectedAmount === amt
                      ? "bg-[#3e2e1e] text-white"
                      : "bg-white hover:bg-orange-100"
                  }`}
                >
                  ₹{amt.toLocaleString()}
                </button>
              ))}
            </div>

            <div className="mb-4">
              <input
                type="number"
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(Number(e.target.value))}
                className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
              />
              <p className="text-xs mt-2 text-gray-400">
                Minimum: ₹500 | Maximum: ₹49,999
              </p>
            </div>
            <div className="flex justify-center mt-4">
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThWX8FGxoSiZEFeky-wDqxpSVpbgGbhEl3TA&s"
                alt="UPI QR Code"
                className="w-40 h-auto rounded-md shadow-md"
              />
            </div>

            <button
              onClick={handleSetAmount}
              className="w-full bg-[#3e2e1e] mt-5 hover:bg-[#8d7a67] text-white font-semibold py-2 rounded-md"
            >
              Set amount
            </button>
          </div>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

// -----------------------------
// Wallet Modal Component
// -----------------------------
const WalletModal = ({ isOpen, onClose }) => {
  const [activeTab, setActiveTab] = useState("overview");
  const [balance, setBalance] = useState("₹0.00");
  const [showDepositModal, setShowDepositModal] = useState(false);

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (isOpen) {
      const token = localStorage.getItem("token");
      if (!token) return;

      axios
        .get(`http://localhost:8085/wallet`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          const rawBalance = parseFloat(res.data.balance || 0);
          setBalance(`₹${rawBalance.toFixed(2)}`);
        })
        .catch((err) => {
          console.error("Failed to fetch wallet balance:", err);
          setBalance("₹0.00");
        });
    }
  }, [isOpen]);

  const tabs = [{ id: "overview", label: "Overview" }];
  if (!isOpen) return null;

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0 flex items-center justify-center z-40 p-4 "
        onClick={onClose}
      >
        <motion.div
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.9, opacity: 0 }}
          className="bg-white rounded-2xl shadow-2xl max-w-md w-full max-h-[90vh] overflow-hidden"
          onClick={(e) => e.stopPropagation()}
        >
          {/* Header */}
          <div className="flex items-center justify-between p-6 border-b border-gray-200">
            <h2 className="text-2xl font-bold text-[#5a3c28]">Wallet</h2>
            <button
              onClick={onClose}
              className="text-gray-500 hover:text-gray-700 text-2xl font-bold"
            >
              ×
            </button>
          </div>

          {/* Tabs */}
          <div className="flex border-b border-gray-200">
            {tabs.map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`flex-1 py-4 px-6 text-sm font-semibold transition-colors ${
                  activeTab === tab.id
                    ? "text-[#a17b5d] border-b-2 border-[#a17b5d]"
                    : "text-gray-500 hover:text-gray-700"
                }`}
              >
                {tab.label}
              </button>
            ))}
          </div>

          {/* Content */}
          <div className="p-6">
            {activeTab === "overview" && (
              <div className="text-center">
                <div className="relative mb-6">
                  <div className="w-32 h-32 mx-auto bg-gradient-to-br from-purple-400 to-blue-500 rounded-full flex items-center justify-center mb-4">
                    <div className="w-20 h-20 bg-white rounded-full flex items-center justify-center">
                      <span className="text-3xl">💰</span>
                    </div>
                  </div>
                  <div className="absolute inset-0 bg-gradient-to-t from-red-500/20 to-transparent rounded-full"></div>
                </div>

                <div className="mb-6">
                  <h3 className="text-xl font-bold text-[#5a3c28] mb-2">
                    Your Art Gallery Wallet
                  </h3>
                  <div className="text-3xl font-bold text-[#a17b5d] mb-2">
                    {balance}
                  </div>
                  <p className="text-gray-600 text-sm">
                    {balance === "₹0.00"
                      ? "Your wallet is currently empty"
                      : "Use your balance to participate in auctions or purchase artwork"}
                  </p>
                </div>

                <p className="text-gray-600 mb-6 text-sm leading-relaxed">
                  Add funds to your wallet to purchase artwork or participate in
                  auctions. You can add money via various payment methods
                  available in your region.
                </p>

                <div className="space-y-3">
                  <button
                    onClick={() => setShowDepositModal(true)}
                    className="w-full bg-[#a17b5d] text-white py-3 px-6 rounded-lg font-semibold hover:bg-[#8c6448] transition-colors"
                  >
                    Add Funds
                  </button>
                </div>
              </div>
            )}
          </div>
        </motion.div>

        {/* Deposit Modal */}
        {showDepositModal && (
          <DepositModal onClose={() => setShowDepositModal(false)} />
        )}
      </motion.div>
    </AnimatePresence>
  );
};

export default WalletModal;
