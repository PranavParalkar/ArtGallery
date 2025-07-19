import React, { useState, useEffect } from "react";
import axios from "axios";

const PaintingManager = () => {
  const [paintings, setPaintings] = useState([]);
  const [newPainting, setNewPainting] = useState({
    name: "",
    description: "",
    price: "",
    sellerUsername: "",
  });

  useEffect(() => {
    fetchPaintings();
  }, []);

  const fetchPaintings = async () => {
    try {
      const res = await axios.get("http://localhost:8085/api/paintings/all");
      const paintingList = res.data?.data || []; // Fallback to empty array
      console.log("🎨 Painting API response:", res.data);
      setPaintings(paintingList);
    } catch (err) {
      console.error("Failed to load paintings", err);
    }
  };

  const handleChange = (e) => {
    setNewPainting({ ...newPainting, [e.target.name]: e.target.value });
  };

  const handleAddPainting = async (e) => {
    e.preventDefault();
    try {
      const paintingPayload = {
        name: newPainting.name,
        description: newPainting.description,
        price: newPainting.price,
        sellerUsername: newPainting.sellerUsername,
      };
      console.log("🚀 Sending payload:", paintingPayload);

      await axios.post(
        "http://localhost:8085/api/paintings/add",
        paintingPayload
      );
      alert("🎨 Painting added!");

      setNewPainting({
        name: "",
        description: "",
        price: "",
        sellerUsername: "",
      });
      fetchPaintings();
    } catch (err) {
      console.error(err.response?.data || err.message);
      alert("❌ Failed to add painting");
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8085/api/paintings/delete/${id}`);
      alert("🗑️ Deleted");
      fetchPaintings();
    } catch (err) {
      alert("❌ Failed to delete painting");
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-4">🖼️ Painting Manager</h1>

      {/* Add Form */}
      <form onSubmit={handleAddPainting} className="mb-6 space-y-2">
        <input
          name="name"
          value={newPainting.name}
          onChange={handleChange}
          placeholder="Title"
          required
          className="w-full p-2 border rounded"
        />
        <input
          name="description"
          value={newPainting.description}
          onChange={handleChange}
          placeholder="Description"
          required
          className="w-full p-2 border rounded"
        />
        <input
          name="price"
          value={newPainting.price}
          onChange={handleChange}
          placeholder="Price"
          required
          className="w-full p-2 border rounded"
        />
        <input
          name="sellerUsername"
          value={newPainting.sellerUsername}
          onChange={handleChange}
          placeholder="Seller Username"
          required
          className="w-full p-2 border rounded"
        />
        <button
          type="submit"
          className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
        >
          ➕ Add Painting
        </button>
      </form>

      {/* Paintings List */}
      <div className="grid gap-4">
        {Array.isArray(paintings) && paintings.length > 0 ? (
          paintings.map((p) => (
            <div
              key={p.paintingId || p.id}
              className="border p-4 rounded shadow bg-white flex justify-between"
            >
              <div>
                <h2 className="font-bold text-xl">{p.name}</h2>
                <p>{p.description}</p>
                <p>💰 ₹{p.currentHighestBid ?? p.price ?? "N/A"}</p>
              </div>
              <button
                onClick={() => handleDelete(p.paintingId)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
              >
                ❌ Delete
              </button>
            </div>
          ))
        ) : (
          <p>No paintings found.</p>
        )}
      </div>
    </div>
  );
};

export default PaintingManager;
