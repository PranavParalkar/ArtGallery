import React, { useEffect, useState } from "react";
import axios from "axios";
import { motion, AnimatePresence } from "framer-motion";

const Auction = () => {
  const [paintings, setPaintings] = useState([]);
  const [fullscreenImage, setFullscreenImage] = useState(null);

  useEffect(() => {
    fetchPaintings();
  }, []);

  const fetchPaintings = async () => {
    try {
      const res = await axios.get("http://localhost:8085/api/paintings/all");
      setPaintings(res.data.data); // ✅ assuming backend wraps response in { data: [...] }
      console.log("🎨 Loaded paintings:", res.data.data);
    } catch (err) {
      console.error("Error fetching paintings", err);
    }
  };

  return (
    <div className="min-h-screen bg-[#fefaf6] px-6 py-12 font-serif relative">
      <h1 className="text-4xl font-bold text-center text-[#3e2e1e] mb-12">
        🎨 Auction Paintings
      </h1>

      <div className="grid gap-10 md:grid-cols-2 xl:grid-cols-4">
        {paintings.length > 0 ? (
          paintings.map((painting) => (
            <motion.div
              key={painting.paintingId}
              initial={{ opacity: 0, y: 40 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5 }}
              className="bg-white rounded-2xl overflow-hidden shadow-md hover:shadow-2xl hover:shadow-amber-950 transition"
            >
              {/* Image skipped since DB doesn't store it */}
              <div className="p-6 flex flex-col justify-between h-[300px]">
                <div>
                  <h2 className="text-xl font-bold text-[#5a3c28] mb-1">
                    {painting.name}
                  </h2>
                  <p className="text-sm text-gray-700 mb-3">
                    {painting.description}
                  </p>
                  <p className="text-sm text-gray-500 mb-2">
                    💰 Current Bid: ₹{painting.currentHighestBid}
                  </p>
                  <p className="text-sm text-gray-500">
                    Seller:{" "}
                    <span className="font-medium text-[#6b4c35]">
                      {painting.seller?.sellerUsername || "Unknown"}
                    </span>
                  </p>
                </div>

                <a
                  href={`/auction/${painting.paintingId}`}
                  className="mt-4 block text-center py-2 rounded-lg bg-green-600 hover:bg-green-700 text-white font-semibold transition"
                >
                  View Details
                </a>
              </div>
            </motion.div>
          ))
        ) : (
          <p className="text-center text-gray-500 col-span-full">
            No paintings found.
          </p>
        )}
      </div>

      {/* Fullscreen Image Modal - Currently Disabled */}
      <AnimatePresence>
        {fullscreenImage && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black/80 flex items-center justify-center z-50"
            onClick={() => setFullscreenImage(null)}
          >
            <motion.div
              initial={{ scale: 0.8 }}
              animate={{ scale: 1 }}
              exit={{ scale: 0.8 }}
              className="relative max-w-4xl w-full"
            >
              <img
                src={fullscreenImage}
                alt="Fullscreen Preview"
                className="w-full max-h-[90vh] object-contain rounded-lg shadow-2xl"
              />
              <button
                onClick={() => setFullscreenImage(null)}
                className="absolute top-3 right-3 text-white bg-black/70 rounded-full px-3 py-1 text-sm hover:bg-black"
              >
                ✕ Close
              </button>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default Auction;
