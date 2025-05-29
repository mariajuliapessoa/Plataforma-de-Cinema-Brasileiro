import { api, type ApiResponse } from "@/lib/api";
import { UserType } from "@/schemas/user.schema";

export async function getUsers(): Promise<ApiResponse<UserType[]>> {
  const users = await api.get("/users").catch((error) => {
    return {
      data: null,
      error: error.message,
    };
  });

  return users;
}
