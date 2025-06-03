"use server";

import { api } from "@/lib/api";
import { DesafioType } from "@/schemas/desafio.schema";
import { cookies } from "next/headers";

export const pegarDesafio = async (id: string): Promise<DesafioType | null> => {
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;

  try {
    const response = await api(token).get(`/desafios/${id}`);
    return response?.data;
  } catch (error) {
    console.error("Erro ao buscar desafio:", error);
    return null;
  }
};

export const concluirDesafio = async (id: string): Promise<void> => {
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;

  try {
    await api(token).post(`/desafios/${id}/concluir`);
  } catch (error) {
    console.error("Erro ao concluir desafio:", error);
  }
};
