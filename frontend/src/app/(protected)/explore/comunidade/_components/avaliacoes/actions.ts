"use server";

import { cookies } from "next/headers";
import { api } from "@/lib/api";
import { AvaliacaoType } from "@/schemas/avaliacao.schema";

export const getMinhasAvaliacoes = async (
  usuarioId: string
): Promise<AvaliacaoType[]> => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  try {
    const response = await api(token).get(`/avaliacoes/usuario/${usuarioId}`);

    if (!response?.data || !Array.isArray(response.data)) {
      return [];
    }

    return response.data;
  } catch (error: any) {
    if (error?.response?.status === 204) {
      return [];
    }

    console.error("Erro ao buscar avaliações do usuário:", error);
    return [];
  }
};
