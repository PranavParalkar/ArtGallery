import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../axiosInstance";
import { motion, AnimatePresence } from "framer-motion";
import { getUserRole } from "../utils/auth";
import {
  FaImage,
  FaInfoCircle,
  FaRulerCombined,
  FaTags,
  FaUser,
  FaUserCircle,
  FaTag,
} from "react-icons/fa";

const Admin = () => {
  const [paintings, setPaintings] = useState([]);
  const [withdrawalRequests, setWithdrawalRequests] = useState([]);
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");
  const [activeTab, setActiveTab] = useState("paintings");
  const navigate = useNavigate();
  const role = getUserRole();

  useEffect(() => {
    if (role !== "ROLE_ADMIN") {
      if (!sessionStorage.getItem("adminAccessDenied")) {
        alert("Access Denied!");
        sessionStorage.setItem("adminAccessDenied", "true");
      }
      navigate("/", { replace: true });
    } else {
      sessionStorage.removeItem("adminAccessDenied");
    }
  }, [role, navigate]);

  const fetchUnverifiedPaintings = async () => {
    try {
      const res = await axiosInstance.get("/admin/paintings/unverified");
      setPaintings(res.data);
    } catch (err) {
      console.error("Error fetching unverified paintings", err);
    }
  };

  const fetchWithdrawalRequests = async () => {
    try {
      const res = await axiosInstance.get("/admin/withdrawals/pending");
      setWithdrawalRequests(res.data);
    } catch (err) {
      console.error("Error fetching withdrawal requests", err);
    }
  };

  const approvePainting = async (id) => {
    try {
      const res = await axiosInstance.post(`/admin/paintings/approve/${id}`);
      setMsg(res.data);
      fetchUnverifiedPaintings();
    } catch (err) {
      console.error("Approval failed", err);
      setMsg("Approval failed.");
    }
  };

  const rejectPainting = async (id) => {
    try {
      const res = await axiosInstance.post(`/admin/paintings/reject/${id}`);
      setMsg(res.data);
      fetchUnverifiedPaintings();
    } catch (err) {
      console.error("Rejection failed", err);
      setMsg("Rejection failed.");
    }
  };

  const approveWithdrawal = async (id) => {
    try {
      const res = await axiosInstance.post(`/admin/withdrawals/approve/${id}`);
      setMsg(res.data);
      fetchWithdrawalRequests();
    } catch (err) {
      console.error("Withdrawal approval failed", err);
      setMsg("Withdrawal approval failed.");
    }
  };

  const rejectWithdrawal = async (id) => {
    try {
      const res = await axiosInstance.post(`/admin/withdrawals/reject/${id}`);
      setMsg(res.data);
      fetchWithdrawalRequests();
    } catch (err) {
      console.error("Withdrawal rejection failed", err);
      setMsg("Withdrawal rejection failed.");
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      await Promise.all([fetchUnverifiedPaintings(), fetchWithdrawalRequests()]);
      setLoading(false);
    };
    fetchData();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  return (
    <div className="p-6 max-w-7xl mx-auto font-serif">
      <h2 className="text-4xl font-bold text-center text-[#3e2e1e] mb-12 mt-10">
        Admin Dashboard
      </h2>

      {msg && (
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-4 p-3 rounded bg-green-100 text-green-700 shadow"
        >
          {msg}
        </motion.div>
      )}

      {/* Tab Navigation */}
      <div className="flex justify-center mb-8">
        <div className="flex bg-gray-100 rounded-lg p-1">
          <button
            onClick={() => setActiveTab("paintings")}
            className={`px-6 py-2 rounded-md font-semibold transition-colors cursor-pointer ${activeTab === "paintings"
              ? "bg-[#a17b5d] text-white"
              : "text-gray-600 hover:text-gray-800"
              }`}
          >
            Paintings ({paintings.length})
          </button>
          <button
            onClick={() => setActiveTab("withdrawals")}
            className={`px-6 py-2 rounded-md font-semibold transition-colors cursor-pointer ${activeTab === "withdrawals"
              ? "bg-[#a17b5d] text-white"
              : "text-gray-600 hover:text-gray-800"
              }`}
          >
            Withdrawals ({withdrawalRequests.length})
          </button>
        </div>
      </div>

      {/* Paintings Tab */}
      {activeTab === "paintings" && (
        <div>
          <h3 className="text-2xl font-bold text-[#3e2e1e] mb-6 text-center">
            Paintings for Auctions/Store
          </h3>

          {paintings.length === 0 ? (
            <div className="text-center text-gray-500 mt-10 text-lg">
              No unverified paintings available.
            </div>
          ) : (
            <motion.div
              layout
              className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6"
            >
              {paintings.map((painting) => (
                <motion.div
                  key={painting.paintingId}
                  initial={{ opacity: 0, y: 15 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.75 }}
                  className="rounded-2xl bg-[#f0e2d2] h-[500px] transform hover:-translate-y-2 duration-300 overflow-hidden shadow-md hover:shadow-2xl hover:shadow-amber-950 transition flex flex-col"
                >
                  {painting.imageUrl && (
                    <div className="relative overflow-hidden h-1/2 rounded-t-md group group">
                      <img
                        src={`http://localhost:8085${painting.imageUrl}`}
                        alt={painting.title}
                        className="w-full h-80 object-cover cursor-pointer transition-transform duration-300 group-hover:scale-105"
                        onClick={() =>
                          setFullscreenImage(
                            `http://localhost:8085${painting.imageUrl}`
                          )
                        }
                      />

                      {/* Hover message */}
                      <div className="absolute bottom-3 left-1/2 transform -translate-x-1/2 bg-[#6b4c35]/50 text-white text-sm px-3 py-1 rounded-full opacity-0 group-hover:opacity-100 transition cursor-pointer">
                        Click to view image
                      </div>
                    </div>
                  )}
                  <div className="p-6 flex flex-col justify-between flex-grow">
                    <div>
                      <div className="flex items-center gap-2 mb-2">
                        <FaImage className="text-[#5a3c28]" />
                        <h2 className="text-2xl font-bold text-[#5a3c28]">
                          {painting.title}
                        </h2>
                      </div>
                      <div className="flex items-start gap-2 text-md text-[#6b4c35] mb-2">
                        <FaInfoCircle className="mt-1 flex-shrink-0" />
                        <p className="line-clamp-2">
                          {painting.description}
                        </p>
                      </div>
                      <p className="text-md text-[#6b4c35] mb-1 flex gap-2">
                        <FaRulerCombined />{" "}
                        <span className="font-bold">Dimensions</span>{" "}
                        {painting.length}cm x {painting.breadth}cm
                      </p>
                      <p className="text-md text-[#6b4c35] flex gap-2 my-2">
                        <FaTag className="" />
                        <span className="font-bold">Starting Price:</span> ₹
                        {painting.startingPrice}
                      </p>
                      <p className="text-md mt-2 font-bold flex gap-2 text-[#6b4c35]">
                        <FaUserCircle className="mt-1" /> Seller:{" "}
                        <span className="font-medium text-[#6b4c35]">
                          {painting.seller || "Unknown"}
                        </span>
                      </p>
                    </div>
                    <div className="flex justify-between mt-4 space-x-2">
                      <button
                        onClick={() => approvePainting(painting.id)}
                        className="w-full bg-green-500 hover:bg-green-600 text-white text-sm py-2 rounded-xl transition-colors cursor-pointer"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => rejectPainting(painting.id)}
                        className="w-full bg-red-500 hover:bg-red-600 text-white text-sm py-2 rounded-xl transition-colors cursor-pointer"
                      >
                        Reject
                      </button>
                    </div>
                  </div>
                </motion.div>
              ))}
            </motion.div>
          )}
        </div>
      )
      }

      {/* Withdrawals Tab */}
      {
        activeTab === "withdrawals" && (
          <div>
            <h3 className="text-2xl font-bold text-[#3e2e1e] mb-6 text-center">
              Pending Withdrawal Requests
            </h3>

            {withdrawalRequests.length === 0 ? (
              <div className="text-center text-gray-500 mt-10 text-lg">
                No pending withdrawal requests.
              </div>
            ) : (
              <motion.div
                layout
                className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
              >
                {withdrawalRequests.map((request) => (
                  <motion.div
                    layout
                    key={request.id}
                    initial={{ opacity: 0, scale: 0.95 }}
                    animate={{ opacity: 1, scale: 1 }}
                    transition={{ duration: 0.3 }}
                    className="bg-white shadow-xl rounded-2xl overflow-hidden hover:shadow-2xl hover:shadow-orange-900 transition-shadow"
                  >
                    <div className="p-6">
                      <div className="flex items-center justify-between mb-4">
                        <h3 className="text-lg font-semibold text-gray-800">
                          Withdrawal Request #{request.id}
                        </h3>
                        <span className="bg-yellow-100 text-yellow-800 text-xs px-2 py-1 rounded-full">
                          PENDING
                        </span>
                      </div>

                      <div className="space-y-3 text-sm">
                        <div>
                          <strong>Amount:</strong> ₹{request.amount}
                        </div>
                        <div>
                          <strong>User:</strong> {request.userEmail}
                        </div>
                        <div>
                          <strong>Account Holder:</strong> {request.accountHolderName}
                        </div>
                        <div>
                          <strong>Bank Account:</strong> {request.bankAccount}
                        </div>
                        <div>
                          <strong>IFSC Code:</strong> {request.ifscCode}
                        </div>
                        <div>
                          <strong>Request Date:</strong>{" "}
                          {new Date(request.requestDate).toLocaleDateString()}
                        </div>
                      </div>

                      <div className="flex justify-between mt-6 space-x-2">
                        <button
                          onClick={() => approveWithdrawal(request.id)}
                          className="w-full bg-green-500 hover:bg-green-600 text-white text-sm py-2 rounded-xl transition-colors cursor-pointer"
                        >
                          Approve
                        </button>
                        <button
                          onClick={() => rejectWithdrawal(request.id)}
                          className="w-full bg-red-500 hover:bg-red-600 text-white text-sm py-2 rounded-xl transition-colors cursor-pointer"
                        >
                          Reject
                        </button>
                      </div>
                    </div>
                  </motion.div>
                ))}
              </motion.div>
            )}
          </div>
        )
      }
      {/* Fullscreen Image Modal */}
      <AnimatePresence>
        {fullscreenImage && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black/80 flex items-center justify-center z-50"
            onClick={() => setFullscreenImage(null)}
          >
            <motion.div
              initial={{ scale: 0.8 }}
              animate={{ scale: 1 }}
              exit={{ scale: 0.8 }}
              className="relative max-w-4xl w-full"
            >
              <img
                src={fullscreenImage}
                alt="Fullscreen Preview"
                className="w-full max-h-[90vh] object-contain rounded-lg shadow-2xl"
              />
              <button
                onClick={() => setFullscreenImage(null)}
                className="absolute top-3 right-3 text-white bg-black/70 rounded-full px-3 py-1 text-sm hover:bg-black"
              >
                ✕ Close
              </button>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>

    </div >
  );
};

export default Admin;
