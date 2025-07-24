import React, { useEffect, useState } from "react";
import axiosInstance from '../axiosInstance';
import { useParams } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";

const BiddingFrontend = () => {
  const { paintingId } = useParams();
  const [painting, setPainting] = useState(null);
  const [bids, setBids] = useState([]);
  const [bidAmount, setBidAmount] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [showPopup, setShowPopup] = useState(false);
  const [popupAmount, setPopupAmount] = useState(0);
  const [isVisible, setIsVisible] = useState(true);

  const token = localStorage.getItem("token");

  // Fetch painting details
  useEffect(() => {
    axiosInstance.get(
      `/auctions/${paintingId}`
    )
      .then((res) => setPainting(res.data))
      .catch(() => setPainting(null));
  }, [paintingId]);

  // Fetch bids
  useEffect(() => {
    axiosInstance.get(
      `/auctions/bid/${paintingId}`
    )
      .then((res) => setBids(res.data))
      .catch(() => setBids([]));
  }, [paintingId]);

  // Place bid
  const handleBid = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      await axiosInstance.post(
        `/auctions/bid/${paintingId}`, {
        bidAmount: parseFloat(bidAmount),
      });
      setMessage("Bid placed successfully!");
      setPopupAmount(parseFloat(bidAmount));
      setShowPopup(true);
      setBidAmount("");
      const res = await axiosInstance.get(
        `/auctions/bid/${paintingId}`
      );
      setBids(res.data);
      setTimeout(() => setShowPopup(false), 3000);
    } catch (err) {
      setError(err?.response?.data || "Failed to place bid.");
    }
  };


  const handleClose = () => {
    setIsVisible(false);
    setTimeout(() => {
      onClose();
    }, 300); // Wait for animation to complete
  };

  const BidSuccessPopup = ({ amount, onClose }) => {
    return (
      <AnimatePresence>
        {isVisible && (
          <motion.div
            onClick={handleClose}
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            transition={{ duration: 0.3 }}
            className="fixed inset-0 text-3xl text-[#3e2e1e] font-serif flex items-center justify-center z-50 backdrop-blur-sm cursor-pointer"
          >
            ✅ You have placed a bid successfully for ₹{amount}.
          </motion.div>
        )}
      </AnimatePresence>
    );
  };

  return (
    <div className="  font-serif">
      <div className="max-w-7xl px-6 pt-10 pb-7 grid grid-cols-2">
        {/* Painting Section */}
        <section className="bg-white rounded-2xl h-[810px] shadow-xl p-8 flex-1 flex flex-col max-w-xl mx-auto xl:mx-0 transition-all duration-500">
          {painting ? (
            <>
              <p className="text-4xl font-bold text-center text-[#3e2e1e] mb-8 ">
                Art work
              </p>
              {/* <div className="overflow-hidden rounded-2xl mb-6">
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-72 object-cover transform transition-transform duration-500 hover:scale-105 shadow-md"
                />
              </div> */}
              <div className="overflow-hidden h-1/2 rounded-2xl">
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-full object-cover cursor-pointer 
                transform transition-transform duration-300 hover:scale-110"
                  onClick={() =>
                    setFullscreenImage(
                      `http://localhost:8085${painting.imageUrl}`
                    )
                  }
                />
              </div>
              <h2 className="text-3xl text-center mt-3 font-extrabold text-[#5a3c28] mb-6 tracking-wide">
                {painting.title}
              </h2>
              <p className="text-md text-gray-700 mb-2 ">
                <b>Description:</b> {painting.description}
              </p>

              <div className="grid grid-cols-2 gap-4 mb-4 text-sm text-gray-700">
                <p>
                  <b>Length:</b> {painting.length} cm
                </p>
                <p>
                  <b>Breadth:</b> {painting.breadth} cm
                </p>
                <p>
                  <b>Seller:</b>{" "}
                  <span className="text-[#6b4c35]">{painting.seller}</span>
                </p>
                <p>
                  <b>
                    {painting.is_sold ? "Buyer ID:" : "Available For Bidding"}
                  </b>{" "}
                  {painting.is_sold ? painting.buyer_id ?? "N/A" : ""}
                </p>
              </div>

              <div className="flex flex-col gap-2 text-lg font-semibold mb-4">
                <span className="text-green-700">
                  Starting: ₹{painting.startingPrice}
                </span>
                <span className="text-blue-700">
                  Current Price: ₹
                  {bids.length > 0
                    ? bids[0].bid
                    : painting.final_price > 0
                      ? painting.final_price
                      : painting.starting_price}
                </span>
                {painting.final_price > 0 && (
                  <span className="text-purple-700">
                    🎯 Final Price: ₹{painting.final_price}
                  </span>
                )}
              </div>

              <span
                className={`inline-block px-4 py-1 rounded-full text-white text-sm font-bold ${painting.is_sold ? "bg-red-600" : "bg-green-600"
                  }`}
              >
                {painting.is_sold ? "✅ Sold" : "🟢 Available"}
              </span>
            </>
          ) : (
            <div className="text-gray-500 animate-pulse">
              Loading painting details...
            </div>
          )}
        </section>

        {/* Bidding Section */}
        <section className="bg-white rounded-2xl shadow-xl p-8 transition w-[140%] ">
          {/* Bidders List */}
          <div className="mt-6 h-1/2">
            <h4 className="text-xl font-bold mb-4 text-[#5a3c28] tracking-wide flex items-center gap-2">
              <span className="inline-block bg-yellow-200 rounded-full px-3 py-1 text-yellow-800 text-base font-semibold shadow-sm">
                🏆 Top 3 Bidders
              </span>
            </h4>
            <ul className="space-y-4">
              {!token ? (
                <li className="text-red-500 italic bg-red-50 rounded-lg px-4 py-3 shadow-sm">
                  Login to view bids
                </li>
              ) : bids.length === 0 ? (
                <li className="text-gray-500 italic bg-gray-50 rounded-lg px-4 py-3 shadow-sm">
                  No bids yet.
                </li>
              ) : (
                bids.slice(0, 3).map((bidder, idx) => (
                  <li
                    key={idx}
                    className="flex items-center justify-between bg-[#fefaf6] border border-[#e7d5c0] rounded-xl px-5 py-4 shadow-md hover:shadow-lg transition-all duration-200"
                  >
                    <div className="flex items-center gap-4">
                      <span className="text-lg font-bold text-[#bfa16a]">{bidder.rank}</span>
                      <div className="bg-yellow-100 border-2 border-yellow-300 rounded-full w-12 h-12 flex items-center justify-center font-bold text-2xl text-[#5a3c28] shadow">
                        {bidder.name?.charAt(0).toUpperCase() || "A"}
                      </div>
                      <span className="font-semibold text-[#6b4c35] text-lg tracking-wide">
                        {bidder.name || "Anonymous"}
                      </span>
                    </div>
                    <span className="font-bold text-blue-700 text-xl bg-blue-50 px-4 py-2 rounded-lg shadow">
                      ₹{bidder.bid}
                    </span>
                  </li>
                ))
              )}
            </ul>
            <h3 className="text-2xl font-extrabold text-[#3e2e1e] mb-6 mt-10 tracking-wide">
              Place Your Bid
            </h3>
            <form
              onSubmit={handleBid}
              className="flex flex-col sm:flex-row gap-4 mb-6"
            >
              <input
                type="number"
                min="1"
                value={bidAmount}
                onChange={(e) => setBidAmount(e.target.value)}
                placeholder="Enter your bid (₹)"
                className="flex-1 px-5 py-3 border border-[#e7d5c0] rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 transition bg-[#fefaf6] text-[#3e2e1e] font-medium text-lg shadow"
                required
              />
              <button
                type="submit"
                className="bg-gradient-to-r from-blue-700 to-blue-500 hover:from-blue-800 hover:to-blue-600 text-white px-8 py-3 rounded-lg font-bold text-lg transition shadow-lg"
              >
                Place Bid
              </button>
            </form>
            {message && (
              <div className="text-green-700 font-semibold text-center mb-4 transition duration-300 bg-green-50 rounded-lg px-4 py-3 shadow">
                {message}
              </div>
            )}
            {error && (
              <div className="text-red-600 font-semibold text-center mb-4 transition duration-300 bg-red-50 rounded-lg px-4 py-3 shadow">
                {error}
              </div>
            )}
          </div>
        </section>

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
          {showPopup && (
            <BidSuccessPopup
              amount={popupAmount}
              onClose={() => setShowPopup(false)}
            />
          )}
        </AnimatePresence>
      </div>
    </div>
  );
};

export default BiddingFrontend