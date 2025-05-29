"use client"

import { Button } from "@/components/ui/button";
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer"
import Link from "next/link";
import { config } from "@/config";
import { VisuallyHidden } from "@radix-ui/react-visually-hidden";
import { useState } from "react";

export default function HamburgerDrawer() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <Drawer open={isOpen} onOpenChange={setIsOpen}>
      <DrawerTrigger asChild>
        <Button className="md:hidden relative" variant="hidden">
          <div className="relative flex overflow-hidden items-center justify-center transform transition-all duration-200">
            <div className="flex flex-col justify-between w-[20px] h-[20px] transform transition-all duration-300 origin-center overflow-hidden">
              <div className={`bg-foreground h-[1px] w-7 transform transition-all duration-300 origin-left ${isOpen ? "translate-x-10" : ""}`}></div>
              <div className={`bg-foreground h-[1px] w-7 rounded transform transition-all duration-300 delay-75 ${isOpen ? "translate-x-10" : ""}`}></div>
              <div className={`bg-foreground h-[1px] w-7 transform transition-all duration-300 origin-left delay-150 ${isOpen ? "translate-x-10" : ""}`}></div>

              <div className={`absolute items-center justify-between transform transition-all duration-500 top-2.5 -translate-x-10 flex ${isOpen ? "translate-x-0 w-12" : "w-0"}`}>
                <div className={`absolute bg-foreground h-[1px] w-5 transform transition-all duration-500 delay-300 ${isOpen ? "rotate-45" : "rotate-0"}`}></div>
                <div className={`absolute bg-foreground h-[1px] w-5 transform transition-all duration-500 delay-300 ${isOpen ? "-rotate-45" : "rotate-0"}`}></div>
              </div>
            </div>
          </div>
        </Button>
      </DrawerTrigger>
      <DrawerContent>
        <VisuallyHidden asChild>
          <DrawerTitle>Menu</DrawerTitle>
        </VisuallyHidden>
        <div className="mx-auto w-full max-w-sm px-4 py-10">
          <div className="flex flex-col gap-4">
            {config.public.navigation.drawerLinks.map((link) => (
              <DrawerClose asChild key={link.href}>
                <Button className="justify-start text-md w-full h-20" variant="outline" asChild>
                  <Link href={link.href}>
                    <link.icon className="size-8 p-2 bg-foreground/10 rounded-md" />
                    {link.label}
                  </Link>
                </Button>
              </DrawerClose>
            ))}
            <div className="flex flex-col gap-2">
              <DrawerClose asChild>
                <Button className="justify-start text-md w-full" size="xl" variant="default" asChild>
                  <Link href="/login">Login</Link>
                </Button>
              </DrawerClose>
              <DrawerClose asChild>
                <Button className="justify-start text-md w-full" size="xl" variant="secondary" asChild>
                  <Link href="/register">Cadastrar</Link>
                </Button>
              </DrawerClose>
            </div>
          </div>
        </div>
      </DrawerContent>
    </Drawer>
  );
}