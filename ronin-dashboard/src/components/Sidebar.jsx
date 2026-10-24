import { NavLink } from "react-router-dom";
import { useApp } from "../context/AppContext"; // <-- Import hook

export default function Sidebar() {
  const { user } = useApp(); // <-- Get user data

  const linkClass = ({ isActive }) =>
    `block p-3 rounded-lg transition-colors duration-200 ${
      isActive ? "bg-blue-600 text-white" : "text-gray-300 hover:bg-gray-800 hover:text-white"
    }`;

  return (
    <aside className="w-64 bg-gray-900 flex flex-col shadow-xl z-20">
      <div className="p-6 text-2xl font-bold text-white border-b border-gray-800 tracking-wider">
        FreelanceDash
      </div>
      
      <nav className="flex-1 p-4 space-y-2 overflow-y-auto">
        <NavLink to="/" className={linkClass}>Overview</NavLink>
        <NavLink to="/projects" className={linkClass}>Projects</NavLink>
        <NavLink to="/settings" className={linkClass}>Settings</NavLink>
      </nav>

      <div className="p-4 border-t border-gray-800">
        <div className="flex items-center space-x-3">
          <div className="w-8 h-8 rounded-full bg-blue-500 flex items-center justify-center text-white font-bold uppercase">
            {user.name.charAt(0)} {/* <-- Shows first letter of name */}
          </div>
          <div>
            <p className="text-sm font-medium text-white">{user.name}</p> {/* <-- Live name */}
            <p className="text-xs text-gray-400">Freelancer</p>
          </div>
        </div>
      </div>
    </aside>
  );
}