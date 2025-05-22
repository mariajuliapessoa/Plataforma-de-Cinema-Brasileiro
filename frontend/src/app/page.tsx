import { Header } from "@/components/Header";
import { Hero } from "@/components/ui/animated-hero";

export default function Home() {
  return (
    <main className="min-h-screen bg-gradient-to-br from-[#050224] via-[#0A0538] to-[#0F0842]">
      <Header />
      <Hero />
    </main> 
  );
}
