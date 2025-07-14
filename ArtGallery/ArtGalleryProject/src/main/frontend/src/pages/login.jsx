import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"; // ✅ 1
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
          username: formData.username,
          password: formData.password,
          confirmPassword: formData.confirmPassword,
          securityQuestion: formData.securityQuestion,
          securityAnswer: formData.securityAnswer,
          phoneNumber: formData.phoneNumber,
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
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md bg-white shadow-md rounded-xl p-8">
        <h2 className="text-2xl font-bold text-center mb-6">
          {isLogin ? "Login" : "Sign Up"}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="email"
            name="email"
            placeholder="Email"
            required
            onChange={handleChange}
            value={formData.email}
            className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {!isLogin && (
            <input
              type="text"
              name="username"
              placeholder="Username"
              required
              onChange={handleChange}
              value={formData.username}
              className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          )}
          <input
            type="password"
            name="password"
            placeholder="Password"
            required
            onChange={handleChange}
            value={formData.password}
            className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
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
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="text"
                name="securityQuestion"
                placeholder="Security Question"
                required
                onChange={handleChange}
                value={formData.securityQuestion}
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="text"
                name="securityAnswer"
                placeholder="Security Answer"
                required
                onChange={handleChange}
                value={formData.securityAnswer}
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="text"
                name="phoneNumber"
                placeholder="Phone Number"
                required
                onChange={handleChange}
                value={formData.phoneNumber}
                className="..."
              />
            </>
          )}
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition"
          >
            {isLogin ? "Login" : "Sign Up"}
          </button>
        </form>
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
      </div>
    </div>
  );
};

export default Login;
