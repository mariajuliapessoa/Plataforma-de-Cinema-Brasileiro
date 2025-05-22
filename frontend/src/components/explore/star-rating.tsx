import { Star } from "lucide-react"

interface StarRatingProps {
  rating: number
  size?: "sm" | "md"
}

export function StarRating({ rating, size = "md" }: StarRatingProps) {
  // Convert rating from 0-10 scale to 0-5 stars
  const starRating = rating / 2

  const starSize = size === "sm" ? "h-3 w-3" : "h-4 w-4"

  return (
    <div className="flex items-center">
      {[1, 2, 3, 4, 5].map((star) => {
        if (star <= Math.floor(starRating)) {
          // Full star
          return <Star key={star} className={`${starSize} fill-yellow-400 text-yellow-400`} />
        } else if (star === Math.ceil(starRating) && !Number.isInteger(starRating)) {
          // Half star - we'll simulate this with opacity
          return <Star key={star} className={`${starSize} fill-yellow-400 text-yellow-400 opacity-60`} />
        } else {
          // Empty star
          return <Star key={star} className={`${starSize} text-gray-500`} />
        }
      })}
    </div>
  )
}
