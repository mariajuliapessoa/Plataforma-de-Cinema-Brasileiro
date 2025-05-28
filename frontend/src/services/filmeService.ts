// src/services/filmeService.ts
import httpClient from './httpClient';

export const importarFilmesBrasileiros = async () => {
  const response = await httpClient.post('/api/filmes/importar');
  return response.data;
};
