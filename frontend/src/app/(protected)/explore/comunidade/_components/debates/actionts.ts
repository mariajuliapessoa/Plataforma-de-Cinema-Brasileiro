import { api } from "@/lib/api";
import { Auth } from "@/lib/auth";
import { DebateResponse } from "@/schemas/debate.schema";

export const getDebates = async () => {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;
  const response = await api(token).get("/debates");

  return response.data;
};

export async function getDebateById(id: string): Promise<DebateResponse> {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;
  const response = await api(token).get<DebateResponse>(`/debates/${id}`);
  
  return response.data;
};
