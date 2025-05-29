'use client'

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 2,
      staleTime: 3 * 1000,
      gcTime: 1 * 60 * 1000,
      refetchOnWindowFocus: true,
      refetchOnReconnect: true
    }
  }
})

export default function LoginLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <QueryClientProvider client={queryClient}>
      <div>
        {children}
      </div>
    </QueryClientProvider>
  );
}