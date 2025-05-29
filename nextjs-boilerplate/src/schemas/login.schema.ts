import { z } from "zod";

export const LoginSchema = z.object({
  email: z.string().email(),
  password: z.string(),
});

export const LoginResponseSchema = z.object({
  token: z.string(),
});

export type LoginSchemaType = z.infer<typeof LoginSchema>;
export type LoginResponseSchemaType = z.infer<typeof LoginResponseSchema>;
