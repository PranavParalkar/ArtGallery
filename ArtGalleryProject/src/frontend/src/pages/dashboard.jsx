import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";

const carouselData = [
  {
    title: "Classic Week",
    subtitle: "Art from antiquity to the 21st century",
    date: "1–9 JUL",
    location: "",
    image:
      "https://plus.unsplash.com/premium_photo-1664013263421-91e3a8101259?w=500&auto=format&fit=crop&q=60",
  },
  {
    title: "Impressionist Masterpieces",
    subtitle: "Bold colors, bold statements",
    date: "10–15 JUL",
    location: "",
    image:
      "https://images.unsplash.com/flagged/photo-1572392640988-ba48d1a74457?w=500&auto=format&fit=crop&q=60",
  },
  {
    title: "Modern Art Highlights",
    subtitle: "From Warhol to Banksy",
    date: "20–25 JUL",
    location: "",
    image:
      "https://images.unsplash.com/photo-1579965342575-16428a7c8881?w=500&auto=format&fit=crop&q=60",
  },
];
const upcomingAuctions = [
  {
    title: "Post-War to Present: Online",
    date: "17 JUN – 3 JUL",
    location: "London",
    type: "Online Auction",
    image:
      "https://plus.unsplash.com/premium_photo-1678812165213-12dc8d1f3e19?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8cGFpbnRpbmd8ZW58MHx8MHx8fDA%3D",
  },
  {
    title: "Valuable Books and Manuscripts",
    date: "9 JUL",
    location: "London",
    type: "Online Auction",
    image:
      "https://images.unsplash.com/photo-1579541513287-3f17a5d8d62c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title:
      "Pressing Forward: Contemporary Editions from a Private Detroit Collection",
    date: "2 JUL – 16 JUL",
    location: "New York",
    type: "Online Auction",
    image:
      "https://images.unsplash.com/photo-1579541814924-49fef17c5be5?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title: "Contemporary Edition: New York",
    date: "2 JUL – 17 JUL",
    location: "New York",
    type: "Online Auction",
    image:
      "https://images.unsplash.com/photo-1549289524-06cf8837ace5?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title: "First Open | Post-War & Contemporary Art",
    date: "3 JUL – 18 JUL",
    location: "New York",
    type: "Online Auction",
    image:
      "https://images.unsplash.com/photo-1579783901586-d88db74b4fe4?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title: "A Goal in Life: Leo Messi x Refik Anadol",
    date: "8 JUL – 22 JUL",
    location: "New York",
    type: "Online Auction",
    image:
      "https://images.unsplash.com/photo-1577083639236-0f560d3d771c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
];

const newsItems = [
  {
    title: "Record-Breaking Sale: Monet’s Water Lilies",
    description:
      "Claude Monet’s 'Nymphéas' sold for a staggering $110 million, setting a new record in the modern art category.",
    price: "$110M",
    date: "2 JUL 2025",
    seller: "Sotheby’s London",
    image:
      "https://images.unsplash.com/photo-1578301977886-43be7b983104?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzV8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title: "Basquiat’s ‘Skull’ Dominates New York Auction",
    description:
      "Jean-Michel Basquiat's iconic piece sold for $93 million during Christie’s spring evening sale.",
    price: "$93M",
    date: "28 JUN 2025",
    seller: "Christie’s NY",
    image:
      "https://images.unsplash.com/photo-1580136579312-94651dfd596d?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDJ8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    title: "Da Vinci Sketch Draws Global Bids",
    description:
      "A rare Leonardo da Vinci sketch attracted over 30 international bids, closing at $65 million.",
    price: "$65M",
    date: "26 JUN 2025",
    seller: "Private Auction",
    image:
      "https://images.unsplash.com/photo-1578926078693-4eb3d4499e43?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mzl8fHBhaW50aW5nfGVufDB8fDB8fHww",
  },
];
const MainTab = () => {
  const [index, setIndex] = useState(0);
  const [direction, setDirection] = useState("next");

  useEffect(() => {
    const interval = setInterval(() => {
      setDirection("next");
      setIndex((prev) => (prev + 1) % carouselData.length);
    }, 2000);
    return () => clearInterval(interval);
  }, []);

  const SlideCard = ({ data }) => (
    <div className="flex w-full h-[890px]">
      {/* Left Content */}
      <div className="w-1/2 pt-10 px-10 flex flex-col justify-center bg-[#f8f5f0]">
        <div className="text-xs uppercase text-gray-600 mb-2">
          Auction Series
        </div>
        <h1 className="text-5xl font-serif mb-4">{data.title}</h1>
        <p className="text-lg text-gray-700 mb-6">{data.subtitle}</p>
        <div className="flex items-center gap-4 text-sm text-gray-600 mb-6">
          <div className="flex items-center gap-1">📅 {data.date}</div>
        </div>
        <Link
          to="/discover"
          className="px-4 py-2 border border-black text-black text-sm hover:bg-black hover:text-white transition w-fit"
        >
          DISCOVER NOW
        </Link>

        {/* Indicators */}
        <div className="flex mt-10 gap-2">
          {carouselData.map((_, i) => (
            <div
              key={i}
              className={`w-2 h-2 rounded-full transition-all ${
                i === index ? "bg-black" : "bg-gray-400"
              }`}
            ></div>
          ))}
        </div>
      </div>

      {/* Right Image */}
      <div className="w-1/2 h-full">
        <img
          src={data.image}
          alt={data.title}
          className="w-full h-full object-cover"
        />
      </div>
    </div>
  );

  return (
    <>
      <div className="relative h-[890px] w-full overflow-hidden bg-[#f8f5f0]">
        <div className="absolute inset-0">
          <AnimatePresence initial={false} mode="wait">
            {carouselData.map((item, i) =>
              i === index ? (
                <motion.div
                  key={i}
                  initial={{ opacity: 0, x: 100 }} // ↓ start from bottom
                  animate={{ opacity: 1, x: 0 }} // → animate to center
                  exit={{ opacity: 0, x: 100 }} // ↑ exit to top
                  transition={{ duration: 0.7, ease: "easeInOut" }}
                  className="absolute top-0 left-0 w-full h-full z-20"
                >
                  <SlideCard data={item} />
                </motion.div>
              ) : null
            )}
          </AnimatePresence>
        </div>
      </div>
      <div className="py-16 px-10 bg-[#f8f5f0]">
        <div className="flex items-center justify-between mb-10">
          <h2 className="text-4xl font-serif">Upcoming auctions</h2>
          <div className="space-x-4">
            <button className="px-3 py-1 border hover:shadow-2xl hover:shadow-black hover:scale-95 duration-200 rounded border-black text-sm font-medium bg-black text-white">
              ALL
            </button>
            <button className="px-3 py-1 border hover:shadow-2xl hover:shadow-black hover:scale-95 duration-200 rounded border-gray-400 text-sm font-medium">
              ONLINE
            </button>
            <button className="px-3 py-1 border hover:shadow-2xl hover:shadow-black hover:scale-95 duration-200 rounded border-gray-400 text-sm font-medium">
              LIVE
            </button>
            <button className="px-3 py-1 border hover:shadow-2xl hover:shadow-black hover:scale-95 duration-200 rounded border-gray-400 text-sm font-medium">
              NEAR YOU
            </button>
            <Link
              to="/all-auctions"
              className="ml-4 text-sm font-medium underline"
            >
              View All →
            </Link>
          </div>
        </div>

        {/* Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          {upcomingAuctions.map((auction, i) => (
            <div
              key={i}
              className="flex hover:scale-105 duration-500 hover:shadow-2xl hover:shadow-black rounded-2xl cursor-pointer gap-4"
            >
              <img
                src={auction.image}
                alt={auction.title}
                className="w-32 h-32 object-cover rounded-l-2xl"
              />
              <div>
                <p className="text-xs text-gray-500 uppercase">
                  {auction.date} | {auction.type}
                </p>
                <h3 className="text-lg font-medium  hover:text-gray-700 cursor-pointer">
                  {auction.title}
                </h3>
                <div className="text-sm text-gray-600 mt-1">
                  📍 {auction.location}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className="px-10 py-16 bg-[#f8f5f0]">
        <h2 className="text-4xl font-serif mb-10">Auction News & Highlights</h2>
        <div className="grid md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-8">
          {newsItems.map((item, i) => (
            <div
              key={i}
              className="shadow-2xl rounded overflow-hidden   duration-400 hover:shadow-2xl hover:shadow-black  cursor-pointer"
            >
              <img
                src={item.image}
                alt={item.title}
                className="h-80 w-full object-cover"
              />
              <div className="p-4">
                <div className="text-xs text-gray-500 mb-1">
                  {item.date} | {item.seller}
                </div>
                <h3 className="text-lg font-semibold mb-2">{item.title}</h3>
                <p className="text-sm text-gray-600 mb-4">{item.description}</p>
                <div className="text-sm font-bold text-black">
                  Final Bid: {item.price}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

    </>
  );
};

export default MainTab;
