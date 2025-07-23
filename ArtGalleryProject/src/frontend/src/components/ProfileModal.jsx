import React, { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axiosInstance from '../axiosInstance';
import { FaSignOutAlt, FaUserCircle, FaEnvelope, FaPhoneAlt, FaMapMarkerAlt } from "react-icons/fa";

const ProfileModal = ({ isOpen, onClose }) => {
  const [profile, setProfile] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (isOpen && token) {
      axiosInstance
        .get(`/user/profile`)
        .then((res) => {
          console.log("Profile data received:", res.data);
          setProfile(res.data);
        })
        .catch((err) => {
          console.error("Profile fetch error:", err);
          setProfile(null);
        });
    } else {
      setProfile(null);
    }
  }, [isOpen, token]);

  if (!isOpen) return null;

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0  flex items-center justify-center z-50 backdrop-blur-sm"
        onClick={onClose}
      >
        <motion.div
          initial={{ y: -30, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          exit={{ y: -30, opacity: 0 }}
          transition={{ duration: 0.3, ease: "easeOut" }}
          className="bg-white rounded-3xl shadow-2xl p-6 sm:p-8 w-[90%] max-w-md relative"
          onClick={(e) => e.stopPropagation()}
        >
          {/* Close Button */}
          <button
            className="absolute top-4 right-5 text-gray-400 hover:text-gray-800 text-2xl"
            onClick={onClose}
          >
            &times;
          </button>

          {/* Header */}
          <div className="flex items-center gap-3 mb-6 text-[#5a3c28]">
            <FaUserCircle className="text-4xl" />
            <h2 className="text-2xl font-bold">User Profile</h2>
          </div>

          {/* Content */}
          {token ? (
            profile ? (
              <div className="space-y-4 text-sm sm:text-base">
                <div className="flex items-center gap-2">
                  <FaUserCircle className="text-[#5a3c28]" />
                  <span><b>Name:</b> {profile.name}</span>
                </div>
                <div className="flex items-center gap-2">
                  <FaEnvelope className="text-[#5a3c28]" />
                  <span><b>Email:</b> {profile.email}</span>
                </div>
                <div className="flex items-center gap-2">
                  <FaPhoneAlt className="text-[#5a3c28]" />
                  <span><b>Phone:</b> {profile.phoneNumber}</span>
                </div>
                <div className="flex gap-2 items-start">
                  <FaMapMarkerAlt className="text-[#5a3c28] mt-1" />
                  <div>
                    <b>Address:</b>
                    <ul className="list-disc list-inside text-gray-700 ml-2">
                      {profile.address?.building && <li>{profile.address.building}</li>}
                      {profile.address?.street && <li>{profile.address.street}</li>}
                      {profile.address?.city && <li>{profile.address.city}</li>}
                      {profile.address?.region && <li>{profile.address.region}</li>}
                      {profile.address?.country && <li>{profile.address.country}</li>}
                      {profile.address?.pincode && <li>Pincode: {profile.address.pincode}</li>}
                    </ul>
                  </div>
                </div>

                {/* Logout Button */}
                <button
                  onClick={() => {
                    localStorage.clear();
                    onClose();
                    window.location.reload();
                  }}
                  className="mt-6 w-full flex items-center justify-center gap-2 px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded-full shadow transition"
                >
                  <FaSignOutAlt /> Logout
                </button>
              </div>
            ) : (
              <div className="text-gray-500 text-center">
                Could not load profile. Please try again.
                {/* Logout Button */}
                <button
                  onClick={() => {
                    localStorage.clear();
                    onClose();
                    window.location.reload();
                  }}
                  className="mt-6 w-full flex items-center justify-center gap-2 px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded-full shadow transition"
                >
                  <FaSignOutAlt /> Logout
                </button>
              </div>
            )
          ) : (
            <div className="text-gray-500 text-center">
              Please log in to view your profile.
            </div>
          )}
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default ProfileModal;
