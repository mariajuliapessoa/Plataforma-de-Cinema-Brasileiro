import { api } from "@/lib/api";
import { cookies } from "next/headers";

export const pegarFilmes = async () => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  const response = await api(token).get("/filmes?page=0&size=10");

  return response?.data;
};
