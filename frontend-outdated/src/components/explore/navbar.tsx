import Link from "next/link"
import { Search, Bell, User } from "lucide-react"
import { Button } from "@/components/ui/button"

export function Navbar() {
    return (
        <header className="fixed top-0 w-full z-50 bg-gradient-to-b from-black/80 to-transparent backdrop-blur-sm">
            <div className="container mx-auto flex items-center justify-between h-16 px-4 md:px-6 lg:px-8">
                <div className="flex items-center gap-8">
                    <Link href="/" className="text-red-600 font-bold text-2xl">
                        CinemaBR
                    </Link>
                    <nav className="hidden md:flex items-center gap-6">
                        <Link href="/" className="text-sm font-medium hover:text-white/80 transition">
                            Início
                        </Link>
                        <Link href="/series" className="text-sm font-medium text-white/70 hover:text-white/80 transition">
                            Séries
                        </Link>
                        <Link href="/filmes" className="text-sm font-medium text-white/70 hover:text-white/80 transition">
                            Filmes
                        </Link>
                        <Link href="/categorias" className="text-sm font-medium text-white/70 hover:text-white/80 transition">
                            Categorias
                        </Link>
                        <Link href="/avaliacoes" className="text-sm font-medium text-white/70 hover:text-white/80 transition">
                            Minhas Avaliações
                        </Link>
                    </nav>
                </div>
                <div className="flex items-center gap-4">
                    <Button variant="ghost" size="icon" className="text-white/70 hover:text-white">
                        <Search className="h-5 w-5" />
                    </Button>
                    <Button variant="ghost" size="icon" className="text-white/70 hover:text-white">
                        <Bell className="h-5 w-5" />
                    </Button>
                    <Button variant="ghost" size="icon" className="text-white/70 hover:text-white">
                        <User className="h-5 w-5" />
                    </Button>
                </div>
            </div>
        </header>
    )
}