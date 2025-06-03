"use client";

import { FilmeType } from "@/schemas/filme.schema";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Loader } from "lucide-react";
import { getFilmes } from "../../actions";

interface PageResponse<T> {
  content: T[];
  totalPages: number;
  number: number;
}

export function FilmesLista({ query }: { query: string }) {
  const [filmes, setFilmes] = useState<FilmeType[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [isLoading, setIsLoading] = useState(false);

  const fetchFilmes = async () => {
    setIsLoading(true);
    const data: PageResponse<FilmeType> = await getFilmes({ page, query });
    setFilmes(data.content);
    setTotalPages(data.totalPages);
    setIsLoading(false);
  };

  useEffect(() => {
    setPage(0); // reset page when query changes
  }, [query]);

  useEffect(() => {
    fetchFilmes();
  }, [page, query]);

  return (
    <div className="flex flex-col gap-8 min-h-screen">
      {isLoading ? (
        <div className="flex flex-1 items-center justify-center py-20">
          <Loader className="w-8 h-8 animate-spin text-primary" />
        </div>
      ) : (
        <>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-8">
            {filmes.map((filme, index) => (
              <FilmeCard key={`${filme.id}-${index}`} filme={filme} />
            ))}
          </div>

          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
        </>
      )}
    </div>
  );
}

const FilmeCard = ({ filme }: { filme: FilmeType }) => {
  return (
    <Link
      href={`/explore/${filme.id}`}
      className="group rounded-lg overflow-hidden border shadow hover:shadow-lg transition-all duration-300 bg-white"
    >
      <div className="relative w-full h-72">
        <Image
          alt={filme.titulo}
          src={filme.bannerUrl || "/fallback.jpg"}
          fill
          className="object-cover group-hover:scale-105 transition-transform duration-300"
        />
      </div>
      <div className="p-4 flex flex-col gap-2">
        <h2 className="text-xl font-bold truncate" title={filme.titulo}>
          {filme.titulo}
        </h2>
        <p className="text-sm text-muted-foreground line-clamp-2" title={filme.sinopse}>
          {filme.sinopse}
        </p>
      </div>
    </Link>
  );
};

const Pagination = ({
  currentPage,
  totalPages,
  onPageChange
}: {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}) => {
  if (totalPages <= 1) return null;

  return (
    <div className="flex gap-2 justify-center flex-wrap">
      {Array.from({ length: totalPages }).map((_, idx) => (
        <Button
          key={idx}
          variant={idx === currentPage ? "default" : "outline"}
          onClick={() => onPageChange(idx)}
        >
          {idx + 1}
        </Button>
      ))}
    </div>
  );
};
