import { RouterProvider, createBrowserRouter } from "react-router-dom";

import AppLayout from "./layouts/app-layout";

import "./App.css";
import MainTab from "./pages/dashboard";
import Login from "./pages/login";
import Onboarding from "./pages/onboarding";
import Auction from "./pages/auction";
import Sell from "./pages/sell";
import Departments from "./pages/departments";
import Discover from "./pages/discover";
import Shop from "./pages/shop";

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
        path: "/onboarding",
        element: <Onboarding />,
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
        path: "/departments",
        element: <Departments />,
      },
      {
        path: "/discover",
        element: <Discover />,
      },
      {
        path: "/shop",
        element: <Shop />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
