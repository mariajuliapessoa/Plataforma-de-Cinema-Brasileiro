import Logo from "@/components/style/logo";
import { Button } from "@/components/ui/button";
import { config } from "@/config";
import Link from "next/link";

export default function CTA() {
  return (
    <section className="flex items-center justify-between relative overflow-hidden w-full bg-muted border rounded-xl py-10 px-8">
      <div className="absolute w-20 h-80 bg-primary/20 rounded-full -top-10 -left-10 blur-xl"></div>
      <div className="absolute w-60 h-60 bg-primary/15 rounded-full -bottom-20 -right-20 blur-xl"></div>
      <div className="absolute w-80 h-80 bg-primary/25 rounded-full top-1/2 right-10 blur-lg"></div>
      <div className="flex flex-col gap-4 relative z-10">
        <CTAHeader title={config.public.cta.title} description={config.public.cta.description} />
        <Button className="w-fit" size={"xl"} asChild>
          <Link href={config.public.cta.button.href}>{config.public.cta.button.label}</Link>
        </Button>
      </div>
      <div className="relative z-10">
        <Logo />
      </div>
    </section>
  );
}

const CTAHeader = ({ title, description }: { title: string, description: string }) => {
  return (
    <div className="flex flex-col gap-2">
      <h1 className="text-4xl font-bold">{title}</h1>
      <p className="text-muted-foreground text-lg">{description}</p>
    </div>
  );
}