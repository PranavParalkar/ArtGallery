import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";
import {
  FaSignOutAlt,
  FaUserCircle,
  FaEnvelope,
  FaPhoneAlt,
  FaMapMarkerAlt,
} from "react-icons/fa";

const ProfilePage = () => {
  const [profile, setProfile] = useState(null);
  const token = localStorage.getItem("token");

  const axiosInstance = useMemo(() => {
    const instance = axios.create({
      baseURL: "http://localhost:8085",
    });
    instance.interceptors.request.use((config) => {
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });
    return instance;
  }, [token]);

  useEffect(() => {
    if (token) {
      axiosInstance
        .get("/user/profile")
        .then((res) => {
          console.dir(res.data, { depth: null });
          setProfile(res.data);
        })
        .catch((err) => {
          console.error("Failed to load profile:", err);
          setProfile(null);
        });
    }
  }, [axiosInstance, token]);

  if (!token) {
    return (
      <div className="p-10 text-center text-gray-500">
        Please login to view your profile.
      </div>
    );
  }

  if (!profile) {
    return (
      <div className="p-10 text-center text-gray-500">Loading profile...</div>
    );
  }

  return (
    <div className="p-6 sm:p-10 max-w-4xl mx-auto text-[#5a3c28]">
      <div className="mb-6 flex items-center gap-4">
        <FaUserCircle className="text-5xl" />
        <h1 className="text-3xl font-bold">My Profile</h1>
      </div>

      <div className="grid sm:grid-cols-2 gap-6 bg-white shadow-md rounded-2xl p-6">
        <div>
          <p className="mb-2">
            <FaUserCircle className="inline-block mr-2" /> <b>Name:</b>{" "}
            {profile.name}
          </p>
          <p className="mb-2">
            <FaEnvelope className="inline-block mr-2" /> <b>Email:</b>{" "}
            {profile.email}
          </p>
          <p className="mb-2">
            <FaPhoneAlt className="inline-block mr-2" /> <b>Phone:</b>{" "}
            {profile.phoneNumber}
          </p>
        </div>

        <div>
          <p className="mb-2">
            <FaMapMarkerAlt className="inline-block mr-2" /> <b>Address:</b>
          </p>
          <ul className="list-disc list-inside text-gray-700 ml-4">
            {profile.address?.building && <li>{profile.address.building}</li>}
            {profile.address?.street && <li>{profile.address.street}</li>}
            {profile.address?.landmark && <li>{profile.address.landmark}</li>}
            {profile.address?.city && <li>{profile.address.city}</li>}
            {profile.address?.region && <li>{profile.address.region}</li>}
            {profile.address?.country && <li>{profile.address.country}</li>}
            {profile.address?.pincode && (
              <li>Pincode: {profile.address.pincode}</li>
            )}
          </ul>
        </div>
      </div>

      {/* Uploaded Paintings */}
      <div className="mt-10">
        <h2 className="text-2xl font-bold mb-4">My Uploaded Paintings</h2>
        {profile.paintingsSold?.length === 0 ? (
          <p className="text-gray-500">
            You haven't uploaded any paintings yet.
          </p>
        ) : (
          <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
            {profile.paintingsSold.map((painting) => (
              <div
                key={painting.paintingId}
                className="border rounded-xl shadow p-3 bg-white"
              >
                <img
                  src={`http://localhost:8085${painting.imageUrl}`}
                  alt={painting.title}
                  className="w-full h-40 object-cover rounded-lg mb-2"
                />
                <h3 className="text-lg font-semibold">{painting.title}</h3>
                <p className="text-sm text-gray-600">{painting.description}</p>
              </div>
            ))}
          </div>
        )}
      </div>

      <button
        onClick={() => {
          localStorage.clear();
          window.location.reload();
        }}
        className="mt-10 w-full sm:w-auto px-6 py-2 bg-red-500 hover:bg-red-600 text-white rounded-full shadow flex items-center gap-2"
      >
        <FaSignOutAlt /> Logout
      </button>
    </div>
  );
};

export default ProfilePage;
