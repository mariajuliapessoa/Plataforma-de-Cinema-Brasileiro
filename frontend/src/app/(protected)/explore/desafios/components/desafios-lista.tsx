"use client";

import { DesafioType } from "@/schemas/desafio.schema";

interface Props {
  desafios: DesafioType[];
}

export function DesafiosLista({ desafios }: Props) {
  if (desafios.length === 0) {
    return <p className="text-sm text-muted-foreground">Nenhum desafio disponível no momento.</p>;
  }

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      {desafios.map((desafio) => (
        <div
          key={desafio.id}
          className="rounded-xl border p-4 shadow hover:shadow-lg transition"
        >
          <h3 className="text-xl font-semibold mb-2">{desafio.titulo}</h3>
          <p className="text-sm text-muted-foreground mb-1">{desafio.descricao}</p>
          <p className="text-sm font-medium">Pontos: {desafio.pontos}</p>
          <p className="text-xs text-gray-500 mt-1">
            Prazo: {new Date(desafio.prazo).toLocaleDateString("pt-BR")}
          </p>
        </div>
      ))}
    </div>
  );
}
