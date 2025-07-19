import { Link } from "react-router-dom";

const Header = () => {
  const navLinks = {
    auctions: "/auctions",
    sell: "/sell",
    departments: "/departments",
    discover: "/discover",
    shop: "/shop",
  };

  // ✅ Check if user is logged in
  const isLoggedIn = localStorage.getItem("userName"); // or "token" if that's what you store

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
          <div className="relative">
            <input
              type="text"
              placeholder="Search by keyword"
              className="pl-2 pr-6 py-1 text-sm border-b border-gray-400 focus:outline-none"
            />
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-4 h-4 absolute right-1 top-1/2 transform -translate-y-1/2 text-gray-600"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M21 21l-4.35-4.35m0 0A7.5 7.5 0 1116.65 6.65a7.5 7.5 0 010 10.6z"
              />
            </svg>
          </div>

          <span className="border-l h-4 border-gray-400"></span>

          {/* ✅ Conditional route */}
          <Link to={isLoggedIn ? "/profile" : "/login"}>
            <button className="flex items-center cursor-pointer hover:scale-115 gap-1 text-sm text-gray-800">
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
        </div>
      </div>
    </header>
  );
};

export default Header;
