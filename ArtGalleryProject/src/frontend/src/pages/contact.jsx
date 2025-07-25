import React from "react";
import { motion } from "framer-motion";
import { FaEnvelope, FaPhoneAlt, FaMapMarkerAlt, FaComments } from "react-icons/fa";

const Contact = () => (
  <div className="relative min-h-[80vh] flex flex-col justify-center items-center">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=1200&q=80"
        alt="Contact Hero"
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
        <FaComments className="text-[#674d33] text-3xl" /> Contact Us
      </h1>
      <p className="mb-4">We'd love to hear from you! Reach out with any questions, feedback, or partnership inquiries.</p>
      <div className="mb-4 flex items-center gap-3">
        <FaEnvelope className="text-[#674d33] text-xl" />
        <div>
          <div className="font-semibold">Email:</div>
          <a href="mailto:support@artfusion.com" className="text-[#674d33] underline">support@artfusion.com</a>
        </div>
      </div>
      <div className="mb-4 flex items-center gap-3">
        <FaPhoneAlt className="text-[#674d33] text-xl" />
        <div>
          <div className="font-semibold">Phone:</div>
          <span className="text-[#674d33]">+1 (555) 123-4567</span>
        </div>
      </div>
      <div className="mb-4 flex items-center gap-3">
        <FaMapMarkerAlt className="text-[#674d33] text-xl" />
        <div>
          <div className="font-semibold">Address:</div>
          <span className="text-[#674d33]">123 Art Lane, Creativity City, 45678</span>
        </div>
      </div>
      <p className="text-md text-gray-600 border-t pt-4">Or use the contact form on our website for a quick response.</p>
    </motion.div>
  </div>
);

export default Contact; 