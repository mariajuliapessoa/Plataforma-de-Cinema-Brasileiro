"use client"

import { HeroSection } from "../../../components/explore/hero-section"
import { MovieCarousel } from "../../../components/explore/movie-carousel"
import { Navbar } from "../../../components/explore/navbar"
import { AdminPanel } from "../../../components/explore/admin-panel"
import { useMovies } from "../../../hooks/use-movies"
import { featuredMovie } from "../../../data/movies"
import { useMemo } from "react"

export default function Home() {
  const { movies, loading, importing, error, importMoviesFromTMDB, addMovie, convertToCarouselFormat, testConnection } =
    useMovies()

  // Converte os filmes da API para o formato dos carrosséis
  const moviesForCarousel = useMemo(() => convertToCarouselFormat(movies), [movies])

  // Organiza os filmes por categorias
  const categorizedMovies = useMemo(() => {
    const currentYear = new Date().getFullYear()

    return {
      newReleases: moviesForCarousel.filter((movie) => movie.year >= currentYear - 2).slice(0, 10),
      classics: moviesForCarousel.filter((movie) => movie.year < 2000).slice(0, 10),
      documentaries: moviesForCarousel
        .filter(
          (movie) =>
            movie.genre.toLowerCase().includes("documentário") || movie.genre.toLowerCase().includes("documentary"),
        )
        .slice(0, 10),
      awardWinning: moviesForCarousel.filter((movie) => movie.rating >= 8.5).slice(0, 10),
      allMovies: moviesForCarousel.slice(0, 10),
    }
  }, [moviesForCarousel])

  return (
    <div className="min-h-screen bg-black text-white">
      <Navbar />
      <main>
        <HeroSection movie={featuredMovie} />
        <div className="space-y-8 pb-16">
          {/* Mostra carrosséis baseados nos dados da API */}
          {categorizedMovies.allMovies.length > 0 && (
            <MovieCarousel title="Catálogo Brasileiro" movies={categorizedMovies.allMovies} />
          )}

          {categorizedMovies.newReleases.length > 0 && (
            <MovieCarousel title="Lançamentos Recentes" movies={categorizedMovies.newReleases} />
          )}

          {categorizedMovies.classics.length > 0 && (
            <MovieCarousel title="Clássicos do Cinema Nacional" movies={categorizedMovies.classics} />
          )}

          {categorizedMovies.documentaries.length > 0 && (
            <MovieCarousel title="Documentários Brasileiros" movies={categorizedMovies.documentaries} />
          )}

          {categorizedMovies.awardWinning.length > 0 && (
            <MovieCarousel title="Premiados e Aclamados" movies={categorizedMovies.awardWinning} />
          )}

          {/* Mensagem quando não há filmes */}
          {movies.length === 0 && !loading && (
            <div className="text-center py-16">
              <h2 className="text-2xl font-bold text-yellow-500 mb-4">Nenhum filme encontrado</h2>
              <p className="text-gray-400 mb-6">Importe filmes do TMDB ou adicione manualmente para começar</p>
              <div className="text-sm text-gray-500">
                <p>💡 Clique no botão ⚙️ no canto inferior direito para acessar o painel de administração</p>
                {error && <p className="text-red-400 mt-2">⚠️ Erro de conexão: Verifique se o backend está rodando</p>}
              </div>
            </div>
          )}

          {/* Loading state */}
          {loading && (
            <div className="text-center py-16">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-yellow-500 mx-auto mb-4"></div>
              <p className="text-gray-400">Carregando filmes...</p>
            </div>
          )}

          {/* Estado de importação */}
          {importing && (
            <div className="fixed top-4 right-4 bg-green-600 text-white px-4 py-2 rounded-lg shadow-lg z-40">
              <div className="flex items-center gap-2">
                <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></div>
                <span>Importando filmes do TMDB...</span>
              </div>
            </div>
          )}
        </div>
      </main>

      {/* Painel de administração */}
      <AdminPanel
        onImportMovies={importMoviesFromTMDB}
        onAddMovie={addMovie}
        importing={importing}
        loading={loading}
        error={error}
        testConnection={testConnection}
      />
    </div>
  )
}
