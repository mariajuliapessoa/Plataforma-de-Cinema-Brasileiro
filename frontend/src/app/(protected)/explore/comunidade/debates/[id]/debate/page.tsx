"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { getUserById } from "@/app/(protected)/actions";
import { getDebateById, getComentariosPorDebate, criarComentario } from "../../actions";
import { useAuth } from "@/hooks/use-auth";
import { toast } from "sonner";
import { Loader } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import Image from "next/image";

interface ComentarioComAutor {
    id: string;
    texto: string;
    autorId: string;
    filmeId: string;
    debateId?: string;
    dataCriacao: string;
    autorNome: string;
}

export default function DebatePage() {
    const { user } = useAuth();
    const router = useRouter();
    const { id } = useParams();

    const [titulo, setTitulo] = useState("");
    const [criador, setCriador] = useState("Desconhecido");
    const [comentarios, setComentarios] = useState<ComentarioComAutor[]>([]);
    const [texto, setTexto] = useState("");
    const [loading, setLoading] = useState(true);
    const [enviando, setEnviando] = useState(false);

    const fetchDebate = async () => {
        try {
            const debate = await getDebateById(id as string);
            if (!debate) throw new Error("Debate não encontrado");
            setTitulo(debate.titulo);
            console.log(debate);
            const criadorData = await getUserById(debate.idCriador);
            setCriador(criadorData?.nome ?? "Desconhecido");
        } catch (e) {
            toast.error("Erro ao carregar o debate.");
            router.push("/explore/comunidade");
        }
    };

    const fetchComentarios = async () => {
        const todos = await getComentariosPorDebate(id as string);
        const enriquecidos = await Promise.all(
            todos.map(async (c) => {
                const autor = await getUserById(c.autorId);
                return {
                    ...c,
                    autorNome: autor?.nome ?? "Desconhecido",
                };
            })
        );
        setComentarios(enriquecidos);
    };

    const enviarComentario = async () => {
        if (!texto.trim()) return;
        setEnviando(true);
        try {
            if (!user) {
                toast.error("Usuário não autenticado.");
                setEnviando(false);
                return;
            }

            await criarComentario({ texto, autorId: user.id, debateId: id as string });
            setTexto("");
            await fetchComentarios();
            toast.success("Comentário enviado!");
        } catch {
            toast.error("Erro ao comentar.");
        } finally {
            setEnviando(false);
        }
    };

    useEffect(() => {
        (async () => {
            setLoading(true);
            await fetchDebate();
            await fetchComentarios();
            setLoading(false);
        })();
    }, [id]);

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <Loader className="w-10 h-10 animate-spin text-primary" />
            </div>
        );
    }

    return (
        <main className="min-h-screen">
            {/* Banner com imagem temática */}
            <div className="relative w-full aspect-[3/1] rounded-lg overflow-hidden mb-6">
                <Image
                    src="https://images.pexels.com/photos/7991259/pexels-photo-7991259.jpeg"
                    alt="Banner de Debate"
                    fill
                    className="object-cover brightness-75"
                    priority
                />
                <div className="absolute inset-0 flex items-end p-6">
                    <h1 className="text-3xl sm:text-4xl font-bold text-white drop-shadow-lg">
                        Debate: {titulo}
                    </h1>
                </div>
            </div>

            {/* Conteúdo principal */}
            <div className="max-w-3xl mx-auto px-4 sm:px-6 space-y-8 pb-16">
                <div className="text-sm text-muted-foreground">
                    Criado por: <strong>{criador}</strong>
                </div>

                <section className="space-y-4">
                    <h2 className="text-xl font-semibold">Comentários ({comentarios.length})</h2>

                    {comentarios.length === 0 ? (
                        <p className="text-muted-foreground italic">Nenhum comentário ainda.</p>
                    ) : (
                        comentarios.map((c) => (
                            <div key={c.id} className="bg-white rounded-md shadow p-4">
                                <div className="flex justify-between text-sm text-muted-foreground">
                                    <span className="font-medium">{c.autorNome}</span>
                                    <span>{new Date(c.dataCriacao).toLocaleString()}</span>
                                </div>
                                <p className="mt-2 text-gray-800">{c.texto}</p>
                            </div>
                        ))
                    )}
                </section>

                {user && (
                    <section className="space-y-2">
                        <Textarea
                            placeholder="Escreva seu comentário..."
                            value={texto}
                            onChange={(e) => setTexto(e.target.value)}
                            className="resize-none"
                        />
                        <Button onClick={enviarComentario} disabled={enviando || !texto.trim()}>
                            {enviando ? "Enviando..." : "Comentar"}
                        </Button>
                    </section>
                )}
            </div>
        </main>
    );
}
