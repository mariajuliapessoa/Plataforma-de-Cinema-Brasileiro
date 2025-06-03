import { api } from "@/lib/api";
import { Auth } from "@/lib/auth";
import { ComentarioType } from "@/schemas/comentario.schema";
import { DebateResponse } from "@/schemas/debate.schema";

export const getDebates = async () => {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;
  const response = await api(token).get("/debates");

  return response.data;
};

export async function getComentariosPorDebate(debateId: string): Promise<ComentarioType[]> {
  const token = Auth.getTokenFromCookies(
    typeof document !== "undefined" ? document.cookie : null
  ) ?? undefined;

  try {
    const response = await api(token).get(`/comentarios`);
    const todos: ComentarioType[] = response.data;

    return todos.filter((c) => c.debateId === debateId);
  } catch (error) {
    console.error("Erro ao buscar comentários:", error);
    return [];
  }
}
export async function getDebateById(id: string): Promise<DebateResponse | null> {
  const token = Auth.getTokenFromCookies(
    typeof document !== "undefined" ? document.cookie : null
  ) ?? undefined;

  try {
    const response = await api(token).get(`/debates/${id}`);
    return response.data;
  } catch (error) {
    console.error("Erro ao buscar debate por ID:", error);
    return null;
  }
}

export async function criarComentario({
  texto,
  autorId,
  debateId,
  filmeId,
}: {
  texto: string;
  autorId: string;
  debateId: string;
  filmeId: string;
}) {

  const token = Auth.getTokenFromCookies(
    typeof document !== "undefined" ? document.cookie : null
  ) ?? undefined;


  return api(token).post("/comentarios", {
    texto,
    autorId,
    debateId,
    filmeId,
  });
}