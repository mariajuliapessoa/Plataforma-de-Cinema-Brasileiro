import { FilmesLista } from "./_components/filmes/filmes-lista";
import { SearchBar } from "./_components/filmes/search-bar";

export default function Dashboard() {
  return (
    <main className="flex flex-col gap-4">
      <h1 className="text-2xl font-bold">Dashboard</h1>
      <SearchBar />
      <FilmesLista />
    </main>
  );
}
