import React from "react";
import { motion } from "framer-motion";
import { FaLock, FaUserSecret, FaCookieBite, FaEnvelopeOpenText } from "react-icons/fa";

const Privacy = () => (
  <div className="relative min-h-[80vh] flex flex-col justify-center items-center">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1465101046530-73398c7f28ca?auto=format&fit=crop&w=1200&q=80"
        alt="Privacy Hero"
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
        <FaLock className="text-[#674d33] text-3xl" /> Privacy Policy
      </h1>
      <p className="mb-4">Your privacy is important to us. This policy explains how Art Fusion collects, uses, and protects your information:</p>
      <ul className="list-disc pl-6 mb-4 space-y-2">
        <li className="flex items-center gap-2"><FaUserSecret className="text-[#674d33]" /> We collect personal information only for account creation, transactions, and platform improvement.</li>
        <li className="flex items-center gap-2"><FaLock className="text-[#674d33]" /> Your data is stored securely and is never sold to third parties.</li>
        <li className="flex items-center gap-2"><FaCookieBite className="text-[#674d33]" /> Cookies are used to enhance your browsing experience and for analytics.</li>
        <li className="flex items-center gap-2"><FaEnvelopeOpenText className="text-[#674d33]" /> You may request deletion or correction of your personal data at any time.</li>
        <li className="flex items-center gap-2"><FaLock className="text-[#674d33]" /> We implement industry-standard security measures to protect your information.</li>
      </ul>
      <p className="text-md text-gray-600 border-t pt-4">For questions or concerns about your privacy, please contact us at privacy@artfusion.com.</p>
    </motion.div>
  </div>
);

export default Privacy; 