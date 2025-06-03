import { api } from "@/lib/api";
import { Auth } from "@/lib/auth";
import { FilmeType } from "@/schemas/filme.schema";

interface PageResponse<T> {
  content: T[];
  totalPages: number;
  number: number;
}

export const getFilmes = async (
  params: { page: number; size?: number; query?: string }
): Promise<PageResponse<FilmeType>> => {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;

  const path = params.query?.trim() ? "/filmes/buscar" : "/filmes";

  const response = await api(token).get(path, {
    params: {
      page: params.page,
      size: params.size ?? 12,
      ...(params.query ? { titulo: params.query } : {})
    }
  });

  return response.data;
};
