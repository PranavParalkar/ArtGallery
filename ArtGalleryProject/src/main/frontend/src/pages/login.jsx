import React, { useState } from "react";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";

const Login = () => {
  const [mode, setMode] = useState("login"); // 'login' or 'signup'
  const [showSecurityCheck, setShowSecurityCheck] = useState(false);
  const [nicknameCheck, setNicknameCheck] = useState("");

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    nickname: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(`${mode.toUpperCase()} Data:`, formData);
  };

  const handleVerify = () => {
    // Simulated nickname check — replace with real validation if needed
    if (
      nicknameCheck.trim().toLowerCase() ===
      formData.nickname.trim().toLowerCase()
    ) {
      alert("✅ Identity verified! You can now reset your password.");
    } else {
      alert("❌ Incorrect answer. Please try again.");
    }
  };

  return (
    <div className="h-[870px] w-screen  bg-gradient-to-tr from-[#f9f9f8] via-[#f9f4ed] to-[#f5ebde] flex items-center justify-center px-4">
      <motion.div
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className="w-[500px] bg-white/40 backdrop-blur-xl shadow-black shadow-2xl rounded-3xl p-8"
      >
        {/* Toggle Buttons */}
        <div className="flex justify-center gap-4 mb-8">
          <button
            onClick={() => {
              setMode("login");
              setShowSecurityCheck(false);
            }}
            className={`px-6 py-2 rounded-full font-semibold cursor-pointer transition duration-300 ${
              mode === "login"
                ? "bg-purple-500 text-white shadow-md"
                : "bg-white/60 text-gray-800"
            }`}
          >
            Login
          </button>
          <button
            onClick={() => {
              setMode("signup");
              setShowSecurityCheck(false);
            }}
            className={`px-6 py-2 rounded-full font-semibold cursor-pointer transition duration-300 ${
              mode === "signup"
                ? "bg-purple-500 text-white shadow-md"
                : "bg-white/60 text-gray-800"
            }`}
          >
            Sign Up
          </button>
        </div>

        {/* Header */}
        <motion.h2
          initial={{ y: -10, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: 0.1, duration: 0.4 }}
          className="text-3xl font-bold text-center font-serif text-gray-800 mb-6"
        >
          {mode === "login" ? "Welcome Back" : "Create Account"}
        </motion.h2>

        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Email */}
          <motion.div
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
          >
            <label
              htmlFor="email"
              className="block text-sm font-semibold font-serif text-gray-700 mb-1"
            >
              Email Address
            </label>
            <input
              type="email"
              name="email"
              id="email"
              onChange={handleChange}
              value={formData.email}
              required
              className="w-full px-4 py-3 border border-transparent rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-400 transition bg-white text-gray-900 shadow-inner"
              placeholder="you@example.com"
            />
          </motion.div>

          {/* Password */}
          <motion.div
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
          >
            <label
              htmlFor="password"
              className="block text-sm font-serif font-semibold text-gray-700 mb-1"
            >
              Password
            </label>
            <input
              type="password"
              name="password"
              id="password"
              onChange={handleChange}
              value={formData.password}
              required
              className="w-full px-4 py-3 border border-transparent rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-400 transition bg-white text-gray-900 shadow-inner"
              placeholder="••••••••"
            />
          </motion.div>

          {/* Confirm Password (Sign Up only) */}
          {mode === "signup" && (
            <motion.div
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.3 }}
            >
              <label
                htmlFor="confirmPassword"
                className="block text-sm font-serif font-semibold text-gray-700 mb-1"
              >
                Confirm Password
              </label>
              <input
                type="password"
                name="confirmPassword"
                id="confirmPassword"
                onChange={handleChange}
                value={formData.confirmPassword}
                required
                className="w-full px-4 py-3 border border-transparent rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-400 transition bg-white text-gray-900 shadow-inner"
                placeholder="••••••••"
              />
            </motion.div>
          )}

          {/* Nickname (Sign Up only) */}
          {mode === "signup" && (
            <motion.div
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.3 }}
            >
              <label
                htmlFor="nickname"
                className="block text-sm font-serif font-semibold text-gray-700 mb-1"
              >
                Set a Nickname (for password recovery)
              </label>
              <input
                type="text"
                name="nickname"
                id="nickname"
                onChange={handleChange}
                value={formData.nickname}
                required
                className="w-full px-4 py-3 border border-transparent rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-400 transition bg-white text-gray-900 shadow-inner"
                placeholder="e.g. Rocky, Mini..."
              />
            </motion.div>
          )}

          {/* Forgot Password (Login only) */}
          {mode === "login" && !showSecurityCheck && (
            <div
              className="text-right text-xs text-purple-600 hover:underline cursor-pointer"
              onClick={() => setShowSecurityCheck(true)}
            >
              Forgot Password?
            </div>
          )}

          {/* Nickname check (forgot password flow) */}
          {mode === "login" && showSecurityCheck && (
            <motion.div
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
            >
              <label
                htmlFor="nicknameCheck"
                className="block text-sm font-serif font-semibold text-gray-700 mb-1"
              >
                What is your nickname?
              </label>
              <input
                type="text"
                name="nicknameCheck"
                id="nicknameCheck"
                value={nicknameCheck}
                onChange={(e) => setNicknameCheck(e.target.value)}
                className="w-full px-4 py-3 border border-transparent rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-400 transition bg-white text-gray-900 shadow-inner"
                placeholder="Enter your nickname"
              />
              <button
                type="button"
                className="mt-4 w-full py-2 rounded-xl bg-purple-500 text-white font-semibold hover:bg-purple-600 transition"
                onClick={handleVerify}
              >
                Verify
              </button>
            </motion.div>
          )}

          {/* Submit Button */}
          {!showSecurityCheck && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.35 }}
            >
              {mode === "login" ? (
                <Link to="/onboarding">
                  <button
                    type="button"
                    className="w-full py-3 cursor-pointer rounded-xl bg-gradient-to-r from-purple-500 to-indigo-500 text-white font-bold text-lg hover:from-indigo-600 hover:to-purple-600 transition-all duration-300 shadow-lg hover:shadow-xl"
                  >
                    Log In
                  </button>
                </Link>
              ) : (
                <Link to="/onboarding">
                  <button
                    type="submit"
                    className="w-full py-3 cursor-pointer rounded-xl bg-gradient-to-r from-green-500 to-emerald-500 text-white font-bold text-lg hover:from-emerald-600 hover:to-green-600 transition-all duration-300 shadow-lg hover:shadow-xl"
                  >
                    Create Account
                  </button>
                </Link>
              )}
            </motion.div>
          )}
        </form>
      </motion.div>
    </div>
  );
};

export default Login;
