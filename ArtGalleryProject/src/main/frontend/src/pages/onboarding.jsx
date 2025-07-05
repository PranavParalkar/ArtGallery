import React from "react";
import { FaUserTie, FaShoppingCart } from "react-icons/fa";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";

const Onboarding = () => {
  return (
    <motion.div
      className="h-[890px] w-screen flex items-center justify-center bg-[#f6efe8] px-4"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      transition={{ duration: 0.8 }}
    >
      <motion.div
        className="bg-[#fff7f0] p-10 rounded-2xl font-serif shadow-2xl shadow-black max-w-md w-full text-center"
        initial={{ y: 50, scale: 0.9, opacity: 0 }}
        animate={{ y: 0, scale: 1, opacity: 1 }}
        transition={{ type: "spring", stiffness: 80, duration: 0.6 }}
      >
        <h1 className="text-3xl font-bold text-[#5c3a1e] mb-6">
          Welcome to <br /> FUSION ART
        </h1>

        <div className="flex flex-col gap-6">
          {/* Buyer */}
          <Link to="/dash">
            <motion.button
              whileHover={{ scale: 1.0 }}
              whileTap={{ scale: 0.95 }}
              className="flex items-center cursor-pointer justify-center hover:shadow-2xl hover:shadow-black gap-3 bg-[#d5baa4] hover:bg-[#caa78b] text-white font-semibold py-3 px-6 rounded-lg transition w-full"
            >
              <FaShoppingCart className="text-xl" />
              I’m a Buyer
            </motion.button>
          </Link>

          {/* Seller */}
          <Link to="/dash">
            <motion.button
              whileHover={{ scale: 1.0 }}
              whileTap={{ scale: 0.95 }}
              className="flex items-center cursor-pointer justify-center gap-3 hover:shadow-2xl hover:shadow-black bg-[#b08c6a] hover:bg-[#9f7d5e] text-white font-semibold py-3 px-6 rounded-lg transition w-full"
            >
              <FaUserTie className="text-xl" />
              I’m a Seller
            </motion.button>
          </Link>
        </div>
      </motion.div>
    </motion.div>
  );
};

export default Onboarding;
