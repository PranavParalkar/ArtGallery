import React from "react";
import { motion } from "framer-motion";
import { FaGavel, FaShieldAlt, FaUserCheck, FaBalanceScale } from "react-icons/fa";

const Terms = () => (
  <div className="relative min-h-[80vh] flex flex-col justify-center items-center">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1200&q=80"
        alt="Terms Hero"
        className="w-full h-full object-cover opacity-70"
      />
      <div className="absolute inset-0 bg-gradient-to-br from-[#fffaf3]/80 to-[#674d33]/60" />
    </div>
    <motion.div
      initial={{ opacity: 0, y: 40 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.7 }}
      className="relative z-10 max-w-4xl mx-auto bg-white/80 backdrop-blur-lg rounded-2xl shadow-2xl p-10 mt-20 mb-10 text-gray-800"
    >
      <h1 className="text-4xl font-serif font-bold mb-4 text-[#674d33] flex items-center gap-3">
        <FaGavel className="text-[#674d33] text-3xl" /> Terms & Conditions
      </h1>
      <p className="mb-4">By accessing and using Art Fusion, you agree to the following terms and conditions:</p>
      <ul className="list-disc pl-6 mb-4 space-y-2">
        <li className="flex items-center gap-2"><FaUserCheck className="text-[#674d33]" /> All users must provide accurate information during registration and transactions.</li>
        <li className="flex items-center gap-2"><FaShieldAlt className="text-[#674d33]" /> Artworks listed for sale or auction must be authentic and owned by the seller.</li>
        <li className="flex items-center gap-2"><FaGavel className="text-[#674d33]" /> Bidding is binding. Winning bidders are required to complete the purchase.</li>
        <li className="flex items-center gap-2"><FaBalanceScale className="text-[#674d33]" /> Art Fusion is not responsible for disputes between buyers and sellers, but will mediate when possible.</li>
        <li className="flex items-center gap-2"><FaShieldAlt className="text-[#674d33]" /> Users must not engage in fraudulent, abusive, or illegal activities on the platform.</li>
        <li className="flex items-center gap-2"><FaShieldAlt className="text-[#674d33]" /> All content and images are protected by copyright and may not be used without permission.</li>
      </ul>
      <p className="text-md text-gray-600 border-t pt-4">For full details, please contact our support team or visit our website.</p>
    </motion.div>
  </div>
);

export default Terms; 