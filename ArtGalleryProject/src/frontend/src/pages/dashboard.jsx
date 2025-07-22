import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";
import axios from "axios";

const newsItems = [
  {
    title: "Record-Breaking Sale: Monet’s Water Lilies",
    description:
      "Claude Monet’s 'Nymphéas' sold for a staggering $110 million, setting a new record in the modern art category.",
    price: "$110M",
    date: "2 JUL 2025",
    seller: "Sotheby’s London",
    image:
      "https://images.unsplash.com/photo-1578301977886-43be7b983104?w=500&auto=format&fit=crop&q=60",
  },
  {
    title: "Basquiat’s ‘Skull’ Dominates New York Auction",
    description:
      "Jean-Michel Basquiat's iconic piece sold for $93 million during Christie’s spring evening sale.",
    price: "$93M",
    date: "28 JUN 2025",
    seller: "Christie’s NY",
    image:
      "https://images.unsplash.com/photo-1580136579312-94651dfd596d?w=500&auto=format&fit=crop&q=60",
  },
  {
    title: "Da Vinci Sketch Draws Global Bids",
    description:
      "A rare Leonardo da Vinci sketch attracted over 30 international bids, closing at $65 million.",
    price: "$65M",
    date: "26 JUN 2025",
    seller: "Private Auction",
    image:
      "https://images.unsplash.com/photo-1578926078693-4eb3d4499e43?w=500&auto=format&fit=crop&q=60",
  },
];

const MainTab = () => {
  const [index, setIndex] = useState(0);
  const [paintings, setPaintings] = useState([]);
  const [carouselItems, setCarouselItems] = useState([]);
  const [hasNextPage, setHasNextPage] = useState(false);

  // Fetch paintings from backend API with pagination
  const fetchPaintings = async (page = 0) => {
    try {
      const res = await axios.get(
        `http://localhost:8085/auctions?pageNo=${page}`
      );
      const data = res.data.content || res.data;
      setPaintings(data);

      // Check if next page has paintings
      const nextRes = await axios.get(
        `http://localhost:8085/auctions?pageNo=${page + 1}`
      );
      const nextData = nextRes.data.content || nextRes.data;
      setHasNextPage(Array.isArray(nextData) ? nextData.length > 0 : false);

      // Use first 3 unsold paintings for carousel
      const unsold = data.filter((p) => !p.isSold);
      setCarouselItems(unsold.slice(0, 3));
    } catch (err) {
      setPaintings([]);
      setHasNextPage(false);
    }
  };

  useEffect(() => {
    fetchPaintings();
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((prev) =>
        carouselItems.length > 0 ? (prev + 1) % carouselItems.length : 0
      );
    }, 5000);
    return () => clearInterval(interval);
  }, [carouselItems]);

  const SlideCard = ({ data }) => (
    <div className="flex w-full font-serif h-[890px]">
      <div className="w-1/2 pt-10 px-10 flex flex-col justify-center bg-[#f8f5f0]">
        <div className="text-xs uppercase text-gray-600 mb-2">
          Auction Series
        </div>
        <h1 className="text-5xl font-serif mb-4">{data.title}</h1>
        <p className="text-lg text-gray-700 mb-6">
          {data.description || "Art at its finest"}
        </p>
        <div className="flex items-center gap-4 text-sm text-gray-600 mb-6">
          <div className="flex items-center gap-1">
            📅 {data.date || "Coming Soon"}
          </div>
          <div className="flex items-center gap-1">
            📍 {data.location || "Online"}
          </div>
        </div>
        <Link
          to="/discover"
          className="px-4 py-2 border border-black text-black text-sm hover:bg-black hover:text-white transition w-fit"
        >
          DISCOVER NOW
        </Link>
        <div className="flex mt-10 gap-2">
          {carouselItems.map((_, i) => (
            <div
              key={i}
              className={`w-2 h-2 rounded-full transition-all ${
                i === index ? "bg-black" : "bg-gray-400"
              }`}
            ></div>
          ))}
        </div>
      </div>

      <div className="w-1/2 h-full">
        <img
          src={`http://localhost:8085${data.imageUrl}`}
          alt={data.title}
          className="w-full h-full object-cover"
        />
      </div>
    </div>
  );

  const upcomingAuctions = paintings.filter((p) => !p.isSold);

  return (
    <>
      {/* 🔹 Carousel */}
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        <div className="relative h-[890px] w-full overflow-hidden font-serif">
          <div className="absolute inset-0">
            <AnimatePresence initial={false} mode="wait">
              {carouselItems.map((item, i) =>
                i === index ? (
                  <motion.div
                    key={i}
                    initial={{ opacity: 0, x: 100 }}
                    animate={{ opacity: 1, x: 0 }}
                    exit={{ opacity: 0, x: 100 }}
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
      </motion.div>

      {/* 🔹 Upcoming Auctions */}
      <div className="py-16 px-10 font-serif">
        <div className="flex items-center justify-between mb-10">
          <h2 className="text-4xl font-serif">Upcoming auctions</h2>
          <Link to="/auctions" className="ml-4 text-sm font-medium underline">
            View All →
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          {upcomingAuctions.length > 0 ? (
            upcomingAuctions.map((auction, i) => (
              <div
                key={i}
                className="flex hover:scale-105 bg-[#f8f5f0] duration-500 hover:shadow-2xl hover:shadow-black rounded-2xl cursor-pointer gap-4"
              >
                <img
                  src={`http://localhost:8085${auction.imageUrl}`}
                  alt={auction.title}
                  className="w-32 h-32 object-cover rounded-l-2xl"
                />
                <div className="mt-4">
                  <p className="text-xs text-gray-500 uppercase">
                    {auction.date || "Upcoming"} | Auction
                  </p>
                  <h3 className="text-lg font-medium hover:text-gray-700">
                    {auction.title}
                  </h3>
                  <div className="text-sm text-gray-600 mt-1">
                    📍 {auction.location || "Online"}
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className="text-gray-500 col-span-full text-center">
              No upcoming auctions found.
            </p>
          )}
        </div>
      </div>

      {/* 🔹 Auction News */}
      <div className="px-10 py-16 font-serif">
        <h2 className="text-4xl font-serif mb-10">Auction News & Highlights</h2>
        <div className="grid md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-8">
          {newsItems.map((item, i) => (
            <div
              key={i}
              className="shadow-2xl rounded overflow-hidden bg-[#f8f5f0] duration-400 hover:shadow-2xl hover:shadow-black cursor-pointer"
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
