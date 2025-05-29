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

export default function Avaliar({ filmeId }: { filmeId: string }) {
  const { user } = useAuth();

  return (
    <div className="flex flex-col gap-2">
      {user && <AvaliarDialog filmeId={filmeId} userId={user.id} />}
    </div>
  );
}

export function AvaliarDialog({ filmeId, userId }: { filmeId: string, userId: string }) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="outline">Avaliar</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Avaliar</DialogTitle>
          <DialogDescription>
            Avalie o filme aqui.
          </DialogDescription>
        </DialogHeader>
        <AvaliarForm filmeId={filmeId} userId={userId} />
      </DialogContent>
    </Dialog>
  )
}
