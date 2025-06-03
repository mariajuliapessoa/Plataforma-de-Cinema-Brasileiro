import { z } from "zod";

export const RegisterSchema = z.object({
    nome: z.string().min(1, "Informe seu nome"),
    nomeUsuario: z.string().min(3, "Nome de usuário muito curto"),
    email: z.string().email("Email inválido"),
    cargo: z.literal("USER"),
    senha: z.string().min(6, "Senha muito curta"),
  });

export type RegisterSchemaType = z.infer<typeof RegisterSchema>;