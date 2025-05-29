import { FilmeType } from "@/schemas/filme.schema";
import Image from "next/image";
import Avaliar from "./avaliar";
import { AvaliacaoType } from "@/schemas/avaliacao.schema";

export default function Filme({ filme, avaliacoes }: { filme: FilmeType, avaliacoes: AvaliacaoType[] }) {
  return (
    <div className="flex flex-col gap-2">
      <div className="w-full h-64 relative rounded-md overflow-hidden">
        <Image alt={filme.titulo} src={filme.bannerUrl} fill className="rounded-md object-cover" />
      </div>
      <h1 className="text-xl font-bold">{filme.titulo}</h1>
      <p>{filme.sinopse}</p>
      <p>{filme.diretor}</p>
      <p>{filme.anoLancamento}</p>
      <p>{filme.generos.join(", ")}</p>
      <p>{filme.paisDeOrigem}</p>
      <p>{filme.avaliacao}</p>
      <Avaliar filmeId={filme.id} />
      <Avaliacoes avaliacoes={avaliacoes} />
    </div>
  );
}

const Avaliacoes = ({ avaliacoes }: { avaliacoes: AvaliacaoType[] }) => {
  return (
    <div className="flex flex-col gap-2">
      {avaliacoes?.length > 0 && avaliacoes.map((avaliacao) => (
        <Avaliacao key={avaliacao.id} avaliacao={avaliacao} />
      ))}
      {avaliacoes?.length === 0 && <p>Nenhuma avaliação encontrada</p>}
    </div>
  );
};

const Avaliacao = ({ avaliacao }: { avaliacao: AvaliacaoType }) => {
  return (
    <div key={avaliacao.id} className="border rounded-md p-2">
      <p>{avaliacao.texto}</p>
      <p>Nota: {avaliacao.nota}</p>
      <p>Data: {new Date(avaliacao.dataCriacao).toLocaleDateString()}</p>
    </div>
  );
};