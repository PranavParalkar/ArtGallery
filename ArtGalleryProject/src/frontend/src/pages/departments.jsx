import React from "react";
import { motion } from "framer-motion";
import {
  FaPaintBrush,
  FaShapes,
  FaLandmark,
  FaCameraRetro,
  FaFeather,
  FaPalette,
} from "react-icons/fa";

const departmentData = [
  {
    title: "Modern Art",
    description: "Bold expressions and contemporary vision.",
    icon: <FaPaintBrush className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Sculptures",
    description: "3D artistic creations across materials.",
    icon: <FaShapes className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Antiques",
    description: "Collectibles from bygone eras & civilizations.",
    icon: <FaLandmark className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Photography",
    description: "Visual storytelling through the lens.",
    icon: <FaCameraRetro className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Calligraphy",
    description: "Elegant strokes & artistic writing.",
    icon: <FaFeather className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Traditional Paintings",
    description: "Cultural classics: Warli, Madhubani, Pattachitra.",
    icon: <FaPalette className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Digital Art",
    description: "Creative artworks made using digital technology.",
    icon: <FaPaintBrush className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Textile Art",
    description: "Fabric-based expressions including embroidery and weaving.",
    icon: <FaFeather className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Miniature Art",
    description: "Intricately detailed small-scale paintings.",
    icon: <FaPalette className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Street Art",
    description: "Graffiti and urban expression on public walls.",
    icon: <FaLandmark className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Ceramics & Pottery",
    description: "Handcrafted clay art with aesthetic forms.",
    icon: <FaShapes className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Mixed Media",
    description: "Fusion of materials, textures, and techniques.",
    icon: <FaCameraRetro className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Glass Art",
    description: "Sculptures and decor using stained and blown glass.",
    icon: <FaLandmark className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Printmaking",
    description: "Block, etching, and screen-printed masterpieces.",
    icon: <FaPaintBrush className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Installation Art",
    description: "Large-scale immersive art experiences.",
    icon: <FaShapes className="text-3xl text-[#a17b5d]" />,
  },
  {
    title: "Illustration",
    description: "Detailed visual storytelling for books, comics, and media.",
    icon: <FaFeather className="text-3xl text-[#a17b5d]" />,
  },
];

const Departments = () => {
  return (
    <div className="w-screen bg-[#fffaf3] px-6 py-12 font-serif">
      <motion.h1
        initial={{ y: -20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.6 }}
        className="text-4xl font-bold text-center text-[#5a3c28] mb-12"
      >
        Art Departments
      </motion.h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8  mx-auto">
        {departmentData.map((dept, i) => (
          <motion.div
            key={i}
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: i * 0.05 }}
            className="bg-white p-6 rounded-2xl shadow-md hover:shadow-2xl hover:shadow-orange-950 cursor-pointer transition"
          >
            <div className="mb-4">{dept.icon}</div>
            <h2 className="text-xl font-bold text-[#6b4c35] mb-2">
              {dept.title}
            </h2>
            <p className="text-sm text-gray-700">{dept.description}</p>
          </motion.div>
        ))}
      </div>
    </div>
  );
};

export default Departments;
