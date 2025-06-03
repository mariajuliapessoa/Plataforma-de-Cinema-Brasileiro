"use client";

import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { getDebateById } from "../../explore/comunidade/_components/debates/actionts";
import { getComentariosPorDebate } from "../../explore/comunidade/_components/debates/comment";
import { getUserById } from "../../actions";
import { DebateResponse } from "@/schemas/debate.schema";
import { ComentarioType } from "@/schemas/comentario.schema";
import { Card, CardHeader, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Loader } from "lucide-react";

interface ComentarioComAutor extends ComentarioType {
  autorNome: string;
}

export default function DebatePage() {
  const params = useParams();
  const id = params.id as string;

  const [debate, setDebate] = useState<DebateResponse | null>(null);
  const [nomeCriador, setNomeCriador] = useState<string>("Desconhecido");
  const [comentarios, setComentarios] = useState<ComentarioComAutor[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    if (!id) {
      setErro("ID do debate não foi fornecido na URL.");
      setLoading(false);
      return;
    }

    async function fetchDados() {
      try {
        setLoading(true);
        const dataDebate = await getDebateById(id);
        if (!dataDebate) throw new Error("Debate não encontrado.");
        setDebate(dataDebate);

        const idCriador = dataDebate.criador.value;
        if (idCriador) {
          const criador = await getUserById(idCriador);
          setNomeCriador(criador?.nome ?? "Desconhecido");
        }

        const rawComentarios = await getComentariosPorDebate(id);
        const comentariosComAutor: ComentarioComAutor[] = await Promise.all(
          rawComentarios.map(async (c) => {
            let autorNome = "Desconhecido";
            if (c.autorId) {
              const autor = await getUserById(c.autorId);
              autorNome = autor?.nome ?? "Desconhecido";
            }
            return { ...c, autorNome };
          })
        );

        setComentarios(comentariosComAutor);
      } catch (e: any) {
        const msgAPI = e.response?.data?.message;
        setErro(msgAPI || e.message || "Erro ao carregar o debate.");
      } finally {
        setLoading(false);
      }
    }

    fetchDados();
  }, [id]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Loader className="w-10 h-10 animate-spin text-primary" />
      </div>
    );
  }

  if (erro) {
    return (
      <main className="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4">
        <div className="max-w-lg w-full text-center space-y-6">
          <p className="text-red-600 font-semibold">{erro}</p>
          <Link href="/explore/comunidade">
            <Button variant="outline">← Voltar à Comunidade</Button>
          </Link>
        </div>
      </main>
    );
  }

  if (!debate) {
    return (
      <main className="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4">
        <div className="max-w-lg w-full text-center space-y-6">
          <p className="text-red-600 font-semibold">Debate não encontrado.</p>
          <Link href="/explore/comunidade">
            <Button variant="outline">← Voltar à Comunidade</Button>
          </Link>
        </div>
      </main>
    );
  }

  const inicial = nomeCriador.charAt(0).toUpperCase() || "?";

  return (
    <main className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-3xl mx-auto space-y-6">
        <div>
          <Link href="/explore/comunidade">
            <Button variant="ghost" className="text-sm">
              ← Voltar à Comunidade
            </Button>
          </Link>
        </div>

        <Card className="bg-white shadow-lg">
          <CardHeader className="flex flex-col gap-2 p-6">
            <h1 className="text-2xl sm:text-3xl font-extrabold leading-tight">
              {debate.titulo}
            </h1>
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-primary/20 text-primary flex items-center justify-center rounded-full text-lg font-semibold uppercase">
                {inicial}
              </div>
              <span className="text-sm text-muted-foreground">
                Criado por: <strong className="text-gray-700">{nomeCriador}</strong>
              </span>
            </div>
          </CardHeader>

          <CardContent className="px-6 pb-6 pt-0 space-y-8">
            <div className="space-y-4">
              <h2 className="text-xl font-semibold">Comentários ({comentarios.length})</h2>
              {comentarios.length === 0 ? (
                <p className="text-gray-500 italic">Nenhum comentário ainda.</p>
              ) : (
                comentarios.map((c) => (
                  <div
                    key={c.id}
                    className="border rounded-lg p-4 bg-white shadow-sm"
                  >
                    <div className="flex items-center justify-between">
                      <div className="text-sm font-medium text-gray-700">
                        {c.autorNome}
                      </div>
                      <div className="text-xs text-gray-500">
                        {new Date(c.dataCriacao).toLocaleString()}
                      </div>
                    </div>
                    <p className="mt-2 text-gray-800">{c.texto}</p>
                  </div>
                ))
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </main>
  );
}