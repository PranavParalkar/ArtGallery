import { RouterProvider, createBrowserRouter } from "react-router-dom";

import AppLayout from "./layouts/app-layout";

import "./App.css";
import MainTab from "./pages/dashboard";
import Login from "./pages/login";
import Onboarding from "./pages/onboarding";

const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      {
        path: "/dash",
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
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
