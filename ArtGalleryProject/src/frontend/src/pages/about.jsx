import React from "react";
import { motion } from "framer-motion";
import { FaPalette, FaGlobe, FaUsers, FaStar } from "react-icons/fa";

const WaveDivider = () => (
  <svg className="absolute bottom-0 left-0 w-full h-24 z-10" viewBox="0 0 1440 320" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill="#674d33" fillOpacity="1" d="M0,224L48,213.3C96,203,192,181,288,192C384,203,480,245,576,240C672,235,768,181,864,176C960,171,1056,213,1152,229.3C1248,245,1344,235,1392,229.3L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>
);

const About = () => (
  <div className="relative min-h-screen flex flex-col justify-center items-center overflow-hidden bg-[#f8f5f0]">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1464983953574-0892a716854b?auto=format&fit=crop&w=1200&q=80"
        alt="Art Gallery Hero"
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
      <h1 className="text-5xl font-serif font-bold mb-4 text-[#674d33] flex items-center gap-3 drop-shadow-lg">
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
    <WaveDivider />
  </div>
);

export default About; 