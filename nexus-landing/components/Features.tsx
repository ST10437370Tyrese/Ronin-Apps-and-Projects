"use client";
import { useLanguage } from "@/context/LanguageContext";
import { motion } from "framer-motion";
import { Zap, Shield, Cpu } from "lucide-react";

export default function Features() {
  const { t } = useLanguage();

  const features = [
    { icon: Zap, en: "Lightning Fast", ar: "سرعة البرق", descEn: "Built on Next.js for instant loading times.", descAr: "مبني على Next.js لأوقات تحميل فورية." },
    { icon: Shield, en: "Secure by Default", ar: "آمن افتراضياً", descEn: "Enterprise-grade security for your data.", descAr: "أمان على مستوى المؤسسات لبياناتك." },
    { icon: Cpu, en: "AI Powered", ar: "مدعوم بالذكاء الاصطناعي", descEn: "Smart automations that learn your habits.", descAr: "أتمتة ذكية تتعلم عاداتك." },
  ];

  return (
    <section className="py-20 px-6 max-w-7xl mx-auto">
      <div className="grid md:grid-cols-3 gap-8">
        {features.map((f, i) => (
          <motion.div 
            key={i}
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ delay: i * 0.2 }}
            className="p-8 rounded-2xl bg-slate-900 border border-slate-800 hover:border-slate-700 transition-colors"
          >
            <f.icon className="w-10 h-10 text-blue-500 mb-6" />
            <h3 className="text-xl font-bold mb-2">{t(f.en, f.ar)}</h3>
            <p className="text-slate-400">{t(f.descEn, f.descAr)}</p>
          </motion.div>
        ))}
      </div>
    </section>
  );
}