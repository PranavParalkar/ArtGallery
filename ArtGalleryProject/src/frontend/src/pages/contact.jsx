import React from "react";
import { motion } from "framer-motion";
import { FaEnvelope, FaPhoneAlt, FaMapMarkerAlt, FaComments } from "react-icons/fa";

const WaveDivider = () => (
  <svg className="absolute bottom-0 left-0 w-full h-24 z-10" viewBox="0 0 1440 320" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill="#674d33" fillOpacity="1" d="M0,224L48,213.3C96,203,192,181,288,192C384,203,480,245,576,240C672,235,768,181,864,176C960,171,1056,213,1152,229.3C1248,245,1344,235,1392,229.3L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>
);

const Contact = () => (
  <div className="relative min-h-screen flex flex-col justify-center items-center overflow-hidden bg-[#f8f5f0]">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=1200&q=80"
        alt="Contact Hero"
        className="w-full h-full object-cover opacity-60 scale-105"
      />
      <div className="absolute inset-0 bg-gradient-to-b from-[#fffaf3]/80 via-[#fffaf3]/60 to-[#674d33]/70" />
    </div>
    <motion.div
      initial={{ opacity: 0, y: 40 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.7 }}
      className="relative z-20 max-w-4xl mx-auto bg-white/90 backdrop-blur-lg rounded-3xl shadow-2xl border border-[#674d33]/20 p-10 mt-24 mb-24 text-gray-800"
    >
      <h1 className="text-4xl font-serif font-bold mb-4 text-[#674d33] flex items-center gap-3 drop-shadow-lg">
        <FaComments className="text-[#674d33] text-3xl" /> Contact Us
      </h1>
      <p className="mb-4">We'd love to hear from you! Reach out with any questions, feedback, or partnership inquiries.</p>
      <div className="mb-4 flex items-center gap-3">
        <FaEnvelope className="text-[#674d33] text-xl" />
        <div>
          <div className="font-semibold">Email:</div>
          <a href="mailto:varad4422@gmail.com" className="text-[#674d33] underline">varad4422@gmail.com</a>
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
    <WaveDivider />
  </div>
);

export default Contact; 