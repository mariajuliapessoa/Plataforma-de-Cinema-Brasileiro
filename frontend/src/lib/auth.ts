import { api } from "./api";

export class Auth {
  static async login(nomeUsuario: string, senha: string) {
    const response = await api().post("/auth/entrar", {
      nomeUsuario,
      senha,
    });

    if (response.status === 200) {
      document.cookie = `token=${response.data}; path=/; max-age=2592000; SameSite=Strict; Secure`;
    }

    return response.data;
  }

  static async register(params: {
    email: string;
    password: string;
    name: string;
  }) {
    const response = await api().post("/auth/registrar", params);

    return response.data;
  }

  static async getSession(token?: string) {
    const response = await api(token).get("/usuarios/me");

    if (response.status === 200) {
      return response.data;
    }

    return null;
  }

  static getTokenFromCookies(requestCookies: string | null) {
    if (!requestCookies) return null;

    const tokenMatch = requestCookies.match(/token=([^;]*)/);
    return tokenMatch ? tokenMatch[1] : null;
  }
}
