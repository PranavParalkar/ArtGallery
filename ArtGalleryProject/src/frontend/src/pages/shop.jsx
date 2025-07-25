import React, { useEffect, useState } from "react";
import axiosInstance from "../axiosInstance";
import { motion, AnimatePresence } from "framer-motion";
import { useNavigate } from "react-router-dom";

const Shop = () => {
  const [paintings, setPaintings] = useState([]);
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [pageNo, setPageNo] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchPaintings(pageNo);
  }, [pageNo]);

  const fetchPaintings = async (page = 0) => {
    try {
      const res = await axiosInstance.get(`/store?pageNo=${page}`);
      const data = res.data.content || res.data;
      setPaintings(data);

      const nextRes = await axiosInstance.get(`/store?pageNo=${page + 1}`);
      const nextData = nextRes.data.content || nextRes.data;
      setHasNextPage(Array.isArray(nextData) ? nextData.length > 0 : false);
    } catch (err) {
      setPaintings([]);
      setHasNextPage(false);
    }
  };
  const [showOrderModal, setShowOrderModal] = useState(false);
  const [selectedPainting, setSelectedPainting] = useState(null);
  const [orderInfo, setOrderInfo] = useState({
    name: "",
    email: "",
    address: "",
    paymentMode: "Cash on Delivery",
  });
  const [orderPlaced, setOrderPlaced] = useState(false);

  return (
    <div className="px-20 py-10 font-serif relative">
      <h1 className="text-4xl font-bold text-center text-[#3e2e1e] mb-12">
        Art Store
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 max-w-[1300px] mx-auto">
        {paintings.length > 0 ? (
          paintings.map((painting) => (
            <motion.div
              key={painting.paintingId}
              initial={{ opacity: 0, y: 15 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.75 }}
              className="rounded-2xl bg-[#f0e2d2] h-[500px] transform hover:-translate-y-2 duration-300 overflow-hidden shadow-md hover:shadow-2xl hover:shadow-amber-950 transition"
            >
              {painting.imageUrl && (
                <div className="overflow-hidden h-1/2 rounded-t-2xl">
                  <img
                    src={`http://localhost:8085${painting.imageUrl}`}
                    alt={painting.title}
                    className="w-full h-full object-cover cursor-pointer transform transition-transform duration-300 hover:scale-110"
                    onClick={() =>
                      setFullscreenImage(
                        `http://localhost:8085${painting.imageUrl}`
                      )
                    }
                  />
                </div>
              )}
              <div className="p-6 flex flex-col justify-between">
                <div>
                  <h2 className="text-xl font-bold text-[#5a3c28] mb-1">
                    {painting.title}
                  </h2>
                  <p className="text-md text-[#6b4c35] mb-2">
                    {painting.description}
                  </p>
                  <p className="text-md text-[#6b4c35] mb-1">
                    <span className="font-bold">Dimensions</span>{" "}
                    {painting.length}cm x {painting.breadth}cm
                  </p>
                  <p className="text-md text-[#6b4c35] mb-1">
                    <span className="font-bold">Price:</span> ₹
                    {painting.startingPrice}
                  </p>
                  <p className="text-md font-bold text-[#6b4c35]">
                    Seller:{" "}
                    <span className="font-medium text-[#6b4c35]">
                      {painting.seller || "Unknown"}
                    </span>
                  </p>
                </div>
                <button
                  className="mt-4 block text-center bottom-0 cursor-pointer hover:scale-95 duration-300 ease-in-out py-2 rounded-lg bg-[#6b4c35] hover:bg-[#776354] text-white font-semibold transition"
                  onClick={() => {
                    setSelectedPainting(painting);
                    setShowOrderModal(true);
                  }}
                >
                  Buy Now
                </button>
              </div>
            </motion.div>
          ))
        ) : (
          <p className="text-center text-gray-500 col-span-full">
            No paintings found.
          </p>
        )}
      </div>

      {/* Pagination */}
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
      <AnimatePresence>
        {showOrderModal && (
          <div>
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              className="fixed inset-0 flex items-center justify-center backdrop-blur-2xl bg-opacity-40 z-50"
            >
              <div className="text-center space-y-6">
                <h2 className="text-xl font-semibold text-[#5a3c28]">
                  Select the Mode of Payment
                </h2>
                <div className="flex justify-center gap-6 mt-4">
                  <motion.button
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    className="bg-[#5a3c28] w-[40%] text-white px-6 py-3 rounded-md hover:bg-[#3d281a]"
                    value={orderInfo.paymentMode}
                    onClick={() => {
                      if (orderInfo.paymentMode === "Cash on Delivery") {
                        setShowOrderModal(false);
                        setOrderPlaced(true);
                        setTimeout(() => setOrderPlaced(false), 3000);
                      } else {
                        alert(
                          `Redirecting to ${orderInfo.paymentMode} payment gateway...`
                        );
                      }
                    }}
                  >
                    Cash on Delivery
                  </motion.button>
                  <motion.button
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    className="bg-[#5a3c28] w-[40%] text-white px-6 py-3 rounded-md hover:bg-[#3d281a]"
                    value={orderInfo.paymentMode}
                    onClick={() => {
                      if (orderInfo.paymentMode === "Pay with Wallet") {
                        navigate(
                          "http://127.0.0.1:5500/ArtGallery/ArtGalleryProject/src/main/resources/templates/orders.html"
                        );
                      } else {
                        alert(
                          `Redirecting to ${orderInfo.paymentMode} payment gateway...`
                        );
                      }
                    }}
                  >
                  Pay with Wallet
                  </motion.button>
                </div>
              </div>

            </motion.div>
          </div>
        )}
      </AnimatePresence>
      <AnimatePresence>
        {orderPlaced && (
          <motion.div
            initial={{ opacity: 0, y: -50 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            transition={{ duration: 0.3 }}
            className="fixed inset-0 text-4xl text-[#3e2e1e] font-serif flex items-center justify-center z-50 backdrop-blur-3xl cursor-pointer"
            onClick={() => setOrderPlaced(false)}
          >
            ✅ You have placed an order successfully for "
            {selectedPainting?.title}".
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default Shop;
