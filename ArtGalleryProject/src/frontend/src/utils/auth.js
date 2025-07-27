import { jwtDecode } from "jwt-decode";
import axiosInstance from '../axiosInstance';

// Get role by decoding the JWT token using jwt-decode library
export function getUserRole() {
  const token = localStorage.getItem("token");
  if (!token) return null;
  try {
    const decoded = jwtDecode(token);
    console.log("Decoded JWT:", decoded);
    console.log("User Role:", decoded.role);
    return decoded.role || null;
  } catch (e) {
    return null;
  }
}

// Get username from localStorage or fetch from profile
export async function getUsername() {
  // First try to get from localStorage
  const cachedUsername = localStorage.getItem("username");
  if (cachedUsername) {
    return cachedUsername;
  }

  // If not in localStorage, fetch from profile
  const token = localStorage.getItem("token");
  if (!token) return null;
  
  try {
    const response = await axiosInstance.get("/user/profile");
    const username = response.data.name;
    if (username) {
      localStorage.setItem("username", username);
      return username;
    }
    return null;
  } catch (e) {
    console.error("Failed to fetch username:", e);
    return null;
  }
}

// Get username synchronously (for immediate use in components)
export function getUsernameSync() {
  return localStorage.getItem("username");
}