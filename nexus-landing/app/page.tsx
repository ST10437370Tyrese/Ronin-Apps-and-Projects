import Navbar from "@/components/Navbar";
import Hero from "@/components/Hero";
import Features from "@/components/Features";

export default function Home() {
  return (
    <main className="min-h-screen">
      <Navbar />
      <Hero />
      <Features />
      
      <footer className="py-20 text-center border-t border-slate-800">
        <h2 className="text-3xl font-bold mb-6">Ready to level up?</h2>
        <button className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-4 rounded-full font-bold text-lg transition-transform hover:scale-105 shadow-lg shadow-blue-600/30">
          Get Started for Free
        </button>
      </footer>
    </main>
  );
}