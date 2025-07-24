import { RouterProvider, createBrowserRouter } from "react-router-dom";

import AppLayout from "./layouts/app-layout";

import "./App.css";
import Dashboard from "./pages/dashboard";
import Login from "./pages/login";
import Auction from "./pages/auction";
import Sell from "./pages/sell";
import Discover from "./pages/discover";
import Shop from "./pages/shop";
import BiddingFrontend from "./pages/biddingFrontend";
import Admin from "./pages/admin";
import ProfilePage from "./components/ProfilePage";
import { isTokenExpired, logoutUser } from './axiosInstance';

const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      {
        path: "/",
        element: <Dashboard />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/auctions",
        element: <Auction />,
      },
      {
        path: "/sell",
        element: <Sell />,
      },

      {
        path: "/discover",
        element: <Discover />,
      },
      {
        path: "/shop",
        element: <Shop />,
      },
      {
        path: "/admin",
        element: <Admin />,
      },
      {
        path: "/profile",
        element: <ProfilePage />,
      },

      {
        path: "/biddingFrontend/:paintingId", // Dynamic route with paintingId
        element: <BiddingFrontend />,
      },
    ],
  },
]);

// Check token on app load
const token = localStorage.getItem('token');
if (token && isTokenExpired(token)) {
  logoutUser();
}

function App() {
  return <RouterProvider router={router} />;
}

export default App;
