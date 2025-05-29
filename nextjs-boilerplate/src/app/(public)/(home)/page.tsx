import FAQ from "./_components/sections/faq";
import Hero from "./_components/sections/hero";
import Bento from "./_components/sections/bento";
import CTA from "./_components/sections/cta";
import HIW from "./_components/sections/hiw";

export default function Home() {
  return (
    <main className="w-full flex flex-col gap-20">
      <Hero />
      <Bento />
      <HIW />
      <CTA />
      <FAQ />
    </main>
  );
}
