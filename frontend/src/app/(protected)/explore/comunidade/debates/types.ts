export interface ComentarioComAutor {
    id: string;
    texto: string;
    autorId: string;
    filmeId: string;
    debateId?: string;
    comentarioPaiId?: string | null;
    dataCriacao: string;
    autorNome: string;
    respostas?: ComentarioComAutor[];
  }