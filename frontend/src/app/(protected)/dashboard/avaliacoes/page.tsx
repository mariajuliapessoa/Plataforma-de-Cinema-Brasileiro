import { AvaliacoesLista } from "./_components/avaliacoes/avaliacoes-lista";

export default function Avaliacoes() {

  return (
    <main className="flex flex-col gap-4">
      <h1 className="text-2xl font-bold">Minhas Avaliações</h1>
      <AvaliacoesLista />
    </main>
  );
}
