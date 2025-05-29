import { z } from "zod";

export const AvaliacaoSchema = z.object({
  id: z.string(),
  texto: z.string(),
  nota: z.number(),
  autorNome: z.string(),
  filmeTitulo: z.string(),
  dataCriacao: z.string(),
});

export const AvaliacoesSchema = z.object({
  content: z.array(AvaliacaoSchema),
});

export const AvaliacaoCreateSchema = z.object({
  texto: z
    .string()
    .min(10, "O comentário deve ter pelo menos 10 caracteres")
    .max(500, "O comentário deve ter no máximo 500 caracteres"),
  nota: z
    .number()
    .min(1, "A nota deve ser pelo menos 1")
    .max(10, "A nota deve ser no máximo 10"),
  usuarioId: z.string().min(1, "ID do usuário é obrigatório"),
  filmeId: z.string().min(1, "ID do filme é obrigatório"),
});

export type AvaliacaoType = z.infer<typeof AvaliacaoSchema>;
export type AvaliacoesType = z.infer<typeof AvaliacoesSchema>;
export type AvaliacaoCreateType = z.infer<typeof AvaliacaoCreateSchema>;
