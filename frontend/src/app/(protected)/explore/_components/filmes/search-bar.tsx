"use client";

import { useState, useEffect } from "react";
import { Search } from "lucide-react";
import { Button } from "@/components/ui/button";

export function SearchBar({ onSearch }: { onSearch: (query: string) => void }) {
  const [localQuery, setLocalQuery] = useState("");

  // Disparar search após digitação com debounce
  useEffect(() => {
    const timeout = setTimeout(() => {
      onSearch(localQuery.trim());
    }, 500);
    return () => clearTimeout(timeout);
  }, [localQuery]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(localQuery.trim());
  };

  return (
    <form onSubmit={handleSubmit} className="flex w-full max-w-xl mx-auto items-center gap-2">
      <input
        type="text"
        value={localQuery}
        onChange={(e) => setLocalQuery(e.target.value)}
        placeholder="Pesquisar filmes brasileiros..."
        className="flex-1 border border-gray-300 rounded-md px-4 py-2 outline-none focus:ring-2 focus:ring-primary"
      />
      <Button type="submit" variant="default" size="icon">
        <Search className="w-5 h-5" />
      </Button>
    </form>
  );
}
