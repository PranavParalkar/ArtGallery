import { motion, AnimatePresence } from "framer-motion";
import React, { useEffect, useState, useMemo } from "react";
import axiosInstance from '../axiosInstance';
import { Navigate } from "react-router-dom";
import {
  FaSignOutAlt,
  FaUserCircle,
  FaEnvelope,
  FaPhoneAlt,
  FaMapMarkerAlt,
} from "react-icons/fa";

const ProfilePage = () => {
  const [profile, setProfile] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [form, setForm] = useState({});
  const [isVisible, setIsVisible] = useState(false);
  const [activeTab, setActiveTab] = useState("profile");
  const token = localStorage.getItem("token");

  useEffect(() => {
    axiosInstance.get(
      "/user/profile"
    )
      .then((res) => {
        console.dir(res.data, { depth: null });
        setProfile(res.data);
      })
      .catch((err) => {
        console.error("Failed to load profile:", err);
        setProfile(null);
      });
  }, [axiosInstance, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith("address.")) {
      const key = name.split(".")[1];
      setForm((prev) => ({
        ...prev,
        address: {
          ...prev.address,
          [key]: value,
        },
      }));
    } else {
      setForm((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleUpdate = async () => {
    try {
      await axiosInstance.put("/user/profile-update", form);
      setEditMode(false);
      setProfile((prev) => ({ ...prev, ...form }));
      setIsVisible(true);
      setTimeout(() => setIsVisible(false), 3000);
    } catch (err) {
      console.error("Update failed", err);
      alert("Failed to update profile.");
    }
  };

  const handleClose = () => {
    setIsVisible(false);
  };

  if (!token)
    return (
      <div className="p-10 text-center">Please login to view your profile.</div>
    );
  if (!profile)
    return <div className="p-10 text-center">Loading profile...</div>;

  const tabButton = (label) => (
    <button
      onClick={() => setActiveTab(label)}
      className={`px-6 py-2 rounded-full font-semibold transition-all ${
        activeTab === label
          ? "bg-[#3e2e1e] text-white"
          : "bg-gray-200 text-gray-700 hover:bg-gray-300"
      }`}
    >
      {label === "profile" ? "Profile" : "My Paintings"}
    </button>
  );
  return (
    <div className="font-serif p-6 sm:p-10 text-[#3e2e1e]">
      <AnimatePresence>
        {isVisible && (
          <motion.div
            onClick={handleClose}
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            transition={{ duration: 0.4 }}
            className="fixed inset-0 text-3xl font-serif flex items-center justify-center z-50 backdrop-blur-sm cursor-pointer"
          >
            ✅ You have Updated Profile successfully
          </motion.div>
        )}
      </AnimatePresence>

      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.6 }}
        className="flex flex-row justify-evenly gap-8 items-start"
      >
        {/* Profile Section */}
        <div className="flex flex-col items-center justify-center w-full lg:pl-20">
          <img
            className="rounded-full md:h-2/3 md:w-2/3 mt-10 w-40 h-40 hover:scale-110 transition-all duration-500 shadow-xl"
            src="https://images.unsplash.com/photo-1740252117070-7aa2955b25f8?w=600&auto=format&fit=crop&q=60"
            alt="Profile"
          />
          <h2 className="text-5xl items-center justify-center ml-16 font-serif font-bold mt-16 ">
            Hello {profile.name} !
          </h2>
        </div>

        <motion.div
          initial={{ x: -50, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ duration: 0.5 }}
          className=" rounded-2xl w-full p-6"
        >
          <div className=" p-6 sm:p-10 text-[#3e2e1e]">
            {/* Tab Buttons */}
            <div className="flex justify-center mb-8 gap-4">
              {tabButton("profile")}
              {tabButton("paintings")}
            </div>

            {/* Animate section switch */}
            <AnimatePresence mode="wait">
              {activeTab === "profile" ? (
                <motion.div
                  key="profile"
                  initial={{ opacity: 0, x: -50 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: 50 }}
                  transition={{ duration: 0.5 }}
                  className="bg-white rounded-2xl shadow-lg p-6 max-w-4xl mx-auto"
                >
                  <div className="flex items-center mb-6 gap-4">
                    <FaUserCircle className="text-4xl" />
                    <h1 className="text-3xl font-bold">My Profile</h1>
                    <button
                      onClick={() => setEditMode((prev) => !prev)}
                      className="ml-auto px-4 py-1 bg-[#94520f] text-white rounded-full"
                    >
                      {editMode ? "Cancel" : "Edit"}
                    </button>
                  </div>

                  {/* Name */}
                  <label className="block mb-3">
                    <b>
                      <FaUserCircle className="inline-block mr-2" /> Name:
                    </b>{" "}
                    {editMode ? (
                      <input
                        type="text"
                        name="name"
                        value={form.name}
                        onChange={handleInputChange}
                        className="border rounded px-3 py-1 w-full mt-1"
                      />
                    ) : (
                      <span className="ml-2">{profile.name}</span>
                    )}
                  </label>

                  {/* Email */}
                  <p className="mb-3">
                    <FaEnvelope className="inline-block mr-2" />
                    <b>Email:</b> {profile.email}
                  </p>

                  {/* Phone */}
                  <label className="block mb-3">
                    <b>
                      <FaPhoneAlt className="inline-block mr-2" /> Phone:
                    </b>{" "}
                    {editMode ? (
                      <input
                        type="text"
                        name="phoneNumber"
                        value={form.phoneNumber}
                        onChange={handleInputChange}
                        className="border rounded px-3 py-1 w-full mt-1"
                      />
                    ) : (
                      <span className="ml-2">{profile.phoneNumber}</span>
                    )}
                  </label>

                  {/* Address */}
                  <div className="mt-4">
                    <p className="font-bold flex items-center mb-2">
                      <FaMapMarkerAlt className="inline-block mr-2" /> Address:
                    </p>
                    <div className="text-sm text-gray-700">
                      {editMode ? (
                        <div className="flex flex-wrap gap-2">
                          {[
                            "building",
                            "landmark",
                            "street",
                            "city",
                            "region",
                            "country",
                            "pincode",
                          ].map((field) => (
                            <div key={field} className="flex flex-col">
                              <input
                                type="text"
                                name={`address.${field}`}
                                placeholder={field}
                                value={form.address?.[field] || ""}
                                onChange={handleInputChange}
                                className="border rounded px-2 py-1 w-[130px]"
                              />
                            </div>
                          ))}
                        </div>
                      ) : (
                        <span>
                          {[
                            profile.address?.building,
                            profile.address?.landmark,
                            profile.address?.street,
                            profile.address?.city,
                            profile.address?.region,
                            profile.address?.country,
                          ]
                            .filter(Boolean)
                            .join(", ")}
                          {profile.address?.pincode &&
                            `. Pincode - ${profile.address.pincode}`}
                        </span>
                      )}
                    </div>
                  </div>

                  {/* Save Button */}
                  {editMode && (
                    <motion.button
                      whileTap={{ scale: 0.95 }}
                      onClick={handleUpdate}
                      className="mt-6 px-6 py-2 bg-green-500 hover:bg-green-600 text-white rounded-full"
                    >
                      Save Changes
                    </motion.button>
                  )}

                  {/* Logout */}
                  <button
                    onClick={() => {
                      localStorage.removeItem("token");
                      window.location.href = "/";
                    }}
                    className="mt-6 w-full px-6 py-2 bg-red-500 hover:bg-red-600 text-white rounded-full shadow flex items-center justify-center gap-2"
                  >
                    <FaSignOutAlt /> Logout
                  </button>
                </motion.div>
              ) : (
                <motion.div
                  key="paintings"
                  initial={{ opacity: 0, x: 50 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -50 }}
                  transition={{ duration: 0.5 }}
                  className="bg-white rounded-2xl shadow-lg p-6 max-w-6xl mx-auto"
                >
                  <h2 className="text-2xl font-bold mb-4">
                    My Uploaded Paintings
                  </h2>
                  {profile.paintingsSold?.length === 0 ? (
                    <p className="text-gray-500">
                      You haven't uploaded any paintings yet.
                    </p>
                  ) : (
                    <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
                      {profile.paintingsSold.map((painting) => (
                        <motion.div
                          key={painting.paintingId}
                          whileHover={{ scale: 1.03 }}
                          className="border rounded-xl shadow p-3 bg-white"
                        >
                          <img
                            src={`http://localhost:8085${painting.imageUrl}`}
                            alt={painting.title}
                            className="w-full h-40 object-cover rounded-lg mb-2"
                          />
                          <h3 className="text-lg font-semibold">
                            {painting.title}
                          </h3>
                          <p className="text-sm text-gray-600">
                            {painting.description}
                          </p>
                        </motion.div>
                      ))}
                    </div>
                  )}
                </motion.div>
              )}
            </AnimatePresence>
          </div>
        </motion.div>
      </motion.div>
    </div>
  );
};

export default ProfilePage;
