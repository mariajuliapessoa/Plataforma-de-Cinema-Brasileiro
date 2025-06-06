"use client"

import Logo from "@/components/style/logo";
import Link from "next/link";
import { config } from "@/config";
import { Button } from "@/components/ui/button";
import { usePathname } from "next/navigation";
import { useAuth } from "@/hooks/use-auth";

export default function NavBar() {
  const pathname = usePathname();
  const isActive = (path: string) => pathname.endsWith(path);
  const { user } = useAuth();

  return (
    <nav className="sticky top-0 left-0 right-0 w-full border-b z-50 bg-background px-4">
      <div className="h-20 flex items-center justify-between gap-4 mx-auto md:max-w-7xl">
        <div className="flex justify-between w-full md:w-fit md:justify-start items-center gap-4">
          <Logo />
        </div>
        <div className="md:flex gap-4 hidden">
          {config.protected.navigation.navbarLinks.map((link) => (
            <Button variant={isActive(link.href) ? "active" : "ghost"} asChild key={link.href}>
              <Link href={link.href}>{link.label}</Link>
            </Button>
          ))}
        </div>
        <div className="md:flex gap-4 hidden">
          <Button size="lg" variant="link" asChild>
            <Link href={user ? "/logout" : "/login"}>{user ? "Sair" : "Login"}</Link>
          </Button>
          <Button size="lg" variant="default" asChild>
            <Link href={user ? "/explore" : "/register"}>{user ? "Explorar" : "Cadastrar"}</Link>
          </Button>
        </div>
      </div>
    </nav>
  );
}