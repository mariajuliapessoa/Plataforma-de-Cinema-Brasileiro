import axios from "axios";

export type ApiResponse<T> = {
  data: T | null;
  error?: string;
};

export const api = (token?: string) => {
  return axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL + "/api",
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  });
};

export const authApi = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL + "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

authApi.interceptors.request.use((config) => {
  const getCookie = (name: string) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop()?.split(";").shift();
    return null;
  };

  const token = getCookie("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});
