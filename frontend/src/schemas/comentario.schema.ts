import { z } from "zod";

export const ComentarioSchema = z.object({
  id: z.string(),
  texto: z.string(),
  autorId: z.string(),
  filmeId: z.string(),
  debateId: z.string().optional(),
  dataCriacao: z.string().datetime(),
});

export type ComentarioType = z.infer<typeof ComentarioSchema>;
