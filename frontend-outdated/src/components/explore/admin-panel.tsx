"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Download, Plus, Settings, Loader2, Film, AlertCircle, CheckCircle } from "lucide-react"
import type { ApiMovie } from "../../hooks/use-movies"

interface AdminPanelProps {
  onImportMovies: (pages: number) => Promise<void>
  onAddMovie: (movie: Omit<ApiMovie, "id">) => Promise<void>
  importing: boolean
  loading: boolean
  error: string | null
  testConnection: () => Promise<boolean>
}

export function AdminPanel({ onImportMovies, onAddMovie, importing, loading, error, testConnection }: AdminPanelProps) {
  const [showAdmin, setShowAdmin] = useState(false)
  const [showAddMovie, setShowAddMovie] = useState(false)
  const [connectionStatus, setConnectionStatus] = useState<"unknown" | "connected" | "disconnected">("unknown")
  const [addMovieError, setAddMovieError] = useState<string | null>(null)
  const [formData, setFormData] = useState({
    titulo: "",
    diretor: "",
    anoLancamento: "",
    generos: "",
    paisOrigem: "Brasil",
    bannerUrl: "",
  })

  const checkConnection = async () => {
    const isConnected = await testConnection()
    setConnectionStatus(isConnected ? "connected" : "disconnected")
  }

  const handleAddMovie = async (e: React.FormEvent) => {
    e.preventDefault()
    setAddMovieError(null)

    try {
      const movieData: Omit<ApiMovie, "id"> = {
        titulo: formData.titulo,
        diretor: formData.diretor,
        anoLancamento: formData.anoLancamento ? Number.parseInt(formData.anoLancamento) : null,
        generos: formData.generos
          .split(",")
          .map((g) => g.trim())
          .filter((g) => g),
        paisOrigem: formData.paisOrigem,
        bannerUrl: formData.bannerUrl || null,
      }

      await onAddMovie(movieData)
      setShowAddMovie(false)
      setFormData({
        titulo: "",
        diretor: "",
        anoLancamento: "",
        generos: "",
        paisOrigem: "Brasil",
        bannerUrl: "",
      })
    } catch (error) {
      setAddMovieError(error instanceof Error ? error.message : "Erro ao adicionar filme")
    }
  }

  return (
    <>
      {/* Botão flutuante de administração */}
      <div className="fixed bottom-6 right-6 z-50">
        <Button
          onClick={() => {
            setShowAdmin(true)
            checkConnection()
          }}
          className="rounded-full w-14 h-14 bg-yellow-500 hover:bg-yellow-600 text-black shadow-lg"
        >
          <Settings className="h-6 w-6" />
        </Button>
      </div>

      {/* Modal de administração */}
      {showAdmin && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-gray-900 text-white border border-gray-700 rounded-lg p-6 max-w-md w-full mx-4 max-h-[90vh] overflow-y-auto">
            <div className="mb-4">
              <h2 className="text-xl font-bold text-yellow-500">Administração - Cinema Brasileiro</h2>
              <p className="text-gray-300 text-sm">Gerencie o catálogo de filmes brasileiros</p>
            </div>

            {/* Status da conexão */}
            <div className="mb-4 p-3 rounded-lg bg-gray-800">
              <div className="flex items-center gap-2">
                {connectionStatus === "connected" && (
                  <>
                    <CheckCircle className="h-4 w-4 text-green-500" />
                    <span className="text-green-400 text-sm">API conectada</span>
                  </>
                )}
                {connectionStatus === "disconnected" && (
                  <>
                    <AlertCircle className="h-4 w-4 text-red-500" />
                    <span className="text-red-400 text-sm">API desconectada</span>
                  </>
                )}
                {connectionStatus === "unknown" && (
                  <span className="text-gray-400 text-sm">Verificando conexão...</span>
                )}
              </div>
              {connectionStatus === "disconnected" && (
                <div className="mt-2 text-xs text-gray-400">
                  <p>Certifique-se de que o backend está rodando em:</p>
                  <code className="text-yellow-400">http://localhost:8080</code>
                  <p className="mt-1">E que o CORS está configurado corretamente.</p>
                </div>
              )}
            </div>

            {/* Erro geral */}
            {error && (
              <div className="mb-4 p-3 rounded-lg bg-red-900 border border-red-700">
                <div className="flex items-center gap-2">
                  <AlertCircle className="h-4 w-4 text-red-400" />
                  <span className="text-red-400 text-sm">Erro: {error}</span>
                </div>
              </div>
            )}

            <div className="space-y-4">
              <Card className="bg-gray-800 border-gray-700">
                <CardHeader>
                  <CardTitle className="text-white flex items-center gap-2">
                    <Download className="h-5 w-5 text-green-500" />
                    Importar do TMDB
                  </CardTitle>
                  <CardDescription className="text-gray-400">
                    Importe filmes brasileiros diretamente da base do TMDB
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-3">
                  <Button
                    onClick={() => onImportMovies(3)}
                    disabled={importing || loading || connectionStatus === "disconnected"}
                    className="w-full bg-green-600 hover:bg-green-700 disabled:bg-gray-600"
                  >
                    {importing ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Film className="mr-2 h-4 w-4" />}
                    Importar 3 Páginas (~60 filmes)
                  </Button>
                  <Button
                    onClick={() => onImportMovies(5)}
                    disabled={importing || loading || connectionStatus === "disconnected"}
                    className="w-full bg-gray-700 hover:bg-gray-600 border border-green-600 text-green-400 disabled:bg-gray-600 disabled:text-gray-500"
                  >
                    {importing ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Film className="mr-2 h-4 w-4" />}
                    Importar 5 Páginas (~100 filmes)
                  </Button>
                </CardContent>
              </Card>

              <Card className="bg-gray-800 border-gray-700">
                <CardHeader>
                  <CardTitle className="text-white flex items-center gap-2">
                    <Plus className="h-5 w-5 text-yellow-500" />
                    Adicionar Filme
                  </CardTitle>
                  <CardDescription className="text-gray-400">Adicione filmes manualmente ao catálogo</CardDescription>
                </CardHeader>
                <CardContent>
                  <Button
                    onClick={() => setShowAddMovie(true)}
                    disabled={connectionStatus === "disconnected"}
                    className="w-full bg-yellow-500 hover:bg-yellow-600 text-black disabled:bg-gray-600"
                  >
                    <Plus className="mr-2 h-4 w-4" />
                    Adicionar Novo Filme
                  </Button>
                </CardContent>
              </Card>
            </div>

            <div className="mt-6 flex justify-between">
              <Button onClick={checkConnection} variant="outline" className="text-gray-300 border-gray-600">
                Testar Conexão
              </Button>
              <Button onClick={() => setShowAdmin(false)} className="bg-gray-700 hover:bg-gray-600 text-white">
                Fechar
              </Button>
            </div>
          </div>
        </div>
      )}

      {/* Modal para adicionar filme */}
      {showAddMovie && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-gray-900 text-white border border-gray-700 rounded-lg p-6 max-w-md w-full mx-4 max-h-[90vh] overflow-y-auto">
            <div className="mb-4">
              <h2 className="text-xl font-bold text-yellow-500">Adicionar Novo Filme</h2>
              <p className="text-gray-300 text-sm">Preencha os dados do filme brasileiro</p>
            </div>

            {/* Erro de adição */}
            {addMovieError && (
              <div className="mb-4 p-3 rounded-lg bg-red-900 border border-red-700">
                <div className="flex items-center gap-2">
                  <AlertCircle className="h-4 w-4 text-red-400" />
                  <span className="text-red-400 text-sm">{addMovieError}</span>
                </div>
              </div>
            )}

            <form onSubmit={handleAddMovie}>
              <div className="space-y-4">
                <div>
                  <label htmlFor="titulo" className="block text-white text-sm font-medium mb-1">
                    Título *
                  </label>
                  <Input
                    id="titulo"
                    value={formData.titulo}
                    onChange={(e) => setFormData((prev) => ({ ...prev, titulo: e.target.value }))}
                    required
                    className="bg-gray-800 border-gray-600 text-white"
                  />
                </div>

                <div>
                  <label htmlFor="diretor" className="block text-white text-sm font-medium mb-1">
                    Diretor *
                  </label>
                  <Input
                    id="diretor"
                    value={formData.diretor}
                    onChange={(e) => setFormData((prev) => ({ ...prev, diretor: e.target.value }))}
                    required
                    className="bg-gray-800 border-gray-600 text-white"
                  />
                </div>

                <div>
                  <label htmlFor="anoLancamento" className="block text-white text-sm font-medium mb-1">
                    Ano de Lançamento
                  </label>
                  <Input
                    id="anoLancamento"
                    type="number"
                    min="1900"
                    max="2030"
                    value={formData.anoLancamento}
                    onChange={(e) => setFormData((prev) => ({ ...prev, anoLancamento: e.target.value }))}
                    className="bg-gray-800 border-gray-600 text-white"
                  />
                </div>

                <div>
                  <label htmlFor="generos" className="block text-white text-sm font-medium mb-1">
                    Gêneros (separados por vírgula)
                  </label>
                  <Input
                    id="generos"
                    placeholder="Drama, Comédia, Romance"
                    value={formData.generos}
                    onChange={(e) => setFormData((prev) => ({ ...prev, generos: e.target.value }))}
                    className="bg-gray-800 border-gray-600 text-white"
                  />
                </div>

                <div>
                  <label htmlFor="bannerUrl" className="block text-white text-sm font-medium mb-1">
                    URL do Poster
                  </label>
                  <Input
                    id="bannerUrl"
                    type="url"
                    placeholder="https://exemplo.com/poster.jpg"
                    value={formData.bannerUrl}
                    onChange={(e) => setFormData((prev) => ({ ...prev, bannerUrl: e.target.value }))}
                    className="bg-gray-800 border-gray-600 text-white"
                  />
                </div>
              </div>

              <div className="mt-6 flex gap-3 justify-end">
                <Button
                  type="button"
                  onClick={() => {
                    setShowAddMovie(false)
                    setAddMovieError(null)
                  }}
                  className="bg-gray-700 hover:bg-gray-600 text-white"
                >
                  Cancelar
                </Button>
                <Button type="submit" className="bg-yellow-500 hover:bg-yellow-600 text-black">
                  <Plus className="mr-2 h-4 w-4" />
                  Adicionar Filme
                </Button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  )
}
