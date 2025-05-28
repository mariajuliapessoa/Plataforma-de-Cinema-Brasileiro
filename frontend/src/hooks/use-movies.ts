"use client"

import { useState, useEffect } from "react"

// Tipo da API do backend
export interface ApiMovie {
  id: string
  titulo: string
  diretor: string
  anoLancamento: number | null
  generos: string[]
  paisOrigem: string
  bannerUrl: string | null
}

// Tipo compatível com seus componentes existentes
export interface Movie {
  id: string
  title: string
  year: number
  director: string
  genre: string
  poster: string
  rating: number
  description: string
  posterUrl: string
  backdropUrl: string
  duration: string
  genres: string[]
}

const API_BASE_URL = "http://localhost:8080/api/filmes"

export function useMovies() {
  const [movies, setMovies] = useState<ApiMovie[]>([])
  const [loading, setLoading] = useState(false)
  const [importing, setImporting] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const loadMovies = async () => {
    setLoading(true)
    setError(null)
    try {
      console.log("Tentando carregar filmes de:", API_BASE_URL)

      const response = await fetch(API_BASE_URL, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })

      console.log("Status da resposta:", response.status)

      if (response.ok) {
        const data = await response.json()
        console.log("Filmes carregados:", data)
        setMovies(data)
      } else {
        throw new Error(`Erro HTTP: ${response.status}`)
      }
    } catch (error) {
      console.error("Erro ao carregar filmes:", error)
      setError(error instanceof Error ? error.message : "Erro desconhecido")
      setMovies([])
    } finally {
      setLoading(false)
    }
  }

  const importMoviesFromTMDB = async (pages = 3) => {
    setImporting(true)
    setError(null)
    try {
      console.log(`Importando ${pages} páginas do TMDB...`)

      const response = await fetch(`${API_BASE_URL}/importar/${pages}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      })

      if (response.ok) {
        const importedMovies = await response.json()
        console.log("Filmes importados:", importedMovies)
        setMovies((prev) => [...importedMovies, ...prev])
        return importedMovies
      } else {
        throw new Error(`Erro ao importar: ${response.status}`)
      }
    } catch (error) {
      console.error("Erro ao importar filmes:", error)
      setError(error instanceof Error ? error.message : "Erro ao importar filmes")
    } finally {
      setImporting(false)
    }
  }

  const addMovie = async (movieData: Omit<ApiMovie, "id">) => {
    setError(null)
    try {
      console.log("Adicionando filme:", movieData)

      const response = await fetch(API_BASE_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(movieData),
      })

      if (response.ok) {
        const newMovie = await response.json()
        console.log("Filme adicionado:", newMovie)
        setMovies((prev) => [newMovie, ...prev])
        return newMovie
      } else {
        throw new Error(`Erro ao adicionar filme: ${response.status}`)
      }
    } catch (error) {
      console.error("Erro ao adicionar filme:", error)
      setError(error instanceof Error ? error.message : "Erro ao adicionar filme")
      throw error
    }
  }

  const deleteMovie = async (id: string) => {
    setError(null)
    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: "DELETE",
      })

      if (response.ok) {
        setMovies((prev) => prev.filter((movie) => movie.id !== id))
      } else {
        throw new Error(`Erro ao remover filme: ${response.status}`)
      }
    } catch (error) {
      console.error("Erro ao remover filme:", error)
      setError(error instanceof Error ? error.message : "Erro ao remover filme")
    }
  }

  // Converte os filmes da API para o formato esperado pelos componentes existentes
  const convertToCarouselFormat = (apiMovies: ApiMovie[]): Movie[] => {
    return apiMovies.map((movie) => ({
      id: movie.id,
      title: movie.titulo,
      year: movie.anoLancamento || 2024,
      director: movie.diretor,
      genre: movie.generos.join(", ") || "Drama",
      poster: movie.bannerUrl || "/placeholder.svg?height=400&width=300",
      rating: Math.random() * 2 + 8, // Rating aleatório entre 8-10
      // Propriedades adicionais necessárias para compatibilidade
      description: `Filme brasileiro dirigido por ${movie.diretor}. ${movie.generos.join(", ")}.`,
      posterUrl: movie.bannerUrl || "/placeholder.svg?height=400&width=300",
      backdropUrl: movie.bannerUrl || "/placeholder.svg?height=400&width=300",
      duration: "120 min", // Duração padrão
      genres: movie.generos,
    }))
  }

  // Função para testar conexão com a API
  const testConnection = async () => {
    try {
      const response = await fetch(API_BASE_URL, { method: "HEAD" })
      return response.ok
    } catch {
      return false
    }
  }

  useEffect(() => {
    loadMovies()
  }, [])

  return {
    movies,
    loading,
    importing,
    error,
    loadMovies,
    importMoviesFromTMDB,
    addMovie,
    deleteMovie,
    convertToCarouselFormat,
    testConnection,
  }
}
