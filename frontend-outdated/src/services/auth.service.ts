import httpClient from "@/lib/api";
import { User } from "@/models/users";

interface RegisterRequest {
  nome: string;
  nomeUsuario: string;
  email: string;
  senha: string;
}

interface LoginRequest {
  nomeUsuario: string;
  senha: string;
}

interface LoginResponse {
  token: string;
}

export const authService = {
  async register(data: RegisterRequest): Promise<String> {
    try {
      const response = await httpClient.post("/api/auth/cadastrar", data);
      const dataR = response.data;
      return dataR;
    } catch (error: any) {
      throw new Error(
        "Erro ao fazer cadastro: " + (error.response?.data || error.message)
      );
    }
  },

  async login(data: LoginRequest): Promise<String> {
    try {
      const response = await httpClient.post("/api/auth/entrar", data);
      const token = response.data;

      localStorage.setItem("token", token);
      return token;
    } catch (error: any) {
      throw new Error(
        "Erro ao fazer login: " + (error.response?.data || error.message)
      );
    }
  },

  getToken(): string | null {
    return localStorage.getItem("token");
  },

  logout(): void {
    localStorage.removeItem("token");
  },
};

export default authService;
