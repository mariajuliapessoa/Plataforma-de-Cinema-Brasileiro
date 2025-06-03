"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import DebateCreateForm from "./debate-create-form";
import { useAuth } from "@/hooks/use-auth";
import { Plus } from "lucide-react";

export default function CriarDebate() {
  const [open, setOpen] = useState(false);
  const { user } = useAuth();

  if (!user) return null;

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="bg-primary hover:bg-primary/90 font-semibold text-white">
          <Plus className="w-4 h-4 mr-2" />
          Criar Debate
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-lg">
        <DialogHeader>
          <DialogTitle>Criar Debate</DialogTitle>
          <DialogDescription>Compartilhe sua opinião e chame outros para debater!</DialogDescription>
        </DialogHeader>
        <DebateCreateForm userId={user.id} setOpen={setOpen} />
      </DialogContent>
    </Dialog>
  );
}
