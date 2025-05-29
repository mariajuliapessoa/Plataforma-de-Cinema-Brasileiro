"use client"

import { Button } from "@/components/ui/button";
import Link from "next/link";
import HamburgerSheet from "./hamburger-drawer";
import { config } from "@/config";
import { usePathname } from "next/navigation";
import Logo from "@/components/style/logo";

export default function NavBar() {
  const pathname = usePathname();
  const isActive = (path: string) => pathname.startsWith(path);

  return (
    <nav className="fixed top-0 left-0 right-0 w-full border-b z-50 bg-background px-4">
      <div className="h-20 flex items-center justify-between gap-4 mx-auto md:max-w-5xl">
        <div className="flex justify-between w-full md:w-fit md:justify-start items-center gap-4">
          <Logo />
          <HamburgerSheet />
        </div>
        <div className="md:flex gap-4 hidden">
          {config.public.navigation.navbarLinks.map((link) => (
            <Button variant={isActive(link.href) ? "active" : "ghost"} asChild key={link.href}>
              <Link href={link.href}>{link.label}</Link>
            </Button>
          ))}
        </div>
        <div className="md:flex gap-4 hidden">
          <Button size="lg" variant="link" asChild>
            <Link href="/login">Login</Link>
          </Button>
          <Button size="lg" variant="default" asChild>
            <Link href="/register">Cadastrar</Link>
          </Button>
        </div>
      </div>
    </nav>
  );
}