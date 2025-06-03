import { api } from "@/lib/api";
import { Auth } from "@/lib/auth";


export async function getUserById(id: string) {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;

  try {
    const response = await api(token).get(`/usuarios/${id}`);
    return response.data;
  } catch (error) {
    console.error("Erro ao buscar usuário por ID", error);
    return null;
  }
}
