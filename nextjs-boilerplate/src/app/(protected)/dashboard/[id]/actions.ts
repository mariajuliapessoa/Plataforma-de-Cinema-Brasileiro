"use server";

import { api } from "@/lib/api";
import { AvaliacaoCreateType, AvaliacaoType } from "@/schemas/avaliacao.schema";
import { FilmeType } from "@/schemas/filme.schema";
import { cookies } from "next/headers";

export const pegarFilme = async (id: string): Promise<FilmeType> => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  const response = await api(token).get(`/filmes/${id}`);

  return response?.data;
};

export const pegarAvaliacoes = async (id: string): Promise<AvaliacaoType[]> => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  const response = await api(token).get(`/avaliacoes/${id}`);

  return response?.data;
};

export const avaliarFilme = async (data: AvaliacaoCreateType) => {
  const cookie = await cookies();
  const token = cookie.get("token")?.value;

  const response = await api(token).post(`/avaliacoes`, data);

  console.log(response);

  return response?.data;
};
