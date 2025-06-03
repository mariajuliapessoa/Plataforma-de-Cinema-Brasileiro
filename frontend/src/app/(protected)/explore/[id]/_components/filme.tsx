import { FilmeType } from "@/schemas/filme.schema";
import Image from "next/image";
import Avaliar from "./avaliar";
import { AvaliacaoType } from "@/schemas/avaliacao.schema";
import { Star } from "lucide-react";

export default function Filme({ filme, avaliacoes }: { filme: FilmeType, avaliacoes: AvaliacaoType[] }) {
  return (
    <div className="flex flex-col w-full gap-10">
      {/* Banner */}
      <div className="relative w-full h-[400px] rounded-lg overflow-hidden shadow">
        <Image
          alt={filme.titulo}
          src={filme.bannerUrl}
          fill
          className="object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 to-transparent flex items-end p-6">
          <h1 className="text-3xl sm:text-4xl font-bold text-white drop-shadow-md">
            {filme.titulo}
          </h1>
        </div>
      </div>

      {/* Informações */}
      <section className="flex flex-col gap-6">
        {/* Sinopse */}
        <div className="flex flex-col gap-2">
          <h2 className="text-xl font-semibold">Sinopse</h2>
          <p className="text-muted-foreground">{filme.sinopse}</p>
        </div>

        {/* Meta infos */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          <Info label="Diretor" value={filme.diretor} />
          <Info label="Ano de Lançamento" value={filme.anoLancamento.toString()} />
          <Info label="Gêneros" value={filme.generos.join(", ")} />
          <Info label="Origem" value={filme.paisDeOrigem || "Brasil"} />
          <Info
            label="Avaliação do Público"
            value={
              <div className="flex items-center gap-1 font-semibold text-yellow-600">
                <Star className="w-4 h-4 fill-yellow-600" />
                {filme.avaliacao.toFixed(1)} / 10
              </div>
            }
          />
        </div>

        {/* Botão Avaliar */}
        <div className="pt-4">
          <Avaliar filmeId={filme.id} />
        </div>

        {/* Avaliações */}
        <div className="flex flex-col gap-4 mt-6">
          <h2 className="text-xl font-semibold">Avaliações</h2>
          <Avaliacoes avaliacoes={avaliacoes} />
        </div>
      </section>
    </div>
  );
}

const Info = ({ label, value }: { label: string; value: string | React.ReactNode }) => (
  <div className="flex flex-col">
    <span className="text-muted-foreground text-sm">{label}</span>
    <span className="font-semibold">{value}</span>
  </div>
);

const Avaliacoes = ({ avaliacoes }: { avaliacoes: AvaliacaoType[] }) => {
  return (
    <div className="flex flex-col gap-4">
      {avaliacoes.length === 0 && (
        <p className="text-muted-foreground italic">Nenhuma avaliação ainda.</p>
      )}
      {avaliacoes.map((avaliacao) => (
        <div
          key={avaliacao.id}
          className="bg-white border rounded-md p-4 shadow-sm hover:shadow transition"
        >
          <div className="flex items-start gap-3 mb-2">
            {/* Avatar com inicial */}
            <div className="w-10 h-10 bg-primary/20 text-primary flex items-center justify-center rounded-full font-bold uppercase">
              {avaliacao.autorNome.charAt(0)}
            </div>

            <div className="flex flex-col flex-1">
              <div className="flex items-center justify-between">
                <span className="font-medium">{avaliacao.autorNome}</span>
                <span className="text-sm text-muted-foreground">
                  {new Date(avaliacao.dataCriacao).toLocaleDateString()}
                </span>
              </div>

              <p className="text-sm text-muted-foreground italic mt-1">
                “{avaliacao.texto}”
              </p>

              <span className="text-sm font-semibold text-yellow-600 mt-1">
                Nota: {avaliacao.nota}/10
              </span>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};
