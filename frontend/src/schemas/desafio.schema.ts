import { z } from "zod";

export const DesafioSchema = z.object({
  id: z.string().uuid(),
  titulo: z.string(),
  descricao: z.string(),
  pontos: z.number(),
  destinatarioId: z.string().uuid(),
  concluido: z.boolean(),
  prazo: z.string().datetime(),
  dataCriacao: z.string().datetime(),
});

export type DesafioType = z.infer<typeof DesafioSchema>;