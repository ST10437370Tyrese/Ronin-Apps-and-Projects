import { useState } from "react";
import { useApp } from "../context/AppContext";

export default function Settings() {
  const { user, setUser } = useApp();
  // Local state for the form
  const [formData, setFormData] = useState(user);
  const [isSaved, setIsSaved] = useState(false);

  // Handle typing in the inputs
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setIsSaved(false); // Hide "Saved" message if they start typing again
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevents page reload
    setUser(formData);  // Updates the global state
    setIsSaved(true);   // Shows success message
  };

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-800">Profile Settings</h1>
        {isSaved && (
          <span className="text-green-600 bg-green-100 px-3 py-1 rounded-full text-sm font-medium">
            Profile Saved!
          </span>
        )}
      </div>
      
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
          <input 
            type="text" 
            name="name"
            value={formData.name} 
            onChange={handleChange}
            required
            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
          />
        </div>
        
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Email Address</label>
          <input 
            type="email" 
            name="email"
            value={formData.email} 
            onChange={handleChange}
            required
            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">New Password (Optional)</label>
          <input 
            type="password" 
            name="password"
            value={formData.password} 
            onChange={handleChange}
            placeholder="••••••••" 
            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
          />
        </div>

        <div className="pt-4 border-t border-gray-100 flex justify-end">
          <button 
            type="submit"
            className="bg-gray-900 hover:bg-gray-800 text-white px-6 py-2 rounded-lg text-sm font-medium transition-colors"
          >
            Save Changes
          </button>
        </div>
      </form>
    </div>
  );
}