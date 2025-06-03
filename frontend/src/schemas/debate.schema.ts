import { z } from "zod";

export const DebateSchema = z.object({
  id: z.string(),
  titulo: z.string(),
  idCriador: z.string(),
});

export const DebateCreateSchema = z.object({
  titulo: z.string().min(3, "Título muito curto"),
  usuarioId: z.string(),
});

export type DebateCreateType = z.infer<typeof DebateCreateSchema>;

export interface DebateResponse {
  id: {
    value: string;
  };
  titulo: string;
  descricao?: string;
  criador: {
    value: string;
  };
  comentarios: unknown[];
}