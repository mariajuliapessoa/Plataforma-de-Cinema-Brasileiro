import { FilmeType } from "@/schemas/filme.schema";
import { pegarFilmes } from "../../actions";
import Image from "next/image";
import Link from "next/link";

export async function FilmesLista() {
  const filmes = await pegarFilmes();

  return (
    <div className="grid grid-cols-3 gap-4">
      {filmes?.content.map((filme: FilmeType) => (
        <FilmeCard key={filme.id} filme={filme} />
      ))}
    </div>
  );
}

const FilmeCard = ({ filme }: { filme: FilmeType }) => {
  return (
    <Link href={`/dashboard/${filme.id}`} className="flex flex-col gap-2 border p-4 rounded-md">
      <h2 className="text-lg font-bold truncate">{filme.titulo}</h2>
      <p className="truncate">{filme.sinopse}</p>
      <p>{filme.diretor}</p>
      <p>{filme.anoLancamento}</p>
      <p>{filme.generos.join(", ")}</p>
      <p>{filme.paisDeOrigem}</p>
      <p>{filme.avaliacao}</p>
      <div className="w-full h-64 relative">
        <Image alt={filme.titulo} src={filme.bannerUrl} fill className="rounded-md object-cover" />
      </div>
    </Link>
  );
};