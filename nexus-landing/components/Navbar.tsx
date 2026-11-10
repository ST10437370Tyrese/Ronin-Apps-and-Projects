"use client";
import { useLanguage } from "@/context/LanguageContext";
import { motion } from "framer-motion";

export default function Navbar() {
  const { toggleLanguage, language } = useLanguage();

  return (
    <motion.nav 
      initial={{ y: -50, opacity: 0 }} 
      animate={{ y: 0, opacity: 1 }} 
      className="fixed top-0 w-full flex justify-between items-center p-6 backdrop-blur-md bg-slate-950/70 border-b border-slate-800 z-50"
    >
      <div className="text-2xl font-bold tracking-tighter text-blue-500">NEXUS.</div>
      <button 
        onClick={toggleLanguage}
        className="px-4 py-2 border border-slate-700 rounded-full hover:bg-slate-800 transition-colors text-sm font-medium"
      >
        {language === "en" ? "العربية" : "English"}
      </button>
    </motion.nav>
  );
}