import { HeroSection } from "../../../components/explore/hero-section"
import { MovieCarousel } from "../../../components/explore/movie-carousel"
import { Navbar } from "../../../components/explore/navbar"
import { featuredMovie, brazilianClassics, newReleases, documentaries, awardWinning } from "../../../data/movies"

export default function Home() {
  return (
    <div className="min-h-screen bg-black text-white">
      <Navbar />
      <main>
        <HeroSection movie={featuredMovie} />
        <div className="space-y-8 pb-16">
          <MovieCarousel title="Lançamentos Brasileiros" movies={newReleases} />
          <MovieCarousel title="Clássicos do Cinema Nacional" movies={brazilianClassics} />
          <MovieCarousel title="Documentários Brasileiros" movies={documentaries} />
          <MovieCarousel title="Premiados em Festivais" movies={awardWinning} />
        </div>
      </main>
    </div>
  )
}
