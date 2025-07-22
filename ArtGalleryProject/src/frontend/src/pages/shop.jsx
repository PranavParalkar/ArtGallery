import React from "react";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

const shopItems = [
  {
    id: 1,
    title: "Blue Reverie",
    artist: "Meera Shah",
    price: "₹18,000",
    category: "Abstract",
    image:
      "https://images.unsplash.com/photo-1625737186484-f8d4cd0ab8c6?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Qmx1ZSUyMFJldmVyaWUlMjBwYWludGluZ3xlbnwwfHwwfHx8MA%3D%3D",
  },
  {
    id: 2,
    title: "Divine Kathakali",
    artist: "Rahul Nair",
    price: "₹25,000",
    category: "Traditional",
    image:
      "https://plus.unsplash.com/premium_photo-1691030926024-4a5664b37ef8?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8RGl2aW5lJTIwS2F0aGFrYWxpJTIwcGFpbnRpbmd8ZW58MHx8MHx8fDA%3D",
  },
  {
    id: 3,
    title: "Cosmic Gaze",
    artist: "Aanya Rao",
    price: "₹22,500",
    category: "Modern",
    image:
      "https://plus.unsplash.com/premium_photo-1682308431525-6130fb37f641?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Q29zbWljJTIwR2F6ZXBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    id: 4,
    title: "Madhubani Bloom",
    artist: "Ramesh Verma",
    price: "₹15,000",
    category: "Folk",
    image:
      "https://plus.unsplash.com/premium_photo-1720253873735-5acba3758beb?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8TWFkaHViYW5pJTIwQmxvb20lMjBwYWludGluZ3xlbnwwfHwwfHx8MA%3D%3D",
  },
  {
    id: 5,
    title: "Nature’s Verse",
    artist: "Tanya Kulkarni",
    price: "₹20,000",
    category: "Landscape",
    image:
      "https://plus.unsplash.com/premium_photo-1711987235866-c0c38150a8eb?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8TmF0dXJlJUUyJTgwJTk5cyUyMFZlcnNlJTIwcGFpbnRpbmd8ZW58MHx8MHx8fDA%3D",
  },
  {
    id: 6,
    title: "Street Harmony",
    artist: "Varun Jha",
    price: "₹13,500",
    category: "Urban",
    image:
      "https://images.unsplash.com/photo-1650531220654-8c87d2e311e2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8U3RyZWV0JTIwSGFybW9ueSUyMHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    id: 7,
    title: "Whispers of the Desert",
    artist: "Sana Ahmed",
    price: "₹19,800",
    category: "Minimalist",
    image:
      "https://plus.unsplash.com/premium_photo-1694475441593-43f836bedc77?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8V2hpc3BlcnMlMjBvZiUyMHRoZSUyMERlc2VydCUyMHBhaW50aW5nfGVufDB8fDB8fHww",
  },
  {
    id: 8,
    title: "Golden Silence",
    artist: "Vikram Joshi",
    price: "₹27,000",
    category: "Spiritual",
    image:
      "https://plus.unsplash.com/premium_photo-1670966447392-b5688615370b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8R29sZGVuJTIwU2lsZW5jZSUyMHBhaW50aW5nfGVufDB8fDB8fHww",
  },
];

const Shop = () => {
  const navigate = useNavigate();

  const handleBuyNow = (item) => {
    // Navigate to the server endpoint that serves the orders.html page
    // We can pass the item price as a query parameter to pre-fill the amount field
    const price = item.price.replace(/[^\d]/g, ''); // Extract numeric value from price
    window.location.href = `http://localhost:8085/orders?amount=${price}&item=${encodeURIComponent(item.title)}`;
  };

  return (
    <div className=" w-[100%] px-6 py-12 font-serif">
      <motion.h1
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="text-4xl font-bold text-center text-[#5a3c28] mb-12"
      >
        Art Store
      </motion.h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 max-w-[1300px] mx-auto">
        {shopItems.map((item, i) => (
          <motion.div
            key={item.id}
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: i * 0.1 }}
            className="bg-white rounded-2xl overflow-hidden shadow-md hover:shadow-2xl hover:shadow-amber-900 transition transform hover:-translate-y-2  duration-300"
          >
            <img
              src={item.image}
              alt={item.title}
              className="w-full h-60 object-cover"
            />
            <div className="p-5 flex flex-col justify-between h-40">
              <div>
                <h2 className="text-lg font-bold text-[#6b4c35]">
                  {item.title}
                </h2>
                <p className="text-sm text-gray-600 mb-1">by {item.artist}</p>
                <p className="text-sm text-gray-500">
                  Category: {item.category}
                </p>
              </div>
              <div className="mt-4 flex justify-between items-center">
                <span className="text-md font-semibold text-[#a17b5d]">
                  {item.price}
                </span>
                <button 
                  onClick={() => handleBuyNow(item)}
                  className="px-4 py-2 rounded-full text-sm font-semibold text-white bg-[#a17b5d] hover:bg-[#8c6448] transition hover:cursor-pointer"
                >
                  Buy Now
                </button>
              </div>
            </div>
          </motion.div>
        ))}
      </div>
    </div>
  );
};

export default Shop;
