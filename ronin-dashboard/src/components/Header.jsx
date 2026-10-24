import { useState } from "react";

export default function Header() {
  const [isNotifOpen, setIsNotifOpen] = useState(false);

  return (
    <header className="bg-white shadow-sm p-4 flex justify-between items-center relative z-10">
      <h2 className="text-xl font-semibold text-gray-800">Dashboard</h2>
      
      <div className="relative">
        {/* Notification Bell Button */}
        <button 
          onClick={() => setIsNotifOpen(!isNotifOpen)}
          className="p-2 bg-gray-100 rounded-full hover:bg-gray-200 transition-colors relative focus:outline-none focus:ring-2 focus:ring-blue-500"
          aria-label="Notifications"
        >
          🔔
          {/* Red dot indicator for unread notifications */}
          <span className="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
        </button>
        
        {/* Dropdown Menu (Only shows if isNotifOpen is true) */}
        {isNotifOpen && (
          <div className="absolute right-0 mt-2 w-72 bg-white border border-gray-100 rounded-lg shadow-xl overflow-hidden">
            <div className="p-3 border-b border-gray-100 font-semibold text-gray-700 bg-gray-50">
              Recent Activity
            </div>
            <ul className="text-sm text-gray-600 divide-y divide-gray-100">
              <li className="p-3 hover:bg-gray-50 cursor-pointer">
                <span className="block font-medium text-gray-800">New project assigned</span>
                <span className="text-xs text-gray-500">10 mins ago</span>
              </li>
              <li className="p-3 hover:bg-gray-50 cursor-pointer">
                <span className="block font-medium text-gray-800">Invoice #1024 paid</span>
                <span className="text-xs text-gray-500">2 hours ago</span>
              </li>
              <li className="p-3 hover:bg-gray-50 cursor-pointer">
                <span className="block font-medium text-gray-800">3 tasks are due this week</span>
                <span className="text-xs text-gray-500">1 day ago</span>
              </li>
            </ul>
            <div className="p-2 text-center border-t border-gray-100">
              <button className="text-blue-600 text-sm font-medium hover:underline">
                Mark all as read
              </button>
            </div>
          </div>
        )}
      </div>
    </header>
  );
}