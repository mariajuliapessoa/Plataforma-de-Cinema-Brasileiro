import { FilmeType } from "@/schemas/filme.schema";
import { pegarFilmes } from "../../actions";
import Image from "next/image";
import Link from "next/link";

export async function FilmesLista() {
  const filmes = await pegarFilmes();

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      {filmes?.content.map((filme: FilmeType) => (
        <FilmeCard key={filme.id} filme={filme} />
      ))}
    </div>
  );
}

const FilmeCard = ({ filme }: { filme: FilmeType }) => {
  return (
    <Link
      href={`/dashboard/${filme.id}`}
      className="flex flex-col border rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow bg-white"
    >
      <div className="relative w-full h-60">
        <Image
          alt={filme.titulo}
          src={filme.bannerUrl || "/fallback.jpg"}
          fill
          className="object-cover"
        />
      </div>

      <div className="p-4 flex flex-col gap-2">
        <h2 className="text-xl font-semibold truncate" title={filme.titulo}>
          {filme.titulo}
        </h2>
        <p className="text-sm text-gray-700 line-clamp-2" title={filme.sinopse}>
          {filme.sinopse}
        </p>

        <div className="text-sm text-gray-600 space-y-1 mt-2">
          <p><strong>Diretor:</strong> {filme.diretor}</p>
          <p><strong>Ano:</strong> {filme.anoLancamento}</p>
          <p><strong>Gêneros:</strong> {filme.generos.join(", ")}</p>
          <p><strong>Origem:</strong> {filme.paisDeOrigem}</p>
          <p><strong>Avaliação:</strong> {filme.avaliacao}</p>
        </div>
      </div>
    </Link>
  );
};
