import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"; // ✅ 1
import { motion } from "framer-motion";
import { FaShoppingCart, FaUserTie } from "react-icons/fa";
const Login = () => {
  const navigate = useNavigate(); // ✅ 2
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
    securityQuestion: "",
    securityAnswer: "",
    phoneNumber: "",
  });

  const toggleForm = () => {
    setIsLogin((prev) => !prev);
    setFormData({
      email: "",
      username: "",
      password: "",
      confirmPassword: "",
      securityQuestion: "",
      securityAnswer: "",
      phoneNumber: "",
    });
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isLogin && formData.password !== formData.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    const url = isLogin
      ? "http://localhost:8085/auth/login"
      : "http://localhost:8085/auth/register";

    const payload = isLogin
      ? {
          email: formData.email,
          password: formData.password,
        }
      : {
          email: formData.email,
          password: formData.password,
          confirmPassword: formData.confirmPassword,
          securityQuestion: formData.securityQuestion,
          securityAnswer: formData.securityAnswer,
        };

    try {
      const res = await axios.post(url, payload);
      alert(res.data.message || res.data || "Success");
      navigate("/dash"); // ✅ 3
    } catch (err) {
      alert(
        err.response?.data?.message ||
          err.response?.data ||
          "Something went wrong"
      );
    }
  };

  return (
    <div className="h-screen w-screen bg-gradient-to-tr from-[#f9f9f8] via-[#f9f4ed] to-[#f5ebde] flex items-center justify-center px-4">
      <motion.div
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ duration: 0.6 }}
        className="w-[500px] bg-white/40 backdrop-blur-xl shadow-2xl rounded-3xl p-8"
      >
        {/* Toggle */}
        <div className="flex justify-center gap-4 mb-8">
          <button
            onClick={() => setIsLogin(true)}
            className={`px-6 py-2 rounded-full font-semibold ${
              isLogin ? "bg-purple-500 text-white" : "bg-white/60 text-gray-800"
            }`}
          >
            Login
          </button>
          <button
            onClick={() => setIsLogin(false)}
            className={`px-6 py-2 rounded-full font-semibold ${
              !isLogin
                ? "bg-purple-500 text-white"
                : "bg-white/60 text-gray-800"
            }`}
          >
            Sign Up
          </button>
        </div>

        <h2 className="text-3xl font-bold text-center font-serif text-gray-800 mb-6">
          {isLogin ? "Welcome Back" : "Create Account"}
        </h2>

        <form onSubmit={handleSubmit} className="space-y-4">

          <input
            type="email"
            name="email"
            placeholder="Email"
            required
            onChange={handleChange}
            value={formData.email}
            className="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-purple-400 bg-white text-gray-900 shadow-inner"
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            required
            onChange={handleChange}
            value={formData.password}
            className="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-purple-400 bg-white text-gray-900 shadow-inner"
          />

          {!isLogin && (
            <>
              <input
                type="password"
                name="confirmPassword"
                placeholder="Confirm Password"
                required
                onChange={handleChange}
                value={formData.confirmPassword}
                className="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-purple-400 bg-white text-gray-900 shadow-inner"
              />
              <input
                type="text"
                name="securityQuestion"
                placeholder="Security Question"
                required
                onChange={handleChange}
                value={formData.securityQuestion}
                className="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-purple-400 bg-white text-gray-900 shadow-inner"
              />
              <input
                type="text"
                name="securityAnswer"
                placeholder="Security Answer"
                required
                onChange={handleChange}
                value={formData.securityAnswer}
                className="w-full px-4 py-3 border rounded-xl focus:ring-2 focus:ring-purple-400 bg-white text-gray-900 shadow-inner"
              />
            </>
          )}

          {/* Submit */}
          <button
            type="submit"
            className={`w-full py-3 cursor-pointer rounded-xl bg-gradient-to-r ${
              isLogin
                ? "from-purple-500 to-indigo-500 hover:from-indigo-600 hover:to-purple-600"
                : "from-green-500 to-emerald-500 hover:from-emerald-600 hover:to-green-600"
            } text-white font-bold text-lg transition-all duration-300 shadow-lg hover:shadow-xl`}
          >
            {isLogin ? "Login" : "Sign Up"}
          </button>
        </form>

        {/* Toggle Link */}
        <p className="mt-4 text-center text-sm">
          <button
            onClick={toggleForm}
            className="text-blue-600 hover:underline font-medium"
          >
            {isLogin
              ? "Need an account? Sign Up"
              : "Already have an account? Login"}
          </button>
        </p>
      </motion.div>

      {/* Role Popup
      {showRolePopup && (
        <motion.div
          className="fixed inset-0 bg-black/30 backdrop-blur-sm flex justify-center items-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.3 }}
        >
          <motion.div
            className="bg-[#fff7f0] p-8 rounded-xl shadow-2xl text-center font-serif max-w-sm w-full"
            initial={{ scale: 0.8 }}
            animate={{ scale: 1 }}
            transition={{ type: "spring", stiffness: 100 }}
          >
            <h2 className="text-xl font-bold mb-4 text-[#5c3a1e]">
              Select Account Type
            </h2>
            <div className="flex flex-col gap-4">
              <button
                onClick={() => {
                  setFormData((prev) => ({ ...prev, type: "buyer" }));
                  setShowRolePopup(false);
                }}
                className="flex justify-center items-center gap-2 bg-[#d5baa4] hover:bg-[#caa78b] text-white py-2 px-6 rounded-lg"
              >
                <FaShoppingCart /> I’m a Buyer
              </button>
              <button
                onClick={() => {
                  setFormData((prev) => ({ ...prev, type: "seller" }));
                  setShowRolePopup(false);
                }}
                className="flex justify-center items-center gap-2 bg-[#b08c6a] hover:bg-[#9f7d5e] text-white py-2 px-6 rounded-lg"
              >
                <FaUserTie /> I’m a Seller
              </button>
            </div>
          </motion.div>
        </motion.div>
      )} */}
    </div>
  );
};

export default Login;
