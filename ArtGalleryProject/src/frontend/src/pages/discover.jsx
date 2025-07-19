import React from "react";
import { motion } from "framer-motion";
import {
  FaUserAlt,
  FaFireAlt,
  FaRegLightbulb,
  FaGavel,
  FaNewspaper,
} from "react-icons/fa";

const trends = [
  {
    title: "Trending Now",
    description: "Abstract Expressionism is gaining popularity worldwide.",
    icon: <FaFireAlt className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Featured Artist",
    description: "Meet Arjun Patel, redefining Indian contemporary art.",
    icon: <FaUserAlt className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Creative Inspiration",
    description: "Nature, emotion & heritage are ruling artist palettes.",
    icon: <FaRegLightbulb className="text-3xl text-[#a17b5d]" />,
  },
];

const highlights = [
  {
    type: "Trending Artist",
    title: "Ishika Rana",
    detail: "Known for surreal watercolors reflecting Indian mythology.",
    icon: <FaUserAlt className="text-xl text-[#a17b5d]" />,
    image:
      "https://images.unsplash.com/photo-1680506660555-1c225f5da953?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8SXNoaWthJTIwUmFuYXxlbnwwfHwwfHx8MA%3D%3Dg",
  },
  {
    type: "Highest Bidding",
    title: "‘Madhubani Fusion’",
    detail: "Sold for ₹12.5 Lakh in under 2 minutes.",
    icon: <FaGavel className="text-xl text-[#a17b5d]" />,
    image:
      "https://images.unsplash.com/photo-1657049671938-3e5988228df3?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fEhpZ2hlc3QlMjBCaWRkaW5nfGVufDB8fDB8fHww",
  },
  {
    type: "Art News",
    title: "AI x Art Summit 2025",
    detail: "Delhi hosts the largest digital-art-tech collaboration event.",
    icon: <FaNewspaper className="text-xl text-[#a17b5d]" />,
    image:
      "https://plus.unsplash.com/premium_photo-1674571895797-3ca2aaf89eed?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8TmV3cyUyMGxvZ298ZW58MHx8MHx8fDA%3D",
  },
];

const Discover = () => {
  return (
    <div className="bg-[#fdf7f1] w-screen h-[870px] px-6 py-12 font-serif">
      <motion.h1
        initial={{ y: -20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.5 }}
        className="text-4xl font-bold text-center text-[#5a3c28] mb-12"
      >
        Discover Art & Trends
      </motion.h1>

      {/* Discover Highlights */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-6xl mx-auto">
        {trends.map((trend, i) => (
          <motion.div
            key={i}
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: i * 0.1 }}
            className="bg-white rounded-2xl p-6 shadow-md hover:shadow-2xl hover:shadow-orange-950 cursor-pointer transition"
          >
            <div className="mb-4">{trend.icon}</div>
            <h2 className="text-xl font-bold text-[#6b4c35] mb-2">
              {trend.title}
            </h2>
            <p className="text-sm text-gray-700">{trend.description}</p>
          </motion.div>
        ))}
      </div>

      {/* New Highlights Section */}
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.6 }}
        className="mt-16 max-w-5xl  mx-auto"
      >
        <h2 className="text-2xl font-bold text-[#6b4c35] mb-6 text-center">
          What’s Hot in the Art World
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {highlights.map((item, i) => (
            <div
              key={i}
              className="bg-white rounded-2xl hover:shadow-2xl hover:shadow-orange-950 cursor-pointer overflow-hidden shadow-md  transition"
            >
              <img
                src={item.image}
                alt={item.title}
                className="w-full h-48 object-cover"
              />
              <div className="p-4">
                <div className="flex items-center gap-2 text-sm text-gray-500 mb-1">
                  {item.icon}
                  <span>{item.type}</span>
                </div>
                <h3 className="font-bold text-[#5a3c28] text-lg mb-1">
                  {item.title}
                </h3>
                <p className="text-sm text-gray-600">{item.detail}</p>
              </div>
            </div>
          ))}
        </div>
      </motion.div>
    </div>
  );
};

export default Discover;
