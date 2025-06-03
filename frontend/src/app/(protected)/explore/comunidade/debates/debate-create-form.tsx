"use client";

import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useMutation } from "@tanstack/react-query";
import { AnimatedGroup } from "@/components/motion-primitives/animated-group";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { createDebate } from "../actions";

const DebateFormSchema = z.object({
  titulo: z.string().min(3, "Título muito curto"),
  usuarioId: z.string()
});

type DebateFormType = z.infer<typeof DebateFormSchema>;

export default function DebateCreateForm({ userId, setOpen }: { userId: string; setOpen: (open: boolean) => void }) {
  const form = useForm<DebateFormType>({
    resolver: zodResolver(DebateFormSchema),
    defaultValues: {
      titulo: "",
      usuarioId: userId
    }
  });

  const { mutateAsync, isPending } = useMutation({
    mutationFn: createDebate,
    onSuccess: () => {
      toast.success("Debate criado com sucesso!");
      form.reset();
      setOpen(false);
    },
    onError: () => {
      toast.error("Erro ao criar debate");
    }
  });

  const onSubmit = (data: DebateFormType) => {
    mutateAsync(data);
  };

  return (
    <section className="w-full flex justify-center">
      <AnimatedGroup preset="slide" className="w-full max-w-md">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
              control={form.control}
              name="titulo"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Título do Debate</FormLabel>
                  <FormControl>
                    <Input placeholder="Ex: Debate sobre políticas públicas no cinema" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" disabled={isPending} className="w-full">
              {isPending ? "Criando..." : "Criar Debate"}
            </Button>
          </form>
        </Form>
      </AnimatedGroup>
    </section>
  );
}
