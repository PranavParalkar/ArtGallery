// import React, { useEffect, useState } from "react";
// import axios from "axios";

// const Admin = () => {
//   const [paintings, setPaintings] = useState([]);
//   const [loading, setLoading] = useState(true);
//   const [msg, setMsg] = useState("");

//   const fetchUnverifiedPaintings = async () => {
//     try {
//       setLoading(true);
//       const res = await axios.get(
//         "http://localhost:8085/admin/paintings/unverified"
//       );
//       setPaintings(res.data);
//       setLoading(false);
//     } catch (err) {
//       console.error("Error fetching unverified paintings", err);
//       setMsg("Failed to load paintings.");
//     }
//   };

//   const approvePainting = async (id) => {
//     try {
//       const res = await axios.post(
//         `http://localhost:8085/admin/paintings/approve/${id}`
//       );
//       setMsg(res.data);
//       fetchUnverifiedPaintings(); // refresh list
//     } catch (err) {
//       console.error("Approval failed", err);
//       setMsg("Approval failed.");
//     }
//   };

//   const rejectPainting = async (id) => {
//     try {
//       const res = await axios.post(
//         `http://localhost:8085/admin/paintings/reject/${id}`
//       );
//       setMsg(res.data);
//       fetchUnverifiedPaintings(); // refresh list
//     } catch (err) {
//       console.error("Rejection failed", err);
//       setMsg("Rejection failed.");
//     }
//   };

//   useEffect(() => {
//     fetchUnverifiedPaintings();
//   }, []);

//   if (loading) return <div className="text-center p-4">Loading...</div>;

//   return (
//     <div className="p-4">
//       <h2 className="text-2xl font-bold mb-4">Unverified Paintings</h2>
//       {msg && <div className="mb-4 text-green-600">{msg}</div>}

//       {paintings.length === 0 ? (
//         <p>No unverified paintings available.</p>
//       ) : (
//         <div className="overflow-x-auto">
//           <table className="min-w-full bg-white border">
//             <thead>
//               <tr className="bg-gray-100">
//                 <th className="py-2 px-4 border">Image</th>
//                 <th className="py-2 px-4 border">Title</th>
//                 <th className="py-2 px-4 border">Description</th>
//                 <th className="py-2 px-4 border">Length</th>
//                 <th className="py-2 px-4 border">Breadth</th>
//                 <th className="py-2 px-4 border">Price</th>
//                 <th className="py-2 px-4 border">Auction</th>
//                 <th className="py-2 px-4 border">Actions</th>
//               </tr>
//             </thead>
//             <tbody>
//               {paintings.map((p) => (
//                 <tr key={p.id} className="border-t">
//                   <td className="py-2 px-4 border">
//                     <img
//                       src={p.imageUrl}
//                       alt={p.title}
//                       className="h-20 w-auto object-cover"
//                     />
//                   </td>
//                   <td className="py-2 px-4 border">{p.title}</td>
//                   <td className="py-2 px-4 border">{p.description}</td>
//                   <td className="py-2 px-4 border">{p.length}</td>
//                   <td className="py-2 px-4 border">{p.breadth}</td>
//                   <td className="py-2 px-4 border">₹{p.startingPrice}</td>
//                   <td className="py-2 px-4 border">
//                     {p.forAuction ? "Yes" : "No"}
//                   </td>
//                   <td className="py-2 px-4 border space-x-2">
//                     <button
//                       onClick={() => approvePainting(p.id)}
//                       className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
//                     >
//                       Approve
//                     </button>
//                     <button
//                       onClick={() => rejectPainting(p.id)}
//                       className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
//                     >
//                       Reject
//                     </button>
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         </div>
//       )}
//     </div>
//   );
// };

// export default Admin;

import React, { useEffect, useState } from "react";
import axios from "axios";
import { motion } from "framer-motion";

const Admin = () => {
  const [paintings, setPaintings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");

  const fetchUnverifiedPaintings = async () => {
    try {
      setLoading(true);
      const res = await axios.get(
        "http://localhost:8085/admin/paintings/unverified"
      );
      setPaintings(res.data);
      setLoading(false);
    } catch (err) {
      console.error("Error fetching unverified paintings", err);
      setMsg("Failed to load paintings.");
      setLoading(false);
    }
  };

  const approvePainting = async (id) => {
    try {
      const res = await axios.post(
        `http://localhost:8085/admin/paintings/approve/${id}`
      );
      setMsg(res.data);
      fetchUnverifiedPaintings();
    } catch (err) {
      console.error("Approval failed", err);
      setMsg("Approval failed.");
    }
  };

  const rejectPainting = async (id) => {
    try {
      const res = await axios.post(
        `http://localhost:8085/admin/paintings/reject/${id}`
      );
      setMsg(res.data);
      fetchUnverifiedPaintings();
    } catch (err) {
      console.error("Rejection failed", err);
      setMsg("Rejection failed.");
    }
  };

  useEffect(() => {
    fetchUnverifiedPaintings();
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
        Paintings for Auctions/Store
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

      {paintings.length === 0 ? (
        <div className="text-center text-gray-500 mt-10 text-lg">
          No unverified paintings available.
        </div>
      ) : (
        <motion.div
          layout
          className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6"
        >
          {paintings.map((p) => (
            <motion.div
              layout
              key={p.id}
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.3 }}
              className="bg-white shadow-xl rounded-2xl overflow-hidden hover:shadow-2xl transition-shadow"
            >
              <img
                src={`http://localhost:8085${p.imageUrl}`}
                alt={p.title}
                className="h-60 w-full object-cover"
              />
              <div className="p-4">
                <h3 className="text-xl font-semibold text-gray-800">
                  {p.title}
                </h3>
                <p className="text-gray-600 text-sm mt-1">{p.description}</p>

                <div className="mt-2 text-sm text-gray-500">
                  <p>
                    <strong>Size:</strong> {p.length} x {p.breadth}
                  </p>
                  <p>
                    <strong>Price:</strong> ₹{p.startingPrice}
                  </p>
                  <p>
                    <strong>Auction:</strong>{" "}
                    {p.forAuction ? "For Auction" : "For Store"}
                  </p>
                </div>

                <div className="flex justify-between mt-4 space-x-2">
                  <button
                    onClick={() => approvePainting(p.id)}
                    className="w-full bg-green-500 hover:bg-green-600 text-white text-sm py-2 rounded-xl transition-colors"
                  >
                    Approve
                  </button>
                  <button
                    onClick={() => rejectPainting(p.id)}
                    className="w-full bg-red-500 hover:bg-red-600 text-white text-sm py-2 rounded-xl transition-colors"
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
  );
};

export default Admin;
