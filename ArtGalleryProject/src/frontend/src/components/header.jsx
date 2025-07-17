const Header = () => {
  return (
    <header className="border-b text-sm relative ">
      <div className="flex items-center justify-between px-6 py-5">
        {/* Left side: Logo and Menu */}
        <div className="flex items-center gap-8">
          <h1 className="text-xl font-serif tracking-wide">FUSION ART</h1>
          <nav className="flex gap-6 uppercase tracking-wider text-xs text-gray-700">
            {["Auctions", "Sell", "Departments", "Discover", "Shop"].map(
              (item) => (
                <a key={item} href="#" className="relative group">
                  {item}
                  <span className="absolute left-0 -bottom-1 w-0 h-[2px] bg-gray-800 transition-all duration-300 group-hover:w-full"></span>
                </a>
              )
            )}
          </nav>
        </div>

        {/* Right side: Sign In, Language, Search */}
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
          <button className="flex items-center gap-1 text-sm text-gray-800">
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
        </div>
      </div>
    </header>
  );
};

export default Header;
