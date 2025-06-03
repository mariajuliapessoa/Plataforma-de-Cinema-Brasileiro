"use client";

import { useEffect, useRef, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { getUserById } from "@/app/(protected)/actions";
import { getDebateById, getComentariosPorDebate, criarComentario } from "../../actions";
import { getFilmes, getFilmeById } from "@/app/(protected)/explore/actions";
import { useAuth } from "@/hooks/use-auth";
import { toast } from "sonner";
import { Loader, MessageCircle, Reply, Film, User, Calendar } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import Image from "next/image";
import type { FilmeType } from "@/schemas/filme.schema";
import { montarArvoreComentarios } from "./utils";
import type { ComentarioComAutor } from "../../types";

const ComentarioItem = ({
  comentario,
  onResponder,
  filmeTitles,
  depth = 0,
}: {
  comentario: ComentarioComAutor;
  onResponder: (parentId: string, texto: string, filmeId: string) => void;
  filmeTitles: Record<string, string>;
  depth?: number;
}) => {
  const [responderAtivo, setResponderAtivo] = useState(false);
  const [respostaTexto, setRespostaTexto] = useState("");

  const enviarResposta = () => {
    if (!respostaTexto.trim()) {
      toast.error("Por favor, escreva sua resposta.");
      return;
    }
    onResponder(comentario.id, respostaTexto, comentario.filmeId);
    setRespostaTexto("");
    setResponderAtivo(false);
  };

  const isNested = depth > 0;

  return (
    <Card className={`${isNested ? 'ml-6 mt-4 border-l-4 border-l-primary/30' : 'mt-4'} transition-all duration-200 hover:shadow-md`}>
      <CardHeader className="pb-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-primary rounded-full flex items-center justify-center">
              <User className="w-4 h-4 text-primary-foreground" />
            </div>
            <div>
              <p className="font-semibold text-foreground">{comentario.autorNome}</p>
              <div className="flex items-center gap-2 text-sm text-muted-foreground">
                <Calendar className="w-3 h-3" />
                <span>{new Date(comentario.dataCriacao).toLocaleString()}</span>
              </div>
            </div>
          </div>
          <Badge variant="secondary" className="flex items-center gap-1 text-xs">
            <Film className="w-3 h-3" />
            {filmeTitles[comentario.filmeId] ?? comentario.filmeId}
          </Badge>
        </div>
      </CardHeader>
      
      <CardContent className="pt-0">
        <p className="text-foreground leading-relaxed mb-4">{comentario.texto}</p>
        
        <Button
          variant="ghost"
          size="sm"
          onClick={() => setResponderAtivo(!responderAtivo)}
          className="p-0 h-auto font-medium"
        >
          <Reply className="w-4 h-4 mr-1" />
          {responderAtivo ? "Cancelar" : "Responder"}
        </Button>

        {responderAtivo && (
          <div className="mt-4 p-4 bg-muted rounded-lg space-y-3">
            <Textarea
              placeholder="Escreva sua resposta..."
              value={respostaTexto}
              onChange={(e) => setRespostaTexto(e.target.value)}
              className="resize-none"
            />
            <div className="flex gap-2">
              <Button onClick={enviarResposta} size="sm">
                Enviar resposta
              </Button>
              <Button 
                variant="outline" 
                size="sm"
                onClick={() => setResponderAtivo(false)}
              >
                Cancelar
              </Button>
            </div>
          </div>
        )}

        {comentario.respostas && comentario.respostas.length > 0 && (
          <div className="mt-4">
            {comentario.respostas.map((resp) => (
              <ComentarioItem
                key={resp.id}
                comentario={resp}
                onResponder={onResponder}
                filmeTitles={filmeTitles}
                depth={depth + 1}
              />
            ))}
          </div>
        )}
      </CardContent>
    </Card>
  );
};

export default function DebatePage() {
  const { user } = useAuth();
  const router = useRouter();
  const { id } = useParams();

  const [titulo, setTitulo] = useState("");
  const [criador, setCriador] = useState("Desconhecido");
  const [comentarios, setComentarios] = useState<ComentarioComAutor[]>([]);
  const [texto, setTexto] = useState("");
  const [filmeId, setFilmeId] = useState("");
  const [filmeSelecionado, setFilmeSelecionado] = useState<FilmeType | null>(null);

  const [query, setQuery] = useState("");
  const [sugestoes, setSugestoes] = useState<FilmeType[]>([]);
  const [carregandoSugestoes, setCarregandoSugestoes] = useState(false);

  const [loading, setLoading] = useState(true);
  const [enviando, setEnviando] = useState(false);

  const [filmeTitles, setFilmeTitles] = useState<Record<string, string>>({});

  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
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
      const filmeIdsUnicos = Array.from(new Set(todos.map((c) => c.filmeId)));

      const filmesFetch = await Promise.all(
        filmeIdsUnicos.map(async (fid) => {
          try {
            const filme = await getFilmeById(fid);
            return { id: fid, titulo: filme.titulo };
          } catch {
            return { id: fid, titulo: fid };
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

      const arvore = montarArvoreComentarios(enriquecidos);
      setComentarios(arvore);
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

  const enviarResposta = async (parentId: string, textoResposta: string, filmeRespostaId: string) => {
    if (!user) {
      toast.error("Usuário não autenticado.");
      return;
    }
    setEnviando(true);
    try {
      await criarComentario({
        texto: textoResposta,
        autorId: user.id,
        debateId: id as string,
        filmeId: filmeRespostaId,
        comentarioPaiId: parentId,
      });
      toast.success("Resposta enviada!");
      await fetchComentarios();
    } catch {
      toast.error("Erro ao enviar resposta.");
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
        <div className="text-center">
          <Loader className="w-12 h-12 animate-spin text-primary mx-auto mb-4" />
          <p className="text-muted-foreground">Carregando debate...</p>
        </div>
      </div>
    );
  }

  return (
    <main className="min-h-screen bg-background">
      {/* Hero Banner */}
      <div className="relative w-full aspect-[3/1] rounded-lg overflow-hidden mb-8 mx-4 sm:mx-6 max-w-7xl lg:mx-auto">
        <Image
          src="https://images.pexels.com/photos/7991259/pexels-photo-7991259.jpeg"
          alt="Banner de Debate"
          fill
          className="object-cover"
          priority
        />
        <div className="absolute inset-0 flex items-end p-6 lg:p-8">
          <div>
            <h1 className="text-3xl sm:text-4xl lg:text-5xl font-bold text-primary-foreground drop-shadow-lg mb-2">
              {titulo}
            </h1>
            <div className="flex items-center gap-2 text-primary-foreground/90">
              <User className="w-4 h-4" />
              <span className="text-lg">Criado por <strong>{criador}</strong></span>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto px-4 sm:px-6 space-y-8 pb-16">
        {/* Comments Section */}
        <Card className="border-0 shadow-lg">
          <CardHeader className="bg-primary text-primary-foreground rounded-t-lg">
            <CardTitle className="flex items-center gap-3 py-2">
              <MessageCircle className="w-6 h-6 mt-1" />
              Comentários ({comentarios.length})
            </CardTitle>
          </CardHeader>
          <CardContent className="p-6">
            {comentarios.length === 0 ? (
              <div className="text-center py-12">
                <MessageCircle className="w-16 h-16 text-muted-foreground mx-auto mb-4" />
                <p className="text-muted-foreground text-lg">Nenhum comentário ainda.</p>
                <p className="text-muted-foreground">Seja o primeiro a comentar neste debate!</p>
              </div>
            ) : (
              <div className="space-y-4">
                {comentarios.map((c) => (
                  <ComentarioItem 
                    key={c.id} 
                    comentario={c} 
                    onResponder={enviarResposta} 
                    filmeTitles={filmeTitles} 
                  />
                ))}
              </div>
            )}
          </CardContent>
        </Card>

        {/* New Comment Form */}
        {user && (
          <Card className="border-0 shadow-lg">
            <CardHeader className="bg-secondary text-secondary-foreground rounded-t-lg py-2">
              <CardTitle className="mt-2">Adicionar Comentário</CardTitle>
            </CardHeader>
            <CardContent className="p-6 space-y-6">
              {/* Movie Search */}
              <div ref={wrapperRef} className="relative">
                <label htmlFor="buscaFilme" className="block text-sm font-semibold text-foreground mb-2">
                  <Film className="inline w-4 h-4 mr-1" />
                  Selecionar Filme
                </label>
                <div className="relative">
                  <input
                    id="buscaFilme"
                    type="text"
                    value={filmeSelecionado?.titulo ?? query}
                    onChange={(e) => {
                      setFilmeId("");
                      setFilmeSelecionado(null);
                      setQuery(e.target.value);
                    }}
                    placeholder="Digite parte do título do filme..."
                    className="w-full p-4 border border-input rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-ring transition-all"
                    autoComplete="off"
                  />
                  {carregandoSugestoes && (
                    <div className="absolute right-4 top-1/2 transform -translate-y-1/2">
                      <Loader className="w-5 h-5 animate-spin text-primary" />
                    </div>
                  )}
                </div>

                {/* Movie Suggestions */}
                {sugestoes.length > 0 && (
                  <Card className="absolute z-10 w-full mt-1 max-h-64 overflow-auto shadow-xl border">
                    <CardContent className="p-0">
                      {sugestoes.map((f) => (
                        <div
                          key={f.id}
                          onClick={() => {
                            setFilmeId(f.id);
                            setFilmeSelecionado(f);
                            setSugestoes([]);
                          }}
                          className="px-4 py-3 hover:bg-accent cursor-pointer border-b border-border last:border-b-0 transition-colors"
                        >
                          <div className="font-medium text-foreground">{f.titulo}</div>
                          <div className="text-sm text-muted-foreground">
                            {f.anoLancamento} • {f.diretor}
                          </div>
                        </div>
                      ))}
                    </CardContent>
                  </Card>
                )}
              </div>

              {/* Selected Movie Display */}
              {filmeSelecionado && (
                <div className="p-4 bg-accent rounded-lg border">
                  <div className="flex items-center gap-2 text-accent-foreground">
                    <Film className="w-4 h-4" />
                    <span className="font-medium">Filme selecionado:</span>
                  </div>
                  <p className="text-accent-foreground font-semibold">{filmeSelecionado.titulo} ({filmeSelecionado.anoLancamento})</p>
                </div>
              )}

              {/* Comment Textarea */}
              <div>
                <label htmlFor="comentario" className="block text-sm font-semibold text-foreground mb-2">
                  Seu Comentário
                </label>
                <Textarea
                  id="comentario"
                  placeholder="Compartilhe sua opinião sobre este filme..."
                  value={texto}
                  onChange={(e) => setTexto(e.target.value)}
                  className="resize-none min-h-[120px]"
                />
              </div>

              {/* Submit Button */}
              <Button
                onClick={enviarComentario}
                disabled={enviando || !texto.trim() || !filmeId}
                className="w-full"
              >
                {enviando ? (
                  <>
                    <Loader className="w-4 h-4 animate-spin mr-2" />
                    Enviando...
                  </>
                ) : (
                  <>
                    <MessageCircle className="w-4 h-4 mr-2" />
                    Publicar Comentário
                  </>
                )}
              </Button>
            </CardContent>
          </Card>
        )}
      </div>
    </main>
  );
}
