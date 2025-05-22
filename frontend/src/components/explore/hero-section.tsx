import { Button } from "@/components/ui/button"
import { StarRating } from "./star-rating"
import { Info, Play } from "lucide-react"
import type { Movie } from "../../models/movie"

interface HeroSectionProps {
    movie: Movie
}

export function HeroSection({ movie }: HeroSectionProps) {
    return (
        <div className="relative w-full h-[80vh] min-h-[500px]">
            <div className="absolute inset-0">
                <img src={movie.backdropUrl || "/placeholder.svg"} alt={movie.title} className="w-full h-full object-cover" />
                <div className="absolute inset-0 bg-gradient-to-r from-black via-black/70 to-transparent" />
                <div className="absolute inset-0 bg-gradient-to-t from-black via-black/50 to-transparent" />
            </div>

            <div className="relative h-full container mx-auto flex flex-col justify-end pb-20 pt-32 px-4 md:px-6 lg:px-8">
                <div className="max-w-2xl space-y-4">
                    <h1 className="text-3xl md:text-4xl lg:text-5xl font-bold">{movie.title}</h1>
                    <div className="flex flex-wrap items-center gap-4">
                        <span className="text-green-500 font-semibold">{movie.rating.toFixed(1)}/10</span>
                        <StarRating rating={movie.rating} />
                        <span className="text-white/70">{movie.year}</span>
                        <span className="text-white/70">{movie.duration}</span>
                    </div>
                    <p className="text-base md:text-lg text-white/90">{movie.description}</p>
                    <div className="flex flex-wrap gap-2">
                        {movie.genres.map((genre) => (
                            <span key={genre} className="px-2 py-1 bg-white/10 rounded-md text-sm">
                                {genre}
                            </span>
                        ))}
                    </div>
                    <div className="pt-4 flex flex-wrap gap-4">
                        <Button className="bg-white text-black hover:bg-white/90">
                            <Play className="mr-2 h-4 w-4" /> Assistir
                        </Button>
                        <Button variant="outline" className="border-white/30">
                            <Info className="mr-2 h-4 w-4" /> Mais Informações
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    )
}
