import React, { useState, useEffect, useCallback } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axiosInstance from "../axiosInstance";
// -----------------------------
// Withdraw Modal Component
// -----------------------------
const WithdrawModal = ({ onClose, currentBalance }) => {
  const [profile, setProfile] = useState(null);
  // Fetch user profile
  useEffect(() => {
    axiosInstance
      .get("/user/profile")
      .then((res) => {
        setProfile(res.data);
      })
      .catch((err) => {
        console.error("Failed to load profile:", err);
        setProfile(null);
      });
  }, [token]);
  const [formData, setFormData] = useState({
    amount: "",
    bankAccount: "",
    ifscCode: "",
    accountHolderName: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const handleInputChange = useCallback((e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  }, []);

  const handleSubmit = useCallback(async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      if (amount > profile.balance) {
        alert("Insufficient amount in wallet");
        setIsWalletOpen(true);
        setShowWithdrawModal(false);
      } else {
        const response = await axiosInstance.post("/wallet/withdraw", formData);

        if (response.data.message) {
          setSuccess(true);
          setTimeout(() => {
            onClose();
            window.location.reload();
          }, 2000);
        }
      }
    } catch (err) {
      console.error("Withdrawal error:", err);
      setSuccess(true);
      setTimeout(() => {
        onClose();
        window.location.reload();
      }, 2000);
    } finally {
      setLoading(false);
    }
  }, [formData, onClose]);

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
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-semibold">Withdraw Funds</h2>
            <button
              onClick={onClose}
              className="text-[#3e2e1e] text-2xl font-bold"
            >
              ×
            </button>
          </div>

          {success ? (
            <div className="text-center py-8">
              <div className="text-green-500 text-6xl mb-4">✓</div>
              <h3 className="text-lg font-semibold mb-2">Withdrawal Request Submitted!</h3>
              <p className="text-sm text-gray-600">
                Your withdrawal request has been submitted successfully and is awaiting admin approval.
                You will be notified once the request is processed.
              </p>
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="bg-[#f0e2d2] rounded-lg p-4">
                <div className="mb-4">
                  <label className="block text-sm font-semibold mb-2">Amount to Withdraw</label>
                  <input
                    type="number"
                    name="amount"
                    value={formData.amount}
                    onChange={handleInputChange}
                    placeholder="Enter amount"
                    className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
                    required
                  />
                </div>

                <div className="mb-4">
                  <label className="block text-sm font-semibold mb-2">Account Holder Name</label>
                  <input
                    type="text"
                    name="accountHolderName"
                    value={formData.accountHolderName}
                    onChange={handleInputChange}
                    placeholder="Enter account holder name"
                    className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
                    required
                  />
                </div>

                <div className="mb-4">
                  <label className="block text-sm font-semibold mb-2">Bank Account Number</label>
                  <input
                    type="text"
                    name="bankAccount"
                    value={formData.bankAccount}
                    onChange={handleInputChange}
                    placeholder="Enter bank account number"
                    className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
                    required
                  />
                </div>

                <div className="mb-4">
                  <label className="block text-sm font-semibold mb-2">IFSC Code</label>
                  <input
                    type="text"
                    name="ifscCode"
                    value={formData.ifscCode}
                    onChange={handleInputChange}
                    placeholder="Enter IFSC code"
                    className="w-full p-2 bg-white rounded-md outline-none text-black placeholder-gray-400"
                    required
                  />
                </div>

                <button
                  type="submit"
                  disabled={loading}
                  className="w-full bg-[#3e2e1e] hover:bg-[#8d7a67] disabled:bg-gray-400 text-white font-semibold py-2 rounded-md transition-colors"
                >
                  {loading ? "Processing..." : "Submit Withdrawal Request"}
                </button>
              </div>
            </form>
          )}
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
  const [showWithdrawModal, setShowWithdrawModal] = useState(false);
  const [rawBalance, setRawBalance] = useState(0);

  const token = localStorage.getItem("token");

  const fetchBalance = useCallback(async () => {
    if (!token) return;

    try {
      const res = await axiosInstance.get(`http://localhost:8085/wallet`);
      const rawBalance = parseFloat(res.data.balance || 0);
      setRawBalance(rawBalance);
      setBalance(`₹${rawBalance.toFixed(2)}`);
    } catch (err) {
      console.error("Failed to fetch wallet balance:", err);
      setBalance("₹0.00");
      setRawBalance(0);
    }
  }, [token]);

  useEffect(() => {
    if (isOpen) {
      const token = localStorage.getItem("token");
      if (!token) return;

      axiosInstance
        .get(`http://localhost:8085/wallet`)
        .then((res) => {
          const rawBalance = parseFloat(res.data.balance || 0);
          setBalance(`₹${rawBalance.toFixed(2)}`);
        })
        .catch((err) => {
          console.error("Failed to fetch wallet balance:", err);
          setBalance("₹0.00");
        });
    }
  }, [isOpen, fetchBalance]);

  const handleSetAmount = useCallback(() => {
    window.location.href = "http://localhost:8085/orders";
  }, []);

  const handleWithdraw = useCallback(() => {
    setShowWithdrawModal(true);
  }, []);

  const tabs = [{ id: "overview", label: "Overview" }];

  if (!isOpen) return null;

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0 flex items-center justify-center z-40 p-4"
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
              className="text-gray-500 hover:text-gray-700 text-2xl font-bold cursor-pointer"
            >
              x
            </button>
          </div>

          {/* Tabs */}
          <div className="flex border-b border-gray-200">
            {tabs.map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`flex-1 py-4 px-6 text-sm font-semibold transition-colors ${activeTab === tab.id
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
                  auctions. You can also withdraw funds to your bank account.
                </p>

                <div className="space-y-3">
                  <button
                    onClick={handleSetAmount}
                    className="w-full bg-[#a17b5d] text-white py-3 px-6 rounded-lg font-semibold hover:bg-[#8c6448] transition-colors duration-300 ease-in-out cursor-pointer"
                  >
                    Add Funds
                  </button>

                  <button
                    onClick={handleWithdraw}
                    className="w-full bg-white border-2 border-[#a17b5d] text-[#a17b5d] py-3 px-6 rounded-lg font-semibold hover:bg-[#a17b5d] hover:text-white transition-colors duration-300 ease-in-out cursor-pointer"
                  >
                    Withdraw Funds
                  </button>

                </div>
              </div>
            )}
          </div>
        </motion.div>

        {/* Withdraw Modal */}
        {showWithdrawModal && (
          <WithdrawModal
            onClose={() => setShowWithdrawModal(false)}
            currentBalance={balance}
          />
        )}
      </motion.div>
    </AnimatePresence>
  );
};

export default WalletModal;
