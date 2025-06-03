"use client";

import { DesafioType } from "@/schemas/desafio.schema";
import { Button } from "@/components/ui/button";
import { CheckCircle, Clock } from "lucide-react";
import { useTransition } from "react";
import { concluirDesafio } from "../actions";

interface Props {
  desafio: DesafioType;
}

export function Desafio({ desafio }: Props) {
  const [isPending, startTransition] = useTransition();

  const statusColor = desafio.concluido ? "text-green-600" : "text-yellow-600";
  const statusIcon = desafio.concluido ? (
    <CheckCircle className="w-5 h-5" />
  ) : (
    <Clock className="w-5 h-5" />
  );

  const handleConcluir = () => {
    startTransition(() => {
      concluirDesafio(desafio.id);
    });
  };

  return (
    <div className="flex flex-col gap-6">
      <h1 className="text-3xl font-bold">{desafio.titulo}</h1>
      <p className="text-muted-foreground text-base leading-relaxed">
        {desafio.descricao}
      </p>

      <div className="flex flex-wrap gap-4 text-sm text-muted-foreground">
        <span>
          <strong>Pontos:</strong> {desafio.pontos}
        </span>
        <span>
          <strong>Prazo:</strong>{" "}
          {new Date(desafio.prazo).toLocaleDateString("pt-BR")}
        </span>
        <span className={`flex items-center gap-1 ${statusColor}`}>
          <strong>Status:</strong> {statusIcon}{" "}
          {desafio.concluido ? "Concluído" : "Pendente"}
        </span>
      </div>

      {!desafio.concluido && (
        <Button
          variant="default"
          className="w-fit"
          onClick={handleConcluir}
          disabled={isPending}
        >
          {isPending ? "Concluindo..." : "Concluir desafio"}
        </Button>
      )}
    </div>
  );
}
