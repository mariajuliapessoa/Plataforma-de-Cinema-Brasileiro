import { z } from "zod";

export const UserSchema = z.object({
  id: z.string().uuid(),
  nome: z.string(),
  nomeUsuario: z.string(),
  email: z.string().email(),
  cargo: z.enum(["ADMIN", "USER"]),
  pontos: z.number(),
});

export type UserType = z.infer<typeof UserSchema>;
