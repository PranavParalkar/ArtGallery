import { RouterProvider, createBrowserRouter } from "react-router-dom";

import AppLayout from "./layouts/app-layout";

import "./App.css";
import MainTab from "./pages/dashboard";
import Login from "./pages/login";
import Auction from "./pages/auction";
import Sell from "./pages/sell";
import Discover from "./pages/discover";
import Shop from "./pages/shop";
import BiddingFrontend from "./pages/biddingFrontend";
const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      {
        path: "/",
        element: <MainTab />,
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
        path: "/biddingFrontend/:paintingId", // Dynamic route with paintingId
        element: <BiddingFrontend />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
