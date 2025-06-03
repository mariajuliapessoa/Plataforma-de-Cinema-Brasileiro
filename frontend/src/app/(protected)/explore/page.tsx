"use client";

import { useState } from "react";
import { SearchBar } from "./_components/filmes/search-bar";
import { FilmesLista } from "./_components/filmes/filmes-lista";

export default function Explore() {
  const [query, setQuery] = useState("");

  return (
    <main className="flex flex-col gap-10 px-4 xl:px-0 w-full">
      <div className="pt-6">
        <SearchBar onSearch={setQuery} />
      </div>

      <section className="flex flex-col gap-6">
        <h2 className="text-2xl font-bold">
          {query ? `Resultados para "${query}"` : "Catálogo de Filmes"}
        </h2>
        <FilmesLista query={query} />
      </section>
    </main>
  );
}
