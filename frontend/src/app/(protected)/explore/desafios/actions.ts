"use server";

import { api } from "@/lib/api";
import { DesafioType } from "@/schemas/desafio.schema";
import { cookies } from "next/headers";

export const listarDesafiosDisponiveis = async (): Promise<DesafioType[]> => {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value;

    const response = await api(token).get("/desafios");
    return response.data;
};

export const listarDesafiosDoUsuario = async (usuarioId: string): Promise<DesafioType[]> => {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value;

    const response = await api(token).get(`/desafios/usuario/${usuarioId}`);
    return response.data;
};
