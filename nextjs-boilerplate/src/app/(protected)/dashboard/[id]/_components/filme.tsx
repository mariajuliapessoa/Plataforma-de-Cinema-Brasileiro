import { FilmeType } from "@/schemas/filme.schema";
import Image from "next/image";
import Avaliar from "./avaliar";

export default function Filme({ filme }: { filme: FilmeType }) {
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
    </div>
  );
}