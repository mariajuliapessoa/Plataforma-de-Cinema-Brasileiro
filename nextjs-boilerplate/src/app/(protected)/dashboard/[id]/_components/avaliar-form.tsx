"use client";

import { AnimatedGroup } from "@/components/motion-primitives/animated-group";
import { AvaliacaoCreateSchema, AvaliacaoCreateType } from "@/schemas/avaliacao.schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { useMutation } from "@tanstack/react-query";
import { Button } from "@/components/ui/button";
import { Loader2 } from "lucide-react";
import { avaliarFilme } from "../actions";
import { Slider } from "@/components/ui/slider";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Textarea } from "@/components/ui/textarea";

export default function AvaliarForm({ filmeId, userId, setOpen }: { filmeId: string, userId: string, setOpen: (open: boolean) => void }) {

  const form = useForm<AvaliacaoCreateType>({
    resolver: zodResolver(AvaliacaoCreateSchema),
    mode: "onSubmit",
    defaultValues: {
      texto: "",
      nota: 5,
      usuarioId: userId,
      filmeId,
    }
  });

  const { mutateAsync, isPending } = useMutation({
    mutationFn: async (data: AvaliacaoCreateType) => {
      return await avaliarFilme(data);
    },
    onSuccess: () => {
      toast.success('Avaliação enviada com sucesso!');
      form.reset();
      setOpen(false);
    },
    onError: () => {
      toast.error('Erro ao enviar avaliação');
    }
  });

  const onSubmit = (data: AvaliacaoCreateType) => {
    const avaliacaoData = {
      ...data,
      usuarioId: userId,
      filmeId: filmeId,
    };

    mutateAsync(avaliacaoData);
  }

  return (
    <section className="w-full flex items-center justify-center py-2">
      <AnimatedGroup preset="slide" className="w-full max-w-md flex flex-col gap-6">
        <Form {...form}>
          <form className="flex flex-col gap-4" onSubmit={form.handleSubmit(onSubmit)}>
            <FormField
              control={form.control}
              name="texto"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Comentário (mínimo 10 caracteres)</FormLabel>
                  <FormControl>
                    <Textarea
                      placeholder="Escreva sua opinião sobre o filme..."
                      className="min-h-[100px]"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="nota"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nota: {field.value}/10</FormLabel>
                  <FormControl>
                    <Slider
                      value={[field.value]}
                      onValueChange={(value) => field.onChange(value[0])}
                      max={10}
                      min={1}
                      step={1}
                      className="w-full"
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button size="lg" type="submit" disabled={isPending}>
              {isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : 'Enviar Avaliação'}
            </Button>
          </form>
        </Form>
      </AnimatedGroup>
    </section>
  );
}