"use client";

import { AvaliacaoType } from "@/schemas/avaliacao.schema";
import { pegarMinhasAvaliacoes } from "../../actions";
import { useEffect, useState } from "react";
import { useAuth } from "@/hooks/use-auth";

export function AvaliacoesLista() {
  const { user } = useAuth();
  const [avaliacoes, setAvaliacoes] = useState<AvaliacaoType[]>([]);

  useEffect(() => {
    const fetchAvaliacoes = async () => {
      if (!user) return;
      const avaliacoes = await pegarMinhasAvaliacoes(user.id);
      setAvaliacoes(avaliacoes);
    };
    fetchAvaliacoes();
  }, [user]);

  return (
    <div className="grid grid-cols-3 gap-4">
      {avaliacoes?.map((avaliacao: AvaliacaoType) => (
        <AvaliacaoCard key={avaliacao.id} avaliacao={avaliacao} />
      ))}
      {avaliacoes?.length === 0 && <p>Nenhuma avaliação encontrada</p>}
    </div>
  );
}

const AvaliacaoCard = ({ avaliacao }: { avaliacao: AvaliacaoType }) => {
  return (
    <div className="flex flex-col gap-2 border p-4 rounded-md">
      <p>Comentário: {avaliacao.texto}</p>
      <p>Nota: {avaliacao.nota}</p>
      <p>Data: {new Date(avaliacao.dataCriacao).toLocaleDateString()}</p>
    </div>
  );
};