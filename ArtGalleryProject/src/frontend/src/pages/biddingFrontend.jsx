import React, { useEffect, useState } from "react";
import axios from "axios";
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

  // Fetch painting details
  useEffect(() => {
    axiosInstance
      .get(`/auctions/${paintingId}`)
      .then((res) => setPainting(res.data))
      .catch(() => setPainting(null));
  }, [paintingId]);

  // Fetch bids
  useEffect(() => {
    axiosInstance
      .get(`/auctions/bid/${paintingId}`)
      .then((res) => setBids(res.data))
      .catch(() => setBids([]));
  }, [paintingId]);

  // Place bid
  const handleBid = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      await axiosInstance.post(`/auctions/bid/${paintingId}`, {
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
    <div className="font-serif min-h-screen bg-[#fdf6f0] flex items-center justify-center py-10">
      <div className="w-full max-w-7xl grid grid-cols-1 lg:grid-cols-2 gap-14 px-4 lg:px-8">
        {/* Artwork Section */}
        <section className="bg-white rounded-2xl shadow-xl p-8 flex flex-col items-center max-w-2xl mx-auto lg:mx-0">
          {painting ? (
            <>
              <h1 className="text-4xl font-bold text-[#3e2e1e] mb-8 text-center">Art work</h1>
              <div className="overflow-hidden rounded-2xl mb-6 w-full h-96 flex items-center justify-center bg-[#f8f5f0]">
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-full object-cover cursor-pointer transition-transform duration-300 hover:scale-105"
                  onClick={() => setFullscreenImage(`http://localhost:8085${painting.imageUrl}`)}
                />
              </div>
              <h2 className="text-3xl font-extrabold text-[#5a3c28] mb-4 text-center">{painting.title}</h2>
              <p className="text-md text-gray-700 mb-2 text-center"><b>Description:</b> {painting.description}</p>
              <div className="grid grid-cols-2 gap-4 mb-4 text-sm text-gray-700 w-full">
                <p><b>Length:</b> {painting.length} cm</p>
                <p><b>Breadth:</b> {painting.breadth} cm</p>
                <p><b>Seller:</b> <span className="text-[#6b4c35]">{painting.seller}</span></p>
                <p><b>{painting.is_sold ? "Buyer ID:" : "Available For Bidding"}</b> {painting.is_sold ? painting.buyer_id ?? "N/A" : ""}</p>
              </div>
              <div className="flex flex-col gap-2 text-lg font-semibold mb-4 w-full">
                <span className="text-green-700">Starting: ₹{painting.startingPrice}</span>
                <span className="text-blue-700">Current Price: ₹{bids.length > 0 ? bids[0].bid : painting.final_price > 0 ? painting.final_price : painting.starting_price}</span>
                {painting.final_price > 0 && (
                  <span className="text-purple-700">🎯 Final Price: ₹{painting.final_price}</span>
                )}
              </div>
              <span className={`inline-block px-4 py-1 rounded-full text-white text-sm font-bold ${painting.is_sold ? "bg-red-600" : "bg-green-600"}`}>{painting.is_sold ? "✅ Sold" : "🟢 Available"}</span>
            </>
          ) : (
            <div className="text-gray-500 animate-pulse">Loading painting details...</div>
          )}
        </section>

        {/* Bidding Section */}
        <section className="bg-white rounded-2xl shadow-2xl p-10 flex flex-col gap-10 sticky top-10 min-h-[700px] lg:ml-4">
          {/* Top 3 Bidders */}
          <div>
            <h4 className="text-2xl font-bold mb-4 text-[#3e2e1e]">Top 3 Bidders</h4>
            <ul className="space-y-3">
              {bids.length === 0 ? (
                <li className="text-gray-500 italic">No bids yet.</li>
              ) : (
                bids.slice(0, 3).map((bidder, idx) => (
                  <li
                    key={idx}
                    className="flex items-center justify-between bg-[#fefaf6] border border-gray-200 rounded-lg px-4 py-3 shadow-sm hover:shadow-md transition"
                  >
                    <div className="flex items-center gap-3">
                      <div className="bg-yellow-100 rounded-full w-10 h-10 flex items-center justify-center font-bold text-[#5a3c28]">
                        {bidder.name?.charAt(0).toUpperCase() || "B"}
                      </div>
                      <span className="font-semibold text-[#6b4c35]">{bidder.name || "Anonymous"}</span>
                    </div>
                    <span className="font-semibold text-blue-700 text-lg">₹{bidder.bid}</span>
                  </li>
                ))
              )}
            </ul>
          </div>

          <hr className="my-2 border-gray-200" />

          {/* Place Your Bid */}
          <div>
            <h3 className="text-2xl font-extrabold text-[#3e2e1e] mb-4">Place Your Bid</h3>
            <form onSubmit={handleBid} className="flex flex-col sm:flex-row gap-4 mb-4">
              <input
                type="number"
                min="1"
                value={bidAmount}
                onChange={(e) => setBidAmount(e.target.value)}
                placeholder="Enter your bid (₹)"
                className="flex-1 px-6 py-4 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 text-lg transition"
                required
              />
              <button
                type="submit"
                className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-4 rounded-lg font-bold text-lg shadow-md transition duration-200 focus:outline-none focus:ring-2 focus:ring-blue-400"
              >
                Place Bid
              </button>
            </form>
            {message && <div className="text-green-700 font-semibold text-center mb-2 transition duration-300">{message}</div>}
            {error && <div className="text-red-600 font-semibold text-center mb-2 transition duration-300">{error}</div>}
          </div>
        </section>
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
        {showPopup && (
          <BidSuccessPopup
            amount={popupAmount}
            onClose={() => setShowPopup(false)}
          />
        )}
      </AnimatePresence>
    </div>
  );
};

export default BiddingFrontend;
