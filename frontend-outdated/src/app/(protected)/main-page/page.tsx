"use client";

import { Button } from "@/components/ui/button";
import authService from "@/services/auth.service";
import { useRouter } from "next/navigation";
import { toast } from "sonner";

const Dashbooard = () => {
  const router = useRouter();
  const handleLogout = async () => {
    authService.logout();
    router.push("/")
    toast.success("Volte sempre!");
  };

  return (
    <div className="min-h-screen bg-black">
      <h1 className="text-3xl text-white">Página principal pos login</h1>
      <Button onClick={handleLogout}>Logout</Button>
    </div>
  );
};

export default Dashbooard;
