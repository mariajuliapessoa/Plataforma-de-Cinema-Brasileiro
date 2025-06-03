import type { ComentarioComAutor } from "../../types";

export function montarArvoreComentarios(comentarios: ComentarioComAutor[]): ComentarioComAutor[] {
  const map = new Map<string, ComentarioComAutor>();
  const raizes: ComentarioComAutor[] = [];

  comentarios.forEach(c => {
    map.set(c.id, { ...c, respostas: [] });
  });

  comentarios.forEach(c => {
    if (c.comentarioPaiId) {
      const pai = map.get(c.comentarioPaiId);
      if (pai) {
        pai.respostas!.push(map.get(c.id)!);
      }
    } else {
      raizes.push(map.get(c.id)!);
    }
  });

  return raizes;
}
