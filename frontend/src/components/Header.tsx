"use client"

import { useState, useEffect } from "react"
import Link from "next/link"
import { Menu, X } from "lucide-react"
import { Button } from "@/components/ui/button"
import { motion, AnimatePresence } from "framer-motion"

export const Header = () => {
  const [isOpen, setOpen] = useState(false)
  const [scrolled, setScrolled] = useState(false)

  // Handle scroll effect
  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 10)
    }

    window.addEventListener("scroll", handleScroll)
    return () => window.removeEventListener("scroll", handleScroll)
  }, [])

  // Close mobile menu when clicking a link
  const handleLinkClick = () => {
    setOpen(false)
  }

  return (
    <header
      className={`w-full z-40 fixed top-0 left-0 bg-secondary transition-all duration-300 ${
        scrolled ? "border-b border-border shadow-sm" : ""
      }`}
    >
      <div className="container relative mx-auto min-h-15 flex gap-8 flex-row items-center justify-between px-4 sm:px-6 lg:px-8">
        {/* Logo */}
        <div className="flex-shrink-0">
          <Link href="/">
            <p className="font-semibold cursor-pointer text-foreground text-xl">
              bra<span className="text-primary">cine</span>
            </p>
          </Link>
        </div>

        {/* Navegação Desktop */}
        <nav className="hidden lg:flex flex-1 justify-center items-center gap-8 mx-8">
          <Link
            href="/features"
            className="flex items-center gap-2 text-sm font-medium text-foreground hover:text-primary transition-colors"
          >
            Recursos
          </Link>
          <Link
            href="/pricing"
            className="flex items-center gap-2 text-sm font-medium text-foreground hover:text-primary transition-colors"
          >
            Preços
          </Link>
          <Link
            href="/about"
            className="flex items-center gap-2 text-sm font-medium text-foreground hover:text-primary transition-colors"
          >
            Sobre
          </Link>
          <Link
            href="/contact"
            className="flex items-center gap-2 text-sm font-medium text-foreground hover:text-primary transition-colors"
          >
            Contato
          </Link>
        </nav>

        {/* Botões de ação - Desktop */}
        <div className="hidden lg:flex items-center gap-4">
          <Link href="/login">
            <Button variant="outline" className="text-foreground hover:text-primary cursor-pointer border">
              Entrar
            </Button>
          </Link>
          <Link href="/register">
            <Button className="bg-primary hover:bg-primary/90 text-primary-foreground cursor-pointer">Registre-se</Button>
          </Link>
        </div>

        {/* Menu Mobile */}
        <div className="flex lg:hidden items-center gap-4">
          <Button
            variant="ghost"
            onClick={() => setOpen(!isOpen)}
            className="p-2 hover:bg-primary/10 text-foreground"
            aria-expanded={isOpen}
            aria-label={isOpen ? "Fechar menu" : "Abrir menu"}
          >
            {isOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </Button>
        </div>
      </div>

      {/* Mobile Menu Dropdown with Animation */}
      <AnimatePresence>
        {isOpen && (
          <motion.div
            initial={{ opacity: 0, height: 0 }}
            animate={{ opacity: 1, height: "auto" }}
            exit={{ opacity: 0, height: 0 }}
            transition={{ duration: 0.3 }}
            className="lg:hidden border-t border-border shadow-lg bg-background overflow-hidden"
          >
            <div className="container px-4 py-6 flex flex-col space-y-4">
              <Link
                href="/features"
                className="flex items-center gap-2 py-2 text-sm font-medium text-foreground hover:text-primary"
                onClick={handleLinkClick}
              >
                Recursos
              </Link>
              <Link
                href="/pricing"
                className="flex items-center gap-2 py-2 text-sm font-medium text-foreground hover:text-primary"
                onClick={handleLinkClick}
              >
                Preços
              </Link>
              <Link
                href="/about"
                className="flex items-center gap-2 py-2 text-sm font-medium text-foreground hover:text-primary"
                onClick={handleLinkClick}
              >
                Sobre
              </Link>
              <Link
                href="/contact"
                className="flex items-center gap-2 py-2 text-sm font-medium text-foreground hover:text-primary"
                onClick={handleLinkClick}
              >
                Contato
              </Link>
              <div className="pt-4 flex flex-col gap-3 border-t border-border">
                <Link href="/login" onClick={handleLinkClick}>
                  <Button variant="outline" className="w-full justify-center cursor-pointer border">
                    Entrar
                  </Button>
                </Link>
                <Link href="/register" onClick={handleLinkClick}>
                  <Button className="w-full justify-center bg-primary hover:bg-primary/90 cursor-pointer">Registre-se</Button>
                </Link>
              </div>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </header>
  )
}
