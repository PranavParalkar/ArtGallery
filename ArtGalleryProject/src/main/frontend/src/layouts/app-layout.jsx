import Header from "../components/header";
import { Outlet } from "react-router-dom";

const AppLayout = () => {
  return (
    <div>
      <main className=" ">
        <div className="">
          <Header />
        </div>
        <div className="absolute">
          <Outlet />
        </div>
      </main>
    </div>
  );
};

export default AppLayout;
