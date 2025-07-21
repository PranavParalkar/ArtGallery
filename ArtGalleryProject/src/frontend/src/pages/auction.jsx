import React, { useEffect, useState } from "react";
import axios from "axios";
import { motion, AnimatePresence } from "framer-motion";

const Auction = () => {
  const [paintings, setPaintings] = useState([]);
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [pageNo, setPageNo] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true); // Track if next page exists

  useEffect(() => {
    fetchPaintings(pageNo);
  }, [pageNo]);

  const fetchPaintings = async (page = 0) => {
    try {
      const res = await axios.get(`http://localhost:8085/auctions?pageNo=${page}`);
      const data = res.data.content || res.data;
      setPaintings(data);
      // Check if next page has paintings
      const nextRes = await axios.get(`http://localhost:8085/auctions?pageNo=${page + 1}`);
      const nextData = nextRes.data.content || nextRes.data;
      setHasNextPage(Array.isArray(nextData) ? nextData.length > 0 : false);
    } catch (err) {
      setPaintings([]);
      setHasNextPage(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#fefaf6] px-20 py-10 font-serif relative">
      <h1 className="text-4xl font-bold text-center text-[#3e2e1e] mb-12">
        🎨 Auction Paintings
      </h1>

      <div className="grid gap-10 md:grid-cols-2 xl:grid-cols-3">
        {paintings.length > 0 ? (
          paintings.map((painting) => (
            <motion.div
              key={painting.paintingId}
              initial={{ opacity: 0, y: 40 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5 }}
              className="bg-white rounded-2xl overflow-hidden shadow-md hover:shadow-2xl hover:shadow-amber-950 transition"
            >
              {painting.imageUrl && (
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-60 object-cover cursor-pointer"
                  onClick={() => setFullscreenImage(`http://localhost:8085${painting.imageUrl}`)}
                />
              )}
              <div className="p-6 flex flex-col justify-between h-[350px]">
                <div>
                  <h2 className="text-xl font-bold text-[#5a3c28] mb-1">
                    {painting.title}
                  </h2>
                  <p className="text-sm text-gray-700 mb-2">
                    {painting.description}
                  </p>
                  <p className="text-sm text-gray-500 mb-1">
                    📏 {painting.length}cm x {painting.breadth}cm
                  </p>
                  <p className="text-sm text-gray-500 mb-1">
                    💰 Starting Price: ₹{painting.startingPrice}
                  </p>
                  <p className="text-sm text-gray-500 mb-1">
                    {painting.isSold ? '✅ Sold' : '🟢 Available'}
                  </p>
                  <p className="text-sm text-gray-500">
                    Seller: <span className="font-medium text-[#6b4c35]">{painting.seller || 'Unknown'}</span>
                  </p>
                </div>
                <a
                  href={`/auction/${painting.paintingId}`}
                  className="mt-4 block text-center py-2 rounded-lg bg-green-600 hover:bg-green-700 text-white font-semibold transition"
                >
                  Place Bid
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

      {/* Pagination Controls */}
      <div className="flex justify-center mt-8 gap-4">
        <button
          onClick={() => setPageNo((p) => Math.max(0, p - 1))}
          disabled={pageNo === 0}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Previous
        </button>
        <span className="px-4 py-2">Page {pageNo + 1}</span>
        <button
          onClick={() => setPageNo((p) => p + 1)}
          disabled={!hasNextPage}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Next
        </button>
      </div>

      {/* Fullscreen Image Modal */}
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
