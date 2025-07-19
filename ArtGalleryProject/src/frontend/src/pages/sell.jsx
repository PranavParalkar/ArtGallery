import React, { useState } from "react";
import { motion } from "framer-motion";
import axios from "axios";

const Sell = () => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    seller: "",
    basePrice: "",
  });

  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      name: formData.title,
      description: formData.description,
      price: formData.basePrice,
      sellerUsername: formData.seller,
    };

    try {
      await axios.post("http://localhost:8085/api/paintings/add", payload);
      alert("✅ Artwork submitted successfully!");
      setSubmitted(true);
      setFormData({
        title: "",
        description: "",
        seller: "",
        basePrice: "",
      });
    } catch (error) {
      console.error(
        "Submission failed:",
        error.response?.data || error.message
      );
      alert("❌ Failed to submit artwork");
    }
  };

  return (
    <div className="w-screen min-h-screen bg-[#fffaf3] px-6 py-12 font-serif">
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="max-w-4xl mx-auto bg-white rounded-2xl shadow-lg p-8"
      >
        <h1 className="text-4xl font-bold text-center text-[#5a3c28] mb-8">
          Sell Your Artwork
        </h1>

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-10"
        >
          <div>
            <label className="block mb-1 text-sm font-semibold text-gray-800">
              Title
            </label>
            <input
              type="text"
              name="title"
              placeholder="Modern Art Piece"
              value={formData.title}
              onChange={handleChange}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-[#a17b5d] focus:outline-none"
              required
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-semibold text-gray-800">
              Seller Name
            </label>
            <input
              type="text"
              name="seller"
              placeholder="Riya Mehta"
              value={formData.seller}
              onChange={handleChange}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-[#a17b5d] focus:outline-none"
              required
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-semibold text-gray-800">
              Base Price (₹)
            </label>
            <input
              type="number"
              name="basePrice"
              placeholder="50000"
              value={formData.basePrice}
              onChange={handleChange}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-[#a17b5d] focus:outline-none"
              required
            />
          </div>

          <div className="md:col-span-2">
            <label className="block mb-1 text-sm font-semibold text-gray-800">
              Description
            </label>
            <textarea
              name="description"
              rows={4}
              placeholder="Describe your artwork"
              value={formData.description}
              onChange={handleChange}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-[#a17b5d] focus:outline-none"
              required
            />
          </div>

          <div className="md:col-span-2">
            <motion.button
              whileTap={{ scale: 0.95 }}
              className="w-full bg-[#a17b5d] hover:bg-[#8c6448] text-white font-bold py-3 rounded-lg transition"
              type="submit"
            >
              Submit Artwork
            </motion.button>
          </div>
        </form>

        {/* Optional Success Preview */}
        {submitted && (
          <div className="mt-8 border-t pt-8">
            <h2 className="text-2xl font-bold mb-4 text-[#5a3c28]">
              🎉 Your Artwork has been Listed!
            </h2>
            <div className="bg-[#fefaf6] rounded-xl overflow-hidden shadow-md p-6">
              <h3 className="text-2xl font-bold text-[#5a3c28] mb-2">
                {formData.title}
              </h3>
              <p className="text-gray-700 text-sm mb-3">
                {formData.description}
              </p>
              <p className="text-sm text-gray-600 mb-1">
                Seller:{" "}
                <span className="text-[#8c6448] font-medium">
                  {formData.seller}
                </span>
              </p>
              <p className="text-sm text-gray-600">
                Price: ₹{formData.basePrice}
              </p>
            </div>
          </div>
        )}
      </motion.div>
    </div>
  );
};

export default Sell;
