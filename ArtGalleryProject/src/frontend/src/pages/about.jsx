import React, { useState } from "react";
import { motion } from "framer-motion";
import { FaPalette, FaGlobe, FaUsers, FaStar, FaQuestion, FaChevronDown, FaChevronUp } from "react-icons/fa";

const WaveDivider = () => (
  <svg className="absolute bottom-0 left-0 w-full h-24 z-10" viewBox="0 0 1440 320" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill="#674d33" fillOpacity="1" d="M0,224L48,213.3C96,203,192,181,288,192C384,203,480,245,576,240C672,235,768,181,864,176C960,171,1056,213,1152,229.3C1248,245,1344,235,1392,229.3L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>
);

const FAQItem = ({ question, answer, isOpen, onToggle }) => (
  <motion.div
    initial={{ opacity: 0, y: 20 }}
    animate={{ opacity: 1, y: 0 }}
    transition={{ duration: 0.5 }}
    className="border border-[#674d33]/20 rounded-xl overflow-hidden shadow-lg bg-white/90 backdrop-blur-sm"
  >
    <button
      onClick={onToggle}
      className="w-full px-6 py-4 text-left flex items-center justify-between hover:bg-[#674d33]/5 transition-colors duration-200"
    >
      <span className="font-semibold text-[#674d33] text-lg">{question}</span>
      {isOpen ? (
        <FaChevronUp className="text-[#674d33] text-lg transition-transform duration-200" />
      ) : (
        <FaChevronDown className="text-[#674d33] text-lg transition-transform duration-200" />
      )}
    </button>
    <motion.div
      initial={false}
      animate={{ height: isOpen ? "auto" : 0, opacity: isOpen ? 1 : 0 }}
      transition={{ duration: 0.3, ease: "easeInOut" }}
      className="overflow-hidden"
    >
      <div className="px-6 pb-4 text-gray-700 leading-relaxed">
        {answer}
      </div>
    </motion.div>
  </motion.div>
);

const About = () => {
  const [openItems, setOpenItems] = useState(new Set([0])); // First item open by default

  const toggleItem = (index) => {
    const newOpenItems = new Set(openItems);
    if (newOpenItems.has(index)) {
      newOpenItems.delete(index);
    } else {
      newOpenItems.add(index);
    }
    setOpenItems(newOpenItems);
  };

  const faqData = [
    {
      question: "How do I create an account?",
      answer: "Go to the signup page, enter your details, and verify your email. It's a simple process that takes just a few minutes to get started with Art Fusion."
    },
    {
      question: "What categories of art do you have?",
      answer: "We offer portraits, landscapes, abstract, modern, and traditional art. Our diverse collection includes works from both established and emerging artists across various styles and mediums."
    },
    {
      question: "How do I bid for a painting?",
      answer: "Click the painting, enter your bid amount, and submit. Our progressive bidding system ensures fair competition, where each new bid must be higher than the last one."
    },
    {
      question: "What happens if I win a bid?",
      answer: "You'll receive a confirmation and the painting will be added to your purchases. The amount will be automatically deducted from your wallet, and you'll get detailed shipping information."
    },
    {
      question: "How do I add money to my wallet?",
      answer: "Go to Wallet > Add Funds and choose a payment method. We accept credit/debit cards, UPI, and net banking for your convenience."
    },
    {
      question: "Can I filter paintings by artist or price?",
      answer: "Yes, you can filter by artist name, price range, and category. Use our advanced search and filter options to find exactly what you're looking for."
    },
    {
      question: "How do I know if a painting is original?",
      answer: "Verified paintings have a \"Verified\" tag and come with authenticity proof. All our artworks go through a thorough verification process by our expert team."
    },
    {
      question: "Where can I see my previous purchases?",
      answer: "Visit your profile and check the \"Purchase History\" tab. You can also download receipts and track shipping information for all your orders."
    },
    {
      question: "How do I upload my painting?",
      answer: "Go to the Upload page, fill the form, and submit the painting image. Our team will review your submission and verify it before it goes live on the platform."
    },
    {
      question: "What does 'Verified' painting mean?",
      answer: "It means our admin has approved and verified the artwork. Verified paintings have been thoroughly checked for authenticity, quality, and meet our platform standards."
    }
  ];

  return (
  <div className="relative min-h-screen flex flex-col justify-center items-center overflow-hidden bg-[#f8f5f0]">
    {/* Hero Section */}
    <div className="absolute inset-0 z-0">
      <img
        src="https://images.unsplash.com/photo-1464983953574-0892a716854b?auto=format&fit=crop&w=1200&q=80"
        alt="Art Gallery Hero"
        className="w-full h-full object-cover opacity-60 scale-105"
      />
      <div className="absolute inset-0 bg-gradient-to-b from-[#fffaf3]/80 via-[#fffaf3]/60 to-[#674d33]/70" />
    </div>
    <motion.div
      initial={{ opacity: 0, y: 40 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.7 }}
      className="relative z-20 max-w-4xl mx-auto bg-white/90 backdrop-blur-lg rounded-3xl shadow-2xl border border-[#674d33]/20 p-10 mt-24 mb-24 text-gray-800"
    >
      <h1 className="text-5xl font-serif font-bold mb-4 text-[#674d33] flex items-center gap-3 drop-shadow-lg">
        <FaPalette className="text-[#674d33] text-4xl" /> About Art Fusion
      </h1>
      <p className="text-lg mb-6">
        Art Fusion is a premier online art gallery and auction platform, connecting artists, collectors, and enthusiasts from around the world. Our mission is to celebrate creativity, foster artistic growth, and make art accessible to everyone.
      </p>
      <div className="flex flex-col md:flex-row gap-8 mb-6">
        <div className="flex-1">
          <h2 className="text-2xl font-semibold mb-2 text-[#674d33] flex items-center gap-2"><FaGlobe /> Our Vision</h2>
          <p>
            We believe in the transformative power of art. By bridging the gap between artists and audiences, we aim to inspire, educate, and enrich lives through exceptional works of art.
          </p>
        </div>
        <div className="flex-1">
          <h2 className="text-2xl font-semibold mb-2 text-[#674d33] flex items-center gap-2"><FaUsers /> What We Offer</h2>
          <ul className="list-disc pl-6 mb-2">
            <li>Curated online auctions featuring renowned and emerging artists</li>
            <li>Secure and transparent bidding process</li>
            <li>Personalized art discovery and recommendations</li>
            <li>Support for artists to showcase and sell their work globally</li>
          </ul>
        </div>
      </div>
      <div className="flex items-center gap-2 text-md text-gray-600 border-t pt-4"><FaStar className="text-yellow-500" /> Join us in celebrating the world of art, one masterpiece at a time.</div>
      
      {/* FAQ Section */}
      <div className="mt-8 border-t pt-8">
        <h2 className="text-3xl font-serif font-bold mb-6 text-[#674d33] flex items-center gap-3">
          <FaQuestion className="text-[#674d33] text-2xl" /> 
          Frequently Asked Questions
        </h2>
        <p className="text-gray-600 mb-6">
          Find answers to the most common questions about Art Fusion. Can't find what you're looking for? 
          Contact our support team for personalized assistance.
        </p>
        
        <div className="space-y-4">
          {faqData.map((item, index) => (
            <FAQItem
              key={index}
              question={item.question}
              answer={item.answer}
              isOpen={openItems.has(index)}
              onToggle={() => toggleItem(index)}
            />
          ))}
        </div>
      </div>
    </motion.div>
    <WaveDivider />
  </div>
  );
};

export default About; 