import Filme from "./_components/filme";
import { pegarFilme } from "./actions";

export default async function FilmePage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;

  const filme = await pegarFilme(id);

  return (
    <main className="flex flex-col gap-4 w-full">
      <Filme filme={filme} />
    </main>
  );
}