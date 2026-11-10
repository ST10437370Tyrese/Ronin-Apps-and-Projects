"use client";
import { useLanguage } from "@/context/LanguageContext";
import { motion } from "framer-motion";
import Image from "next/image";

export default function Hero() {
  const { t } = useLanguage();

  return (
    <section className="pt-32 pb-20 px-6 max-w-7xl mx-auto flex flex-col items-center text-center">
      <motion.h1 
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="text-5xl md:text-7xl font-extrabold tracking-tight mb-6 bg-linear-to-r from-blue-400 to-indigo-600 bg-clip-text text-transparent"
      >
        {t("Supercharge Your Workflow.", "ضاعف إنتاجيتك اليوم.")}
      </motion.h1>
      
      <motion.p 
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.3, duration: 0.5 }}
        className="text-lg md:text-xl text-slate-400 max-w-2xl mb-10"
      >
        {t(
          "Nexus uses artificial intelligence to automate your daily tasks, so you can focus on what actually matters.",
          "يستخدم نيكسوس الذكاء الاصطناعي لأتمتة مهامك اليومية، حتى تتمكن من التركيز على ما يهم حقًا."
        )}
      </motion.p>

      <motion.div 
        initial={{ opacity: 0, scale: 0.9 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ delay: 0.5 }}
        className="relative w-full max-w-4xl rounded-2xl overflow-hidden border border-slate-800 shadow-2xl shadow-blue-900/20 mt-10"
      >
        <Image 
          src="https://images.unsplash.com/photo-1551288049-bebda4e38f71?q=80&w=2000&auto=format&fit=crop" 
          alt="Dashboard Preview" 
          width={1200} 
          height={600} 
          priority 
          className="object-cover"
        />
      </motion.div>
    </section>
  );
}