"use client";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import AvaliarForm from "./avaliar-form";
import { useAuth } from "@/hooks/use-auth";
import { useState } from "react";

export default function Avaliar({ filmeId }: { filmeId: string }) {
  const { user } = useAuth();

  return (
    <div className="flex flex-col gap-2">
      {user && <AvaliarDialog filmeId={filmeId} userId={user.id} />}
    </div>
  );
}

export function AvaliarDialog({ filmeId, userId }: { filmeId: string, userId: string }) {
  const [open, setOpen] = useState(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="w-full font-semibold text-white bg-primary hover:bg-primary/90 transition-colors" variant="default">
          Avaliar
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Avaliar</DialogTitle>
          <DialogDescription>Deixe sua avaliação sobre o filme.</DialogDescription>
        </DialogHeader>
        <AvaliarForm filmeId={filmeId} userId={userId} setOpen={setOpen} />
      </DialogContent>
    </Dialog>
  );
}