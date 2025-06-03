"use client";

import { useEffect, useState } from "react";
import { useAuth } from "@/hooks/use-auth";
import { useRouter } from "next/navigation";

import { getDebates } from "./debates/actions";
import { getMinhasAvaliacoes } from "./avaliacoes/actions";
import { getUserById } from "../../actions";
import { pegarFilme } from "../[id]/actions";

import { DebateResponse } from "@/schemas/debate.schema";
import { AvaliacaoType } from "@/schemas/avaliacao.schema";
import { FilmeType } from "@/schemas/filme.schema";

import Image from "next/image";
import Link from "next/link";
import { Loader, MessageCircle } from "lucide-react";
import { Button } from "@/components/ui/button";
import CriarDebate from "./debates/criar-debate";

export default function ComunidadePage() {
  const { user } = useAuth();
  const router = useRouter();

  const [tab, setTab] = useState<"debates" | "avaliacoes">("debates");
  const [debates, setDebates] = useState<DebateResponse[]>([]);
  const [avaliacoes, setAvaliacoes] = useState<(AvaliacaoType & { filme?: FilmeType })[]>([]);
  const [users, setUsers] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);

      if (tab === "debates") {
        const result = await getDebates();
        const userMap: Record<string, string> = {};

        await Promise.all(
          result.map(async (debate: DebateResponse) => {
            if (!userMap[debate.idCriador]) {
              const user = await getUserById(debate.idCriador);
              userMap[debate.idCriador] = user?.nome || "Desconhecido";
            }
          })
        );

        setDebates(result);
        setUsers(userMap);
      }

      if (tab === "avaliacoes" && user) {
        const minhas = await getMinhasAvaliacoes(user.id);
        const avaliacoesComFilmes = await Promise.all(
          minhas.map(async (a) => {
            const filme = await pegarFilme(a.filmeId);
            return { ...a, filme };
          })
        );
        setAvaliacoes(avaliacoesComFilmes);
      }

      setLoading(false);
    };

    fetchData();
  }, [tab, user]);

  return (
    <main className="min-h-screen w-full flex flex-col">
      {/* Banner */}
      <div className="relative w-full h-[400px] rounded-lg overflow-hidden shadow mb-10">
        <Image
          src="https://images.pexels.com/photos/7991375/pexels-photo-7991375.jpeg"
          alt="Comunidade"
          fill
          className="object-cover"
        />
        <div className="absolute inset-0 bg-black/60 flex items-end p-6">
          <h1 className="text-4xl sm:text-5xl font-bold text-white">Comunidade</h1>
        </div>
      </div>

      {/* Tabs */}
      <div className="max-w-5xl w-full px-4 mx-auto flex flex-col gap-6">
        <div className="flex justify-between items-center">
          <div className="flex gap-2">
            <Button
              variant={tab === "debates" ? "default" : "outline"}
              onClick={() => setTab("debates")}
            >
              Debates
            </Button>
            <Button
              variant={tab === "avaliacoes" ? "default" : "outline"}
              onClick={() => setTab("avaliacoes")}
            >
              Minhas Avaliações
            </Button>
          </div>
          {tab === "debates" && <CriarDebate />}
        </div>

        {/* Conteúdo */}
        {loading ? (
          <div className="flex justify-center py-12">
            <Loader className="w-8 h-8 animate-spin text-primary" />
          </div>
        ) : tab === "debates" ? (
          <div className="grid gap-4">
            {debates.map((debate) => (
              <Link
                key={debate.id}
                href={`/explore/comunidade/debates/${debate.id}/debate`}
                className="flex items-center justify-between p-4 rounded-lg border bg-white shadow-sm hover:shadow-md transition"
              >
                <div className="flex items-center gap-4">
                  <div className="w-10 h-10 bg-primary/20 text-primary flex items-center justify-center rounded-full font-bold uppercase">
                    {users[debate.idCriador]?.[0]?.toUpperCase() || "?"}
                  </div>
                  <div>
                    <p className="font-medium text-lg">{debate.titulo}</p>
                    <p className="text-sm text-muted-foreground">
                      Criado por: {users[debate.idCriador] || "Desconhecido"}
                    </p>
                  </div>
                </div>
                <MessageCircle className="text-muted-foreground" />
              </Link>
            ))}
            {debates.length === 0 && (
              <p className="text-muted-foreground text-center italic">Nenhum debate encontrado.</p>
            )}
          </div>
        ) : (
          <div className="grid gap-4">
            {avaliacoes.map((avaliacao) => (
              <div
                key={avaliacao.id}
                onClick={() => router.push(`/explore/${avaliacao.filme?.id}`)}
                className="bg-white border rounded-md p-4 shadow-sm hover:shadow transition cursor-pointer flex gap-4"
              >
                {/* Imagem do filme */}
                <div className="relative w-24 h-32 rounded overflow-hidden">
                  {avaliacao.filme?.bannerUrl && (
                    <Image
                      src={avaliacao.filme.bannerUrl}
                      alt={avaliacao.filme.titulo}
                      fill
                      className="object-cover"
                    />
                  )}
                </div>

                {/* Informações */}
                <div className="flex flex-col flex-1 justify-between">
                  <div className="flex flex-col gap-1">
                    <h3 className="text-lg font-semibold">{avaliacao.filme?.titulo}</h3>
                    <p className="text-sm text-muted-foreground italic">“{avaliacao.texto}”</p>
                  </div>
                  <div className="flex items-center justify-between text-sm text-muted-foreground mt-2">
                    <span>
                      Nota:{" "}
                      <strong className="text-yellow-600">{avaliacao.nota}/10</strong>
                    </span>
                    <span>{new Date(avaliacao.dataCriacao).toLocaleDateString()}</span>
                  </div>
                </div>
              </div>
            ))}
            {avaliacoes.length === 0 && (
              <p className="text-muted-foreground text-center italic">Nenhuma avaliação encontrada.</p>
            )}
          </div>
        )}
      </div>
    </main>
  );
}
