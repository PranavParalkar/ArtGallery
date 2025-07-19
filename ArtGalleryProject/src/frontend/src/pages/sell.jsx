import { useState } from "react";
import { motion } from "framer-motion";
import axios from "axios";

const Sell = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    certificate: null,
    painting: null,
    address: "",
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setStep(3); // Proceed to final step
    // Here you can also add API call to submit the form
  };

  return (
    <div className="w-screen min-h-[100%] mt-5 bg-[#fffaf3] px-6 py-12 font-serif">
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="max-w-4xl mx-auto bg-white rounded-2xl shadow-lg p-8"
      >
        <h1 className="text-4xl font-bold text-center text-[#5a3c28] mb-8">
          Sell Your Artwork
        </h1>

        {step === 1 && (
         
                <div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-10 text-[#5a3c28]">
              <motion.div
                initial={{ opacity: 0, x: -20 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.4 }}
                className="bg-[#fffaf3] p-6 rounded-xl border border-[#e0d7cb]"
              >
                <h2 className="text-xl font-semibold mb-2">
                  1. Submit Your Artwork
                </h2>
                <p>
                  Provide basic details and images of the piece you wish to
                  consign.
                </p>
              </motion.div>

              <motion.div
                initial={{ opacity: 0, x: 20 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.4, delay: 0.1 }}
                className="bg-[#fffaf3] p-6 rounded-xl border border-[#e0d7cb]"
              >
                <h2 className="text-xl font-semibold mb-2">
                  2. Review Process
                </h2>
                <p>
                  Our team will evaluate your submission to ensure it meets our
                  standards. You will receive a response within 10 business
                  days.
                </p>
              </motion.div>

              <motion.div
                initial={{ opacity: 0, x: -20 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.4, delay: 0.2 }}
                className="bg-[#fffaf3] p-6 rounded-xl border border-[#e0d7cb]"
              >
                <h2 className="text-xl font-semibold mb-2">
                  3. Receive Expert Guidance
                </h2>
                <p>
                  Our experts will assist you throughout the entire process,
                  from initial submission to the final sale.
                </p>
              </motion.div>

              <motion.div
                initial={{ opacity: 0, x: 20 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.4, delay: 0.3 }}
                className="bg-[#fffaf3] p-6 rounded-xl border border-[#e0d7cb]"
              >
                <h2 className="text-xl font-semibold mb-2">
                  4. Collect Your Payment
                </h2>
                <p>
                  Once your artwork is sold, you will receive your payment
                  promptly.
                </p>
              </motion.div>
            </div>

            <div className="text-center mt-10">
              <button
                onClick={() => setStep(2)}
                className="bg-[#5a3c28] text-white px-8 py-3 rounded-lg text-lg hover:bg-[#3d281a] transition duration-300"
              >
                Start Selling
              </button>
            </div>
          </div>
        )}

        {step === 2 && (
          <form onSubmit={handleSubmit} className="space-y-6 text-[#5a3c28]">
            <div>
              <label className="block font-medium mb-1">Full Name:</label>
              <input
                type="text"
                name="name"
                required
                onChange={handleChange}
                className="w-full border border-gray-300 p-2 rounded-md"
              />
            </div>

            <div>
              <label className="block font-medium mb-1">Email:</label>
              <input
                type="email"
                name="email"
                required
                onChange={handleChange}
                className="w-full border border-gray-300 p-2 rounded-md"
              />
            </div>

            <div>
              <label className="block font-medium mb-1">
                Certificate (Optional):
              </label>
              <input
                type="file"
                name="certificate"
                accept=".pdf,.jpg,.png"
                onChange={handleChange}
                className="w-full border border-gray-300 p-2 rounded-md"
              />
            </div>

            <div>
              <label className="block font-medium mb-1">
                Upload Your Best Painting:
              </label>
              <input
                type="file"
                name="painting"
                required
                accept="image/*"
                onChange={handleChange}
                className="w-full border border-gray-300 p-2 rounded-md"
              />
            </div>

            <div>
              <label className="block font-medium mb-1">Address:</label>
              <textarea
                name="address"
                required
                onChange={handleChange}
                rows="3"
                className="w-full border border-gray-300 p-2 rounded-md"
              />
            </div>

            <button
              type="submit"
              className="bg-[#5a3c28] text-white px-6 py-2 rounded-lg hover:bg-[#3d281a]"
            >
              Submit Application
            </button>
          </form>
        )}

        {step === 3 && (
          <div className="text-[#5a3c28] text-center space-y-4">
            <h2 className="text-2xl font-semibold">
              Your application is being processed!
            </h2>
            <p>
              Thank you for your interest in becoming a seller. We are reviewing
              your application and will notify you via email once approved.
            </p>
            <p className="italic">Please wait while we verify your details.</p>
          </div>
        )}
      </motion.div>
    </div>
  );
};

export default Sell;