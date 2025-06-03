// src/app/(protected)/explore/desafios/page.tsx

"use client";

import { useEffect, useState } from "react";
import { useAuth } from "@/hooks/use-auth";
import { useRouter } from "next/navigation";
import { listarDesafiosDisponiveis } from "./actions";
import { DesafioType } from "@/schemas/desafio.schema";
import { Loader } from "lucide-react";
import Link from "next/link";

export default function DesafiosPage() {
  const { user } = useAuth();
  const router = useRouter();

  const [desafios, setDesafios] = useState<DesafioType[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);

      const result = await listarDesafiosDisponiveis();
      setDesafios(result);
      setLoading(false);
    };

    fetchData();
  }, []);

  return (
    <main className="w-full max-w-6xl mx-auto px-4 py-10 min-h-screen flex flex-col gap-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-3xl font-bold">Desafios</h1>
        <p className="text-muted-foreground">
          Participe de desafios sobre o cinema brasileiro e ganhe pontos!
        </p>
      </div>

      {loading ? (
        <div className="flex justify-center py-12">
          <Loader className="w-8 h-8 animate-spin text-primary" />
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-8">
          {desafios.map((desafio) => (
            <Link
              key={desafio.id}
              href={`/explore/desafios/${desafio.id}`}
              className="group rounded-lg overflow-hidden border shadow hover:shadow-lg transition-all duration-300 bg-white"
            >
              <div className="p-4 flex flex-col gap-2">
                <h2 className="text-xl font-bold truncate" title={desafio.titulo}>
                  {desafio.titulo}
                </h2>
                <p className="text-sm text-muted-foreground line-clamp-2" title={desafio.descricao}>
                  {desafio.descricao}
                </p>
                <p className="text-xs text-muted-foreground">
                  Pontos: <strong>{desafio.pontos}</strong>
                </p>
              </div>
            </Link>
          ))}
          {desafios.length === 0 && (
            <p className="text-muted-foreground text-center italic col-span-full">
              Nenhum desafio disponível no momento.
            </p>
          )}
        </div>
      )}
    </main>
  );
}
