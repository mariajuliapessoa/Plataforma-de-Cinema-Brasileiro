import { pegarDesafio } from "./actions";
import { Desafio } from "./components/desafio";

interface Params {
    params: { id: string };
}

export default async function DesafioPage({ params }: Params) {
    const desafio = await pegarDesafio(params.id);

    if (!desafio) {
        return <p className="p-6 text-muted-foreground">Desafio não encontrado.</p>;
    }

    return (
        <main className="w-full max-w-6xl mx-auto px-4 py-10 min-h-screen flex flex-col gap-6">
            <Desafio desafio={desafio} />
        </main>
    );
}