import { Link } from "react-router-dom";
import { useState } from "react";
import WalletModal from "./WalletModal";
import ProfileModal from "./ProfileModal";

const Header = () => {
  const [isWalletOpen, setIsWalletOpen] = useState(false);
  const token = localStorage.getItem("token");
  const navLinks = {
    auctions: "/auctions",
    sell: "/sell",
    // departments: "/departments",
    discover: "/discover",
    shop: "/shop",
  };

  // ✅ Check if user is logged in
  const isLoggedIn = !!token; // Use token for login check

  return (
    <header className="fixed top-0 left-0 w-full bg-white z-50 border-b shadow-md text-sm">
      <div className="flex items-center justify-between px-6 py-5">
        {/* Left side: Logo and Menu */}
        <div className="flex items-center gap-8">
          <Link to="/">
            <button className="cursor-pointer">
              <h1 className="text-xl font-serif tracking-wide">FUSION ART</h1>
            </button>
          </Link>

          <nav className="flex gap-6 uppercase font-serif tracking-wider text-xs text-gray-700">
            {Object.keys(navLinks).map((item) => (
              <Link to={navLinks[item]} key={item} className="relative group">
                {item.charAt(0).toUpperCase() + item.slice(1)}
                <span className="absolute left-0 -bottom-1 w-0 h-[2px] bg-gray-800 transition-all duration-300 group-hover:w-full"></span>
              </Link>
            ))}
          </nav>
        </div>

        {/* Right side: Search and Login/Profile */}
        <div className="flex items-center gap-4">
          <div className="relative group">
            {/* Wallet Icon Button */}
            <button
              onClick={() => setIsWalletOpen(true)}
              className="w-9 h-9 shadow-2xl shadow-black flex items-center justify-center rounded-full bg-gray-200 hover:bg-gray-300 transition duration-200"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-5 h-5 text-gray-700"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-2m4-5h-4a1 1 0 00-1 1v2a1 1 0 001 1h4v-4z"
                />
              </svg>
            </button>

            {/* Hover Box with Balance */}
          </div>

          <span className="border-l h-4 border-gray-400"></span>

          {/* ✅ Conditional route */}
          {isLoggedIn ? (
            <Link to="/profile">
              <button className="flex items-center cursor-pointer border-1 duration-300 rounded-full hover:scale-115 gap-1 text-sm text-gray-800">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="w-5 h-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M5.121 17.804A13.937 13.937 0 0112 15c2.21 0 4.29.534 6.121 1.477M15 10a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
              </button>
            </Link>
          ) : (
            <Link to="/login">
              <button className=" hover:shadow-lg hover:shadow-gray-300 border-1  cursor-pointer h-8 w-16 rounded-2xl px-2  text-sm text-gray-800">
                {/* <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="w-5 h-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M5.121 17.804A13.937 13.937 0 0112 15c2.21 0 4.29.534 6.121 1.477M15 10a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg> */}
                Login
              </button>
            </Link>
          )}
        </div>
      </div>

      {/* Wallet Modal */}
      <WalletModal
        isOpen={isWalletOpen}
        onClose={() => setIsWalletOpen(false)}
      />
    </header>
  );
};

export default Header;
