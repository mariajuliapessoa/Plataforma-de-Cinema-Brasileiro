import { MovieCard } from "./movie-card"
import type { Movie } from "../../models/movie"
import { ChevronLeft, ChevronRight } from "lucide-react"
import { Button } from "@/components/ui/button"

interface MovieCarouselProps {
    title: string
    movies: Movie[]
}

export function MovieCarousel({ title, movies }: MovieCarouselProps) {
    return (
        <div className="px-4 space-y-3">
            <h2 className="text-xl font-semibold">{title}</h2>
            <div className="relative group">
                <div className="flex overflow-x-auto space-x-4 pb-4 scrollbar-hide">
                    {movies.map((movie) => (
                        <MovieCard key={movie.id} movie={movie} />
                    ))}
                </div>
                <Button
                    variant="ghost"
                    size="icon"
                    className="absolute left-0 top-1/2 -translate-y-1/2 bg-black/50 rounded-full p-2 opacity-0 group-hover:opacity-100 transition"
                >
                    <ChevronLeft className="h-6 w-6" />
                </Button>
                <Button
                    variant="ghost"
                    size="icon"
                    className="absolute right-0 top-1/2 -translate-y-1/2 bg-black/50 rounded-full p-2 opacity-0 group-hover:opacity-100 transition"
                >
                    <ChevronRight className="h-6 w-6" />
                </Button>
            </div>
        </div>
    )
}
