import React, { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axiosInstance from '../axiosInstance';

const WalletModal = ({ isOpen, onClose }) => {
  const [activeTab, setActiveTab] = useState("overview");
  const [balance, setBalance] = useState(null); // null = not loaded
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    if (isOpen) {
      setLoading(true);
      setError("");
      setBalance(null);
      axiosInstance
        .get(`/wallet`)
        .then((res) => {
          if (cancelled) return;
          const rawBalance = parseFloat(res.data.balance || 0);
          console.log(balance);
          setBalance(rawBalance);
          setLoading(false);
        })
        .catch((err) => {
          if (cancelled) return;
          setError("Failed to fetch wallet balance");
          setBalance(0);
          setLoading(false);
        });
    }
    return () => {
      cancelled = true;
    };
  }, [isOpen]);

  const tabs = [{ id: "overview", label: "Overview" }];
  if (!isOpen) return null;

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0   flex items-center justify-center z-50 p-4 backdrop-blur-sm"
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
                {/* Wallet Illustration */}
                <div className="relative mb-6">
                  <div className="w-32 h-32 mx-auto bg-gradient-to-br from-purple-400 to-blue-500 rounded-full flex items-center justify-center mb-4">
                    <div className="w-20 h-20 bg-white rounded-full flex items-center justify-center">
                      <span className="text-3xl">💰</span>
                    </div>
                  </div>
                  <div className="absolute inset-0 bg-gradient-to-t from-red-500/20 to-transparent rounded-full"></div>
                </div>

                {/* Balance Display */}
                <div className="mb-6 min-h-[3.5rem] flex flex-col items-center justify-center">
                  <h3 className="text-xl font-bold text-[#5a3c28] mb-2">
                    Your Art Gallery Wallet
                  </h3>
                  {loading ? (
                    <div className="text-lg text-gray-500 flex items-center gap-2">
                      <svg className="animate-spin h-6 w-6 text-[#a17b5d]" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"><circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle><path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"></path></svg>
                      Loading balance...
                    </div>
                  ) : error ? (
                    <div className="text-red-600 text-sm font-semibold">{error}</div>
                  ) : (
                    <div className="text-3xl font-bold text-[#a17b5d] mb-2">
                      ₹{balance !== null ? balance.toFixed(2) : "0.00"}
                    </div>
                  )}
                  <p className="text-gray-600 text-sm">
                    {balance === 0 || balance === null
                      ? "Your wallet is currently empty"
                      : "Use your balance to participate in auctions or purchase artwork"}
                  </p>
                </div>

                {/* Instructions */}
                <p className="text-gray-600 mb-6 text-sm leading-relaxed">
                  Add funds to your wallet to purchase artwork or participate in
                  auctions. You can add money via various payment methods
                  available in your region.
                </p>

                {/* Action Buttons */}
                <div className="space-y-3">
                  <button className="w-full bg-[#a17b5d] text-white py-3 px-6 rounded-lg font-semibold hover:bg-[#8c6448] transition-colors">
                    Add Funds
                  </button>
                  {/* Optional: Uncomment below when history is ready */}
                  {/* <button className="w-full bg-gray-100 text-[#5a3c28] py-3 px-6 rounded-lg font-semibold hover:bg-gray-200 transition-colors">
                    View Transaction History
                  </button> */}
                </div>
              </div>
            )}
          </div>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default WalletModal;
