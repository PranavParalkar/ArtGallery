import jwtDecode from "jwt-decode";

// Get role by decoding the JWT token using jwt-decode library
export function getUserRole() {
  const token = localStorage.getItem("token");
  if (!token) return null;
  try {
    const decoded = jwtDecode(token);
    return decoded.role || null;
  } catch (e) {
    return null;
  }
}