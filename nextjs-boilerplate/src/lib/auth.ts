import { api, authApi } from "./api";

export class Auth {
  static async login(email: string, password: string) {
    const response = await api.post("/auth/login", {
      email,
      password,
    });

    if (response.status === 201) {
      document.cookie = `token=${response.data.data.token}; path=/; max-age=2592000; SameSite=Strict; Secure`;
    }

    return response.data;
  }

  static async register(params: {
    email: string;
    password: string;
    name: string;
  }) {
    const response = await api.post("/auth/register", params);

    return response.data;
  }

  static async logout() {
    document.cookie =
      "token=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT; SameSite=Strict; Secure";
  }

  static async getUser() {
    const response = await authApi.get("/me");

    if (response.status === 200) {
      return response.data.data;
    }

    return null;
  }

  static async getSession(token: string) {
    const response = await api.get("/me", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200) {
      return response.data.data;
    }

    return null;
  }

  static getTokenFromCookies(requestCookies: string | null) {
    if (!requestCookies) return null;

    const tokenMatch = requestCookies.match(/token=([^;]*)/);
    return tokenMatch ? tokenMatch[1] : null;
  }
}
