"use client";

import Logo from "@/components/style/logo";
import Line from "@/components/style/line";
import Link from "next/link";
import { config } from "@/config";
import { Button } from "@/components/ui/button";
import { ArrowUpIcon } from "lucide-react";

export default function Footer() {
  return (
    <footer className="w-full flex items-center justify-center relative overflow-hidden">
      <section className="border-t pt-10 flex flex-col gap-4 items-start justify-center w-full  relative z-10">
        <div className="flex flex-col gap-4 items-start justify-center">
          <Logo />
          <p className="text-sm text-muted-foreground w-80">{config.project.description}</p>
        </div>
        <Line style="dashed" />
        <div className="w-full grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {config.public.navigation.footerSections.map((section) => (
            <FooterSection key={section.title} title={section.title} links={section.links} />
          ))}
        </div>
        <Line style="dashed" />
        <div className="w-full flex flex-col items-center md:flex-row md:justify-between justify-center gap-8">
          <div>
            <p className="text-sm text-muted-foreground">Copyright © {config.project.name} - {new Date().getFullYear()}</p>
            <p className="text-sm text-muted-foreground">Todos os direitos reservados</p>
          </div>
          <div className="flex flex-col gap-2 items-center justify-center">
            <Button onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })} variant="secondary" size="lg">
              <ArrowUpIcon className="size-4" />
              Voltar ao topo
            </Button>
          </div>
        </div>
        <div className="pt-30"></div>
      </section>
    </footer>
  );
}


interface FooterSectionProps {
  title: string;
  links: {
    label: string;
    href: string;
  }[];
}

const FooterSection = ({ title, links }: FooterSectionProps) => {
  return (
    <div className="flex-1 flex flex-col gap-4 items-start">
      <h3 className="text-xl font-bold">{title}</h3>
      <div className="flex flex-col gap-2 items-start">
        {links.map((link) => (
          <Button variant="mutedLink" size="text" asChild key={link.label}>
            <Link href={link.href}>{link.label}</Link>
          </Button>
        ))}
      </div>
    </div>
  );
};