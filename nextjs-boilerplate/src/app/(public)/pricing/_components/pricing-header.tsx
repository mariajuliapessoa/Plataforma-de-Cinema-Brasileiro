import { config } from "@/config";

export default function PricingHeader() {
  return (
    <header className="py-20 pb-6 text-center flex flex-col gap-4">
      <h1 className="text-6xl text-center font-bold font-serif">{config.public.pricing.title}</h1>
      <p className="text-muted-foreground text-center text-xl">
        {config.public.pricing.description}
      </p>
    </header>
  );
} 