import { Outlet } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import Header from "../components/Header";

export default function DashboardLayout() {
  return (
    <div className="flex h-screen bg-gray-50 font-sans">
      {/* Sidebar stays fixed on the left */}
      <Sidebar />

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Header stays fixed at the top */}
        <Header />
        
        {/* The Outlet renders the specific page content (Overview, Projects, etc.) */}
        <main className="flex-1 overflow-y-auto p-6">
          <Outlet /> 
        </main>
      </div>
    </div>
  );
}