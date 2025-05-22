import { Card } from "@/components/ui/card"
import { StarRating } from "./star-rating"
import type { Movie } from "../../models/movie"
import { Info, Play } from "lucide-react"

interface MovieCardProps {
    movie: Movie
}

export function MovieCard({ movie }: MovieCardProps) {
    return (
        <div className="group relative flex-shrink-0 w-[160px] sm:w-[180px] md:w-[220px]">
            <Card className="overflow-hidden border-0 rounded-md transition-transform duration-300 group-hover:scale-105 group-hover:z-10">
                <div className="relative aspect-[2/3]">
                    <img src={movie.posterUrl || "/placeholder.svg"} alt={movie.title} className="w-full h-full object-cover" />
                    <div className="absolute inset-0 bg-black/0 group-hover:bg-black/60 transition-all duration-300 flex items-center justify-center opacity-0 group-hover:opacity-100">
                        <div className="flex gap-2">
                            <button className="bg-white rounded-full p-2">
                                <Play className="h-4 w-4 text-black" />
                            </button>
                            <button className="bg-white/20 rounded-full p-2">
                                <Info className="h-4 w-4" />
                            </button>
                        </div>
                    </div>
                </div>
                <div className="absolute bottom-0 left-0 right-0 p-2 bg-gradient-to-t from-black to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <h3 className="font-medium text-sm truncate">{movie.title}</h3>
                    <div className="flex items-center gap-1 mt-1">
                        <span className="text-green-500 text-xs font-semibold">{movie.rating.toFixed(1)}</span>
                        <StarRating rating={movie.rating} size="sm" />
                    </div>
                </div>
            </Card>
        </div>
    )
}