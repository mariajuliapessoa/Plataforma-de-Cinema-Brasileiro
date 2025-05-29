import { z } from "zod";
import { RoleSchema } from "./role.schema";

export const UserSchema = z.object({
  id: z.string().uuid(),
  name: z.string(),
  email: z.string().email(),
  password: z.string(),
  status: z.enum(["ACTIVE"]),
  createdAt: z.string().or(z.date()),
  updatedAt: z.string().or(z.date()),
  accountId: z.string().nullable(),
  roleId: z.string(),
  role: RoleSchema,
});

export type UserType = z.infer<typeof UserSchema>;
