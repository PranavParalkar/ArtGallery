import React from "react";
import { motion } from "framer-motion";
import { FaPalette, FaGlobe, FaUsers, FaStar } from "react-icons/fa";

const About = () => (
  <div className="relative min-h-[80vh] flex flex-col justify-center items-center">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1464983953574-0892a716854b?auto=format&fit=crop&w=1200&q=80"
        alt="Art Gallery Hero"
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
      <h1 className="text-5xl font-serif font-bold mb-4 text-[#674d33] flex items-center gap-3">
        <FaPalette className="text-[#674d33] text-4xl" /> About Art Fusion
      </h1>
      <p className="text-lg mb-6">
        Art Fusion is a premier online art gallery and auction platform, connecting artists, collectors, and enthusiasts from around the world. Our mission is to celebrate creativity, foster artistic growth, and make art accessible to everyone.
      </p>
      <div className="flex flex-col md:flex-row gap-8 mb-6">
        <div className="flex-1">
          <h2 className="text-2xl font-semibold mb-2 text-[#674d33] flex items-center gap-2"><FaGlobe /> Our Vision</h2>
          <p>
            We believe in the transformative power of art. By bridging the gap between artists and audiences, we aim to inspire, educate, and enrich lives through exceptional works of art.
          </p>
        </div>
        <div className="flex-1">
          <h2 className="text-2xl font-semibold mb-2 text-[#674d33] flex items-center gap-2"><FaUsers /> What We Offer</h2>
          <ul className="list-disc pl-6 mb-2">
            <li>Curated online auctions featuring renowned and emerging artists</li>
            <li>Secure and transparent bidding process</li>
            <li>Personalized art discovery and recommendations</li>
            <li>Support for artists to showcase and sell their work globally</li>
          </ul>
        </div>
      </div>
      <div className="flex items-center gap-2 text-md text-gray-600 border-t pt-4"><FaStar className="text-yellow-500" /> Join us in celebrating the world of art, one masterpiece at a time.</div>
    </motion.div>
  </div>
);

export default About; 