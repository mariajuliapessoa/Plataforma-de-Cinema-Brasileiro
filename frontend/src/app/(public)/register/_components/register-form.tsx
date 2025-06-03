"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterSchema, RegisterSchemaType } from "@/schemas/register.schema";
import { toast } from "sonner";
import { useMutation } from "@tanstack/react-query";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { config } from "@/config";
import Logo from "@/components/style/logo";
import Link from "next/link";
import { Loader } from "lucide-react";
import { useRouter } from "next/navigation";
import { Auth } from "@/lib/auth";
import { AnimatedGroup } from "@/components/motion-primitives/animated-group";

export default function RegisterForm() {
    const router = useRouter();

    const form = useForm<RegisterSchemaType>({
        resolver: zodResolver(RegisterSchema),
        defaultValues: {
            nome: "",
            nomeUsuario: "",
            email: "",
            cargo: "USER",
            senha: ""
        }
    });

    const { mutateAsync, isPending } = useMutation({
        mutationFn: async (data: RegisterSchemaType) => {
            return await Auth.register(data);
        },
        onSuccess: (data) => {
            toast.success(data);
            router.push("/login");
        },
        onError: (error: any) => {
            const message = error?.response?.data || "Erro ao cadastrar";
            toast.error(message);
        }
    });

    const onSubmit = (data: RegisterSchemaType) => {
        mutateAsync(data);
    };

    return (
        <section className="w-full flex items-center justify-center py-20">
            <AnimatedGroup preset="slide" className="w-full xl:max-w-md flex flex-col gap-8">
                <RegisterHeader />
                <form
                    className="flex flex-col gap-4"
                    onSubmit={form.handleSubmit(onSubmit, (errors) => {
                        Object.values(errors).forEach((error) => {
                            if (error?.message) {
                                toast.error(error.message);
                            }
                        });
                    })}
                >
                    <Input type="text" placeholder="Nome" {...form.register("nome")} />
                    <Input type="text" placeholder="Nome de usuário" {...form.register("nomeUsuario")} />
                    <Input type="email" placeholder="Email" {...form.register("email")} />
                    <Input type="password" placeholder="Senha" {...form.register("senha")} />
                    <Button size="lg" type="submit" disabled={isPending}>
                        {isPending ? <Loader className="w-4 h-4 animate-spin" /> : "Cadastrar"}
                    </Button>
                </form>
                <RegisterFooter />
            </AnimatedGroup>
        </section>
    );
}

const RegisterHeader = () => (
    <div className="flex flex-col gap-4">
        <Logo />
        <div>
            <h1 className="text-3xl font-bold">{config.public.register?.title || "Crie sua conta"}</h1>
            <p className="text-sm text-muted-foreground">{config.public.register?.description || "Explore o melhor do cinema brasileiro"}</p>
        </div>
    </div>
);

const RegisterFooter = () => (
    <div className="flex flex-col gap-4">
        <div className="flex items-center gap-2">
            <div className="w-full h-[1px] bg-muted-foreground/20" />
            <p className="text-sm text-muted-foreground">Ou</p>
            <div className="w-full h-[1px] bg-muted-foreground/20" />
        </div>
        <p className="text-sm text-muted-foreground text-center">
            Já tem uma conta? <Link href="/login" className="text-primary">Entrar</Link>
        </p>
    </div>
);
