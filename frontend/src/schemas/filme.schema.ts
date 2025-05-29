import { z } from "zod";

export const FilmeSchema = z.object({
  id: z.string(),
  titulo: z.string(),
  sinopse: z.string(),
  diretor: z.string(),
  anoLancamento: z.number(),
  generos: z.array(z.string()),
  paisDeOrigem: z.string(),
  avaliacao: z.number(),
  bannerUrl: z.string(),
});

export const FilmesSchema = z.object({
  content: z.array(FilmeSchema),
});

export type FilmeType = z.infer<typeof FilmeSchema>;
export type FilmesType = z.infer<typeof FilmesSchema>;
