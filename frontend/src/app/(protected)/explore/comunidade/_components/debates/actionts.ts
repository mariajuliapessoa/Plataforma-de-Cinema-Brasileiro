import { api } from "@/lib/api";
import { Auth } from "@/lib/auth";

export const getDebates = async () => {
  const token = Auth.getTokenFromCookies(typeof document !== "undefined" ? document.cookie : null) ?? undefined;
  const response = await api(token).get("/debates");

  return response.data;
};
