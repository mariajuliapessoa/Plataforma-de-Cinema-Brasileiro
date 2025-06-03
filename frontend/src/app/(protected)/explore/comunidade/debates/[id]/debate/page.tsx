"use client";

import { useEffect, useRef, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { getUserById } from "@/app/(protected)/actions";
import { getDebateById, getComentariosPorDebate,criarComentario } from "../../actions";
import { getFilmes, getFilmeById  } from "@/app/(protected)/explore/actions";
import { useAuth } from "@/hooks/use-auth";
import { toast } from "sonner";
import { Loader } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import Image from "next/image";
import type { FilmeType } from "@/schemas/filme.schema";

interface ComentarioComAutor {
  id: string;
  texto: string;
  autorId: string;
  filmeId: string;
  debateId?: string;
  dataCriacao: string;
  autorNome: string;
}

export default function DebatePage() {
  const { user } = useAuth();
  const router = useRouter();
  const { id } = useParams();

  const [titulo, setTitulo] = useState("");
  const [criador, setCriador] = useState("Desconhecido");
  const [comentarios, setComentarios] = useState<ComentarioComAutor[]>([]);
  const [texto, setTexto] = useState("");
  const [filmeId, setFilmeId] = useState("");           // ID do filme selecionado
  const [filmeSelecionado, setFilmeSelecionado] = useState<FilmeType | null>(null); // objeto completo

  const [query, setQuery] = useState("");                // termo de busca
  const [sugestoes, setSugestoes] = useState<FilmeType[]>([]);
  const [carregandoSugestoes, setCarregandoSugestoes] = useState(false);

  const [loading, setLoading] = useState(true);
  const [enviando, setEnviando] = useState(false);

  const [filmeTitles, setFilmeTitles] = useState<Record<string, string>>({});

  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        wrapperRef.current &&
        !wrapperRef.current.contains(event.target as Node)
      ) {
        setSugestoes([]);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [wrapperRef]);

  const fetchDebate = async () => {
    try {
      const debate = await getDebateById(id as string);
      if (!debate) throw new Error("Debate não encontrado");
      setTitulo(debate.titulo);

      const criadorData = await getUserById(debate.idCriador);
      setCriador(criadorData?.nome ?? "Desconhecido");
    } catch {
      toast.error("Erro ao carregar o debate.");
      router.push("/explore/comunidade");
    }
  };

  const fetchComentarios = async () => {
    try {
      const todos = await getComentariosPorDebate(id as string);
      const filmeIdsUnicos = Array.from(
        new Set(todos.map((c) => c.filmeId))
      );

      const filmesFetch = await Promise.all(
        filmeIdsUnicos.map(async (fid) => {
          try {
            const filme = await getFilmeById(fid);
            return { id: fid, titulo: filme.titulo };
          } catch {
            return { id: fid, titulo: fid }; // fallback: exibe próprio ID
          }
        })
      );

      const novoMapa: Record<string, string> = {};
      filmesFetch.forEach((f) => {
        novoMapa[f.id] = f.titulo;
      });
      setFilmeTitles(novoMapa);

      const enriquecidos = await Promise.all(
        todos.map(async (c) => {
          const autor = await getUserById(c.autorId);
          return {
            ...c,
            autorNome: autor?.nome ?? "Desconhecido",
          } as ComentarioComAutor;
        })
      );

      setComentarios(enriquecidos);
    } catch {
      toast.error("Erro ao carregar comentários.");
    }
  };

  const fetchFilmesPorTermo = async (termo: string) => {
    if (!termo.trim()) {
      setSugestoes([]);
      return;
    }
    setCarregandoSugestoes(true);
    try {
      const resposta = await getFilmes({
        page: 0,
        size: 12,
        query: termo,
      });
      setSugestoes(resposta.content);
    } catch (error) {
      console.error("erro em fetchFilmesPorTermo:", error);
    } finally {
      setCarregandoSugestoes(false);
    }
  };

  const enviarComentario = async () => {
    if (!texto.trim() || !filmeId) return;
    setEnviando(true);
    try {
      if (!user) {
        toast.error("Usuário não autenticado.");
        setEnviando(false);
        return;
      }
      await criarComentario({
        texto,
        autorId: user.id,
        debateId: id as string,
        filmeId,
      });
      setTexto("");
      setFilmeId("");
      setFilmeSelecionado(null);
      await fetchComentarios();
      toast.success("Comentário enviado!");
    } catch {
      toast.error("Erro ao comentar.");
    } finally {
      setEnviando(false);
    }
  };

  useEffect(() => {
    (async () => {
      setLoading(true);
      await Promise.all([fetchDebate(), fetchComentarios()]);
      setLoading(false);
    })();
  }, [id]);

  useEffect(() => {
    const delay = setTimeout(() => {
      fetchFilmesPorTermo(query);
    }, 300);
    return () => clearTimeout(delay);
  }, [query]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Loader className="w-10 h-10 animate-spin text-primary" />
      </div>
    );
  }  

  return (
    <main className="min-h-screen">
      <div className="relative w-full aspect-[3/1] rounded-lg overflow-hidden mb-6">
        <Image
          src="https://images.pexels.com/photos/7991259/pexels-photo-7991259.jpeg"
          alt="Banner de Debate"
          fill
          className="object-cover brightness-75"
          priority
        />
        <div className="absolute inset-0 flex items-end p-6">
          <h1 className="text-3xl sm:text-4xl font-bold text-white drop-shadow-lg">
            Debate: {titulo}
          </h1>
        </div>
      </div>

      <div className="max-w-3xl mx-auto px-4 sm:px-6 space-y-8 pb-16">
        <div className="text-sm text-muted-foreground">
          Criado por: <strong>{criador}</strong>
        </div>

        <section className="space-y-4">
          <h2 className="text-xl font-semibold">
            Comentários ({comentarios.length})
          </h2>
          {comentarios.length === 0 ? (
            <p className="text-muted-foreground italic">
              Nenhum comentário ainda.
            </p>
          ) : (
            comentarios.map((c) => (
              <div key={c.id} className="bg-white rounded-md shadow p-4">
                <div className="flex justify-between text-sm text-muted-foreground">
                  <span className="font-medium">{c.autorNome}</span>
                  <span>{new Date(c.dataCriacao).toLocaleString()}</span>
                </div>
                <p className="mt-2 text-gray-800">{c.texto}</p>
                <p className="mt-1 text-sm text-muted-foreground">
                  Filme:{" "}
                  <strong>{filmeTitles[c.filmeId] ?? c.filmeId}</strong>
                </p>
              </div>
            ))
          )}
        </section>

        {user && (
          <section className="space-y-4">
            <div ref={wrapperRef} className="relative">
              <label htmlFor="buscaFilme" className="text-sm font-medium">
                Buscar filme
              </label>
              <input
                id="buscaFilme"
                type="text"
                value={filmeSelecionado?.titulo ?? query}
                onChange={(e) => {
                  setFilmeId("");
                  setFilmeSelecionado(null);
                  setQuery(e.target.value);
                }}
                placeholder="Digite parte do título..."
                className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
                autoComplete="off"
              />

              {/* Lista de sugestões */}
              {sugestoes.length > 0 && (
                <ul className="absolute z-10 w-full bg-white border border-gray-200 rounded-md mt-1 max-h-48 overflow-auto">
                  {sugestoes.map((f) => (
                    <li
                      key={f.id}
                      onClick={() => {
                        setFilmeId(f.id);
                        setFilmeSelecionado(f);
                        setSugestoes([]);
                      }}
                      className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
                    >
                      {f.titulo} ({f.anoLancamento}) — {f.diretor}
                    </li>
                  ))}
                </ul>
              )}

              {carregandoSugestoes && (
                <div className="absolute right-2 top-9">
                  <Loader className="w-5 h-5 animate-spin text-primary" />
                </div>
              )}
            </div>

            <Textarea
              placeholder="Escreva seu comentário..."
              value={texto}
              onChange={(e) => setTexto(e.target.value)}
              className="resize-none"
            />
            <Button
              onClick={enviarComentario}
              disabled={enviando || !texto.trim() || !filmeId}
            >
              {enviando ? "Enviando..." : "Comentar"}
            </Button>
          </section>
        )}
      </div>
    </main>
  );
}
