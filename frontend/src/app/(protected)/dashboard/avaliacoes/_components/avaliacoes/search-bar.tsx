'use client'

import { useState } from 'react'
import { Search } from 'lucide-react'
import { Button } from '@/components/ui/button'

export function SearchBar() {
  const [query, setQuery] = useState('')

  return (
    <div className="flex w-full justify-between gap-2 items-center">
      <input
        type="text"
        value={query}
        onChange={e => setQuery(e.target.value)}
        placeholder="Pesquisar…"
        className="bg-transparent flex-1 outline-none border rounded-md p-2 px-4"
        autoFocus
      />
      <Button
        variant="ghost"
        size="icon"
        className="rounded-md"
      >
        <Search />
      </Button>
    </div>
  )
}