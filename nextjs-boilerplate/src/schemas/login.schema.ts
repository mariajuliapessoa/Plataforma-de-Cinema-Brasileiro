import { z } from "zod";

export const LoginSchema = z.object({
  nomeUsuario: z.string(),
  senha: z.string(),
});

export const LoginResponseSchema = z.string();

export type LoginSchemaType = z.infer<typeof LoginSchema>;
export type LoginResponseSchemaType = z.infer<typeof LoginResponseSchema>;
