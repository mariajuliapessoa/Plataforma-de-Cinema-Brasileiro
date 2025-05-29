"use server";

import { api } from "@/lib/api";
import { AvaliacaoType } from "@/schemas/avaliacao.schema";
import { cookies } from "next/headers";

export const pegarMinhasAvaliacoes = async (
  usuarioId: string
): Promise<AvaliacaoType[]> => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  const response = await api(token).get(`/avaliacoes/usuario/${usuarioId}`);

  return response?.data;
};
