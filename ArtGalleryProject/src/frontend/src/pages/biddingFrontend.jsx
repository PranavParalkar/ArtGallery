import React, { useEffect, useState } from "react";
import axiosInstance from '../axiosInstance';
import { useParams } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";
import Timer  from "../utils/Timer";

const BiddingFrontend = () => {
  const { paintingId } = useParams();
  const [painting, setPainting] = useState(null);
  const [bids, setBids] = useState([]);
  const [bidAmount, setBidAmount] = useState("");
  const [error, setError] = useState("");
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [showPopup, setShowPopup] = useState(false);
  const [popupAmount, setPopupAmount] = useState(0);
  const [message, setMessage] = useState("");

  // This state is now controlled by the Timer component via a prop
  const [auctionLive, setAuctionLive] = useState(false);

  // Fetch painting details
  useEffect(() => {
    axiosInstance.get(`/auctions/${paintingId}`)
      .then((res) => setPainting(res.data))
      .catch(() => setPainting(null));
  }, [paintingId]);

  // Fetch bids
  useEffect(() => {
    const fetchBids = async () => {
      try {
        const res = await axiosInstance.get(`/auctions/bid/${paintingId}`);
        setBids(res.data);
      } catch {
        setBids([]);
      }
    };
    fetchBids();
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
      // Set state to show the popup
      setPopupAmount(parseFloat(bidAmount));
      setShowPopup(true);
      setBidAmount("");
      // Refetch bids to show the new top bid
      const res = await axiosInstance.get(`/auctions/bid/${paintingId}`);
      setBids(res.data);
    } catch (err) {
      setError(err?.response?.data || "Failed to place bid.");
    }
  };

  // Popup component that automatically closes
  const BidSuccessPopup = ({ amount, onClose }) => {
    useEffect(() => {
      const timerId = setTimeout(() => {
        onClose();
      }, 3000); // Popup will disappear after 3 seconds

      return () => clearTimeout(timerId);
    }, [onClose]);

    return (
      <motion.div
        onClick={onClose}
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: 20, transition: { duration: 0.4 } }}
        className="fixed inset-0 text-4xl text-[#3e2e1e] font-serif flex items-center justify-center z-50 backdrop-blur-2xl cursor-pointer"
      >
        ✅ You have placed a bid successfully for ₹{amount}.
      </motion.div>
    );
  };

  return (
    <div className="font-serif">
      <div className="max-w-7xl px-6 pt-10 pb-7 grid grid-cols-2">
        {/* Painting Section */}
        <section className="bg-white rounded-2xl h-[810px] shadow-xl p-8 flex-1 flex flex-col max-w-xl mx-auto xl:mx-0 transition-all duration-500">
          {painting ? (
            <>
              <h1 className="text-4xl font-extrabold text-center text-[#3e2e1e] mb-6 tracking-tight">
                {painting.title}
              </h1>
              <div className="relative overflow-hidden rounded-2xl mb-6 group">
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-80 object-cover cursor-pointer transition-transform duration-300 group-hover:scale-105"
                  onClick={() => setFullscreenImage(`http://localhost:8085${painting.imageUrl}`)}
                />
                <button
                  onClick={() => setFullscreenImage(`http://localhost:8085${painting.imageUrl}`)}
                  className="absolute bottom-3 right-3 bg-[#6b4c35]/80 text-white px-4 py-1 rounded shadow hover:bg-[#3e2e1e]/90 transition"
                >
                  🔍
                </button>
              </div>
              <div className="mb-4">
                <p className="text-lg text-gray-700 mb-2">
                  <span className="font-semibold">Description:</span> {painting.description}
                </p>
                <div className="grid grid-cols-2 gap-4 text-sm text-gray-700">
                  <p><span className="font-semibold">Length:</span> {painting.length} cm</p>
                  <p><span className="font-semibold">Breadth:</span> {painting.breadth} cm</p>
                  <p><span className="font-semibold">Seller:</span> <span className="text-[#6b4c35]">{painting.seller}</span></p>
                  <p><span className="font-semibold">{painting.is_sold ? "Buyer ID:" : "Available For Bidding"}</span> {painting.is_sold ? painting.buyer_id ?? "N/A" : ""}</p>
                </div>
              </div>
              <div className="flex flex-col gap-1 text-lg font-semibold mb-4">
                <span className="text-[#483424]">Starting Price: ₹{painting.startingPrice}</span>
                <span className="text-[#c2804d]">
                  Current Price: ₹
                  {bids.length > 0
                    ? bids[0].bid
                    : painting.final_price > 0
                      ? painting.final_price
                      : painting.starting_price}
                </span>
                {painting.final_price > 0 && (
                  <span className="text-purple-700">🎯 Final Price: ₹{painting.final_price}</span>
                )}
              </div>
              {/* The self-contained Timer component is rendered here */}
              <Timer setAuctionLive={setAuctionLive} />
            </>
          ) : (
            <div className="flex flex-col items-center justify-center h-full text-gray-500 animate-pulse">
              <svg className="w-12 h-12 mb-4 text-[#c2804d] animate-spin" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"></path>
              </svg>
              Loading painting details...
            </div>
          )}
        </section>

        {/* Bidding Section */}
        <section className="bg-white rounded-2xl shadow-xl p-8 transition w-[140%]">
          <div className="mt-6 h-1/2">
            <h4 className="text-xl font-bold mb-4 text-[#5a3c28] tracking-wide flex items-center gap-2">
              <span className="inline-block rounded-full px-3 py-1 text-yellow-800 text-base font-semibold shadow-sm">
                Top 3 Bidders...
              </span>
            </h4>
            <ul className="space-y-4">
              {bids.length === 0 ? (
                <li className="text-gray-500 italic bg-gray-50 rounded-lg px-4 py-3 shadow-sm">
                  No bids yet.
                </li>
              ) : (
                bids.slice(0, 3).map((bidder, idx) => (
                  <li key={idx} className="flex items-center justify-between bg-[#fefaf6] border border-[#e7d5c0] rounded-xl px-5 py-2 shadow-md hover:shadow-lg transition-all duration-200">
                    <div className="flex items-center gap-4">
                      <span className="text-lg font-bold text-[#bfa16a]">{bidder.rank}</span>
                      <div className="bg-[#6b4c35] border-2 border-[#6b4c35] rounded-full w-12 h-12 flex items-center justify-center font-bold text-2xl text-white shadow">
                        {bidder.name?.charAt(0).toUpperCase() || "A"}
                      </div>
                      <span className="font-semibold text-[#6b4c35] text-lg tracking-wide">
                        {bidder.name || "Anonymous"}
                      </span>
                    </div>
                    <span className="font-bold text-[#6b4c35] text-xl bg-[#eeae7d] px-4 py-2 rounded-lg shadow">
                      ₹{bidder.bid}
                    </span>
                  </li>
                ))
              )}
            </ul>
            <h3 className="text-2xl font-extrabold text-[#3e2e1e] mb-6 mt-10 tracking-wide">
              Place Your Bid
            </h3>
            <form onSubmit={handleBid} className="flex flex-col sm:flex-row gap-4 mb-6">
              <input
                id="bidAmount"
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
                disabled={!auctionLive} // Disabled state now works perfectly
                className={`${auctionLive
                    ? "bg-gradient-to-r from-[#6b4c35] to-[#ca6b22] hover:from-[#d0732c] hover:to-[#6b4c35]"
                    : "bg-gray-400 cursor-not-allowed"
                  } text-white px-8 py-3 rounded-lg font-bold text-lg transition shadow-lg cursor-pointer`}
              >
                {console.log(auctionLive)}
                Place Bid
              </button>
            </form>
            {message && !showPopup && ( // Hide text message when popup is visible
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
              <motion.img
                initial={{ scale: 0.8 }}
                animate={{ scale: 1 }}
                exit={{ scale: 0.8 }}
                src={fullscreenImage}
                alt="Fullscreen Preview"
                className="max-w-4xl w-full max-h-[90vh] object-contain rounded-lg shadow-2xl"
              />
              <button
                onClick={() => setFullscreenImage(null)}
                className="absolute top-3 right-3 text-white bg-black/70 rounded-full px-3 py-1 text-sm hover:bg-black"
              >
                ✕ Close
              </button>
            </motion.div>
          )}
        </AnimatePresence>
      </div>

      {/* Popup is now handled here, outside the main grid, and animates smoothly */}
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

// import React, { useEffect, useState } from "react";
// import axiosInstance from "../axiosInstance";
// import { useParams } from "react-router-dom";
// import { motion, AnimatePresence } from "framer-motion";
// import { jwtDecode } from "jwt-decode";

// const BiddingFrontend = () => {
//   const { paintingId } = useParams();
//   const [painting, setPainting] = useState(null);
//   const [bids, setBids] = useState([]);
//   const [bidAmount, setBidAmount] = useState("");
//   const [error, setError] = useState("");
//   const [fullscreenImage, setFullscreenImage] = useState(null);
//   const [showPopup, setShowPopup] = useState(false);
//   const [popupAmount, setPopupAmount] = useState(0);
//   const [isVisible, setIsVisible] = useState(true);
//   const [message, setMessage] = useState("");
//   const [timeLeft, setTimeLeft] = useState({
//     hours: 0,
//     minutes: 0,
//     seconds: 0,
//   });
//   const [auctionLive, setAuctionLive] = useState(false);
//   const [username, setUsername] = useState("");
//   const [winnerName, setWinnerName] = useState("");
//   const normalize = (str) => str?.trim().toLowerCase();
//   const token = localStorage.getItem("token");
//   const [userEmail, setUserEmail] = useState("");
//   const [userId, setUserId] = useState("");
//   const [decodedUser, setDecodedUser] = useState(null);

//   useEffect(() => {
//     if (token) {
//       try {
//         const decoded = jwtDecode(token);
//         setDecodedUser(decoded); // 🔹 Store decoded token
//       } catch (err) {
//         console.error("Token decode failed", err);
//       }
//     }
//   }, [token]);
//   useEffect(() => {
//     const interval = setInterval(() => {
//       setAuctionLive(isAuctionLive());
//     }, 1000);
//     return () => clearInterval(interval);
//   }, []);

//   const calculateTime = () => {
//     const now = new Date();
//     const auctionEnd = new Date();
//     auctionEnd.setHours(15, 41, 0, 0); // 4 PM today

//     const currentTime = now.getTime();
//     const endTime = auctionEnd.getTime();

//     if (currentTime < endTime) {
//       const diff = endTime - currentTime;
//       const hours = Math.floor(diff / (1000 * 60 * 60));
//       const minutes = Math.floor((diff / (1000 * 60)) % 60);
//       const seconds = Math.floor((diff / 1000) % 60);
//       setTimeLeft({ hours, minutes, seconds });
//       setMessage("Auction ends in");
//     } else {
//       setTimeLeft({ hours: 0, minutes: 0, seconds: 0 });
//       setMessage("Auction has ended");
//     }
//   };

//   useEffect(() => {
//     calculateTime();
//     const timer = setInterval(calculateTime, 1000);
//     return () => clearInterval(timer);
//   }, []);

//   const isAuctionLive = () => {
//     const now = new Date();
//     const end = new Date();
//     end.setHours(15, 41, 0, 0); // 4 PM today

//     return now <= end;
//   };

//   useEffect(() => {
//     axiosInstance
//       .get(`/auctions/${paintingId}`)
//       .then((res) => setPainting(res.data))
//       .catch(() => setPainting(null));
//   }, [paintingId]);

//   // useEffect(() => {
//   //   axiosInstance
//   //     .get(`/auctions/bid/${paintingId}`)
//   //     .then((res) => setBids(res.data))
//   //     .catch(() => setBids([]));
//   // }, [paintingId]);
//   useEffect(() => {
//     axiosInstance
//       .get(`/auctions/bid/${paintingId}`)
//       .then((res) => {
//         setBids(res.data);
//         if (res.data.length > 0) {
//           setWinnerName(res.data[0].name); // top bidder
//         }
//       })
//       .catch(() => setBids([]));
//   }, [paintingId]);

//   const handleBid = async (e) => {
//     e.preventDefault();
//     setMessage("");
//     setError("");
//     try {
//       await axiosInstance.post(`/auctions/bid/${paintingId}`, {
//         bidAmount: parseFloat(bidAmount),
//       });
//       setMessage("Bid placed successfully!");
//       setPopupAmount(parseFloat(bidAmount));
//       setShowPopup(true);
//       setBidAmount("");
//       const res = await axiosInstance.get(`/auctions/bid/${paintingId}`);
//       setBids(res.data);
//       setTimeout(() => setShowPopup(false), 3000);
//     } catch (err) {
//       setError(err?.response?.data || "Failed to place bid.");
//     }
//   };

//   const handleClose = () => {
//     setIsVisible(false);
//     setTimeout(() => {
//       onClose();
//     }, 300);
//   };

//   const BidSuccessPopup = ({ amount, onClose }) => (
//     <AnimatePresence>
//       {isVisible && (
//         <motion.div
//           onClick={handleClose}
//           initial={{ opacity: 0, y: -20 }}
//           animate={{ opacity: 1, y: 0 }}
//           exit={{ opacity: 0, y: -20 }}
//           transition={{ duration: 0.3 }}
//           className="fixed inset-0 text-3xl text-[#3e2e1e] font-serif flex items-center justify-center z-50 backdrop-blur-sm cursor-pointer"
//         >
//           ✅ You have placed a bid successfully for ₹{amount}.
//         </motion.div>
//       )}
//     </AnimatePresence>
//   );

//   return (
//     <div className="  font-serif">
//       <div className="max-w-7xl px-6 pt-10 pb-7 grid grid-cols-2">
//         {/* Painting Section */}
//         <section className="bg-white rounded-2xl h-[810px] shadow-xl p-8 flex-1 flex flex-col max-w-xl mx-auto xl:mx-0 transition-all duration-500">
//           {painting ? (
//             <>
//               <p className="text-4xl font-bold text-center text-[#3e2e1e] mb-8 ">
//                 Art work
//               </p>

//               <div className="overflow-hidden h-1/2 rounded-2xl">
//                 <img
//                   src={`http://localhost:8085${painting.imageUrl}`}
//                   alt={painting.title}
//                   className="w-full h-full object-cover cursor-pointer 
//                 transform transition-transform duration-300 hover:scale-110"
//                   onClick={() =>
//                     setFullscreenImage(
//                       `http://localhost:8085${painting.imageUrl}`
//                     )
//                   }
//                 />
//               </div>
//               <h2 className="text-3xl text-center mt-3 font-extrabold text-[#5a3c28] mb-6 tracking-wide">
//                 {painting.title}
//               </h2>
//               <p className="text-md text-gray-700 mb-2 ">
//                 <b>Description:</b> {painting.description}
//               </p>

//               <div className="grid grid-cols-2 gap-4 mb-4 text-sm text-gray-700">
//                 <p>
//                   <b>Length:</b> {painting.length} cm
//                 </p>
//                 <p>
//                   <b>Breadth:</b> {painting.breadth} cm
//                 </p>
//                 <p>
//                   <b>Seller:</b>{" "}
//                   <span className="text-[#6b4c35]">{painting.seller}</span>
//                 </p>
//                 <p>
//                   <b>
//                     {painting.is_sold ? "Buyer ID:" : "Available For Bidding"}
//                   </b>{" "}
//                   {painting.is_sold ? painting.buyer_id ?? "N/A" : ""}
//                 </p>
//               </div>

//               <div className="flex flex-col gap-2 text-lg font-semibold mb-4">
//                 <span className="text-[#483424]">
//                   Starting: ₹{painting.startingPrice}
//                 </span>
//                 <span className="text-[#c2804d]">
//                   Current Price: ₹
//                   {bids.length > 0
//                     ? bids[0].bid
//                     : painting.final_price > 0
//                     ? painting.final_price
//                     : painting.starting_price}
//                 </span>
//                 {painting.final_price > 0 && (
//                   <span className="text-purple-700">
//                     🎯 Final Price: ₹{painting.final_price}
//                   </span>
//                 )}
//               </div>

//               <span className="text-sm text-gray-700 font-semibold">
//                 ⏳ {String(timeLeft.hours).padStart(2, "0")} hrs :{" "}
//                 {String(timeLeft.minutes).padStart(2, "0")} min :{" "}
//                 {String(timeLeft.seconds).padStart(2, "0")} sec Remaining
//               </span>
//             </>
//           ) : (
//             <div className="text-gray-500 animate-pulse">
//               Loading painting details...
//             </div>
//           )}
//         </section>

//         {/* Bidding Section */}
//         <section className="bg-white rounded-2xl shadow-xl p-8 transition w-[140%] ">
//           {/* Bidders List */}
//           <div className="mt-6 h-1/2">
//             <h4 className="text-xl font-bold mb-4 text-[#5a3c28] tracking-wide flex items-center gap-2">
//               <span className="inline-block  rounded-full px-3 py-1 text-yellow-800 text-base font-semibold shadow-sm">
//                 Top 3 Bidders...
//               </span>
//             </h4>
//             <ul className="space-y-4">
//               {!token ? (
//                 <li className="text-red-500 italic bg-red-50 rounded-lg px-4 py-3 shadow-sm">
//                   Login to view bids
//                 </li>
//               ) : bids.length === 0 ? (
//                 <li className="text-gray-500 italic bg-gray-50 rounded-lg px-4 py-3 shadow-sm">
//                   No bids yet.
//                 </li>
//               ) : (
//                 bids.slice(0, 3).map((bidder, idx) => (
//                   <li
//                     key={idx}
//                     className="flex items-center justify-between bg-[#fefaf6] border border-[#e7d5c0] rounded-xl px-5 py-2 shadow-md hover:shadow-lg transition-all duration-200"
//                   >
//                     <div className="flex items-center gap-4">
//                       <span className="text-lg font-bold text-[#bfa16a]">
//                         {bidder.rank}
//                       </span>
//                       <div className="bg-[#6b4c35] border-2 border-[#6b4c35] rounded-full w-12 h-12 flex items-center justify-center font-bold text-2xl text-white shadow">
//                         {bidder.name?.charAt(0).toUpperCase() || "A"}
//                       </div>
//                       <span className="font-semibold text-[#6b4c35] text-lg tracking-wide">
//                         {bidder.name || "Anonymous"}
//                       </span>
//                     </div>
//                     <span className="font-bold text-[#6b4c35] text-xl bg-[#eeae7d] px-4 py-2 rounded-lg shadow">
//                       ₹{bidder.bid}
//                     </span>
//                   </li>
//                 ))
//               )}
//             </ul>
//             <h3 className="text-2xl font-extrabold text-[#3e2e1e] mb-6 mt-10 tracking-wide">
//               Place Your Bid
//             </h3>
//             <form
//               onSubmit={handleBid}
//               className="flex flex-col sm:flex-row gap-4 mb-6"
//             >
//               <input
//                 type="number"
//                 min="1"
//                 value={bidAmount}
//                 onChange={(e) => setBidAmount(e.target.value)}
//                 placeholder="Enter your bid (₹)"
//                 className="flex-1 px-5 py-3 border border-[#e7d5c0] rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 transition bg-[#fefaf6] text-[#3e2e1e] font-medium text-lg shadow"
//                 required
//               />
//               <button
//                 type="submit"
//                 disabled={!auctionLive}
//                 className={`${
//                   auctionLive
//                     ? "bg-gradient-to-r from-[#6b4c35] to-[#ca6b22] hover:from-[#d0732c] hover:to-[#6b4c35]"
//                     : "bg-gray-400 cursor-not-allowed"
//                 } text-white px-8 py-3 rounded-lg font-bold text-lg transition shadow-lg`}
//               >
//                 Place Bid
//               </button>
//             </form>
//             {message && (
//               <div className="text-green-700 font-semibold text-center mb-4 transition duration-300 bg-green-50 rounded-lg px-4 py-3 shadow">
//                 {message}
//               </div>
//             )}
//             {error && (
//               <div className="text-red-600 font-semibold text-center mb-4 transition duration-300 bg-red-50 rounded-lg px-4 py-3 shadow">
//                 {error}
//               </div>
//             )}
//             {!auctionLive && bids.length > 0 && (
//               <div className="bg-yellow-50 border border-yellow-300 text-yellow-800 p-4 rounded-xl text-lg font-semibold shadow mb-6">
//                 {decodedUser?.userId === bids[0]?.userId ? (
//                   <>
//                     🎉 Congratulations {bids[0].name}!<br />
//                     You won the auction with a bid of ₹{bids[0].bid}!<br />
//                     💰 Amount will be deducted from your wallet.
//                   </>
//                 ) : (
//                   <>
//                     🏆 {bids[0].name} won the auction with a bid of ₹
//                     {bids[0].bid}.
//                   </>
//                 )}
//               </div>
//             )}
//           </div>
//         </section>

//         <AnimatePresence>
//           {fullscreenImage && (
//             <motion.div
//               initial={{ opacity: 0 }}
//               animate={{ opacity: 1 }}
//               exit={{ opacity: 0 }}
//               className="fixed inset-0 bg-black/80 flex items-center justify-center z-50"
//               onClick={() => setFullscreenImage(null)}
//             >
//               <motion.div
//                 initial={{ scale: 0.8 }}
//                 animate={{ scale: 1 }}
//                 exit={{ scale: 0.8 }}
//                 className="relative max-w-4xl w-full"
//               >
//                 <img
//                   src={fullscreenImage}
//                   alt="Fullscreen Preview"
//                   className="w-full max-h-[90vh] object-contain rounded-lg shadow-2xl"
//                 />
//                 <button
//                   onClick={() => setFullscreenImage(null)}
//                   className="absolute top-3 right-3 text-white bg-black/70 rounded-full px-3 py-1 text-sm hover:bg-black"
//                 >
//                   ✕ Close
//                 </button>
//               </motion.div>
//             </motion.div>
//           )}
//         </AnimatePresence>
//         <AnimatePresence>
//           {showPopup && (
//             <BidSuccessPopup
//               amount={popupAmount}
//               onClose={() => setShowPopup(false)}
//             />
//           )}
//         </AnimatePresence>

//         <AnimatePresence>
//           {showPopup && (
//             <BidSuccessPopup
//               amount={popupAmount}
//               onClose={() => setShowPopup(false)}
//             />
//           )}
//         </AnimatePresence>
//       </div>
//     </div>
//   );
// };

// export default BiddingFrontend;
