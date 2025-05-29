import { config } from "@/config";

export default function BlogHeader() {
  return (
    <header className="py-20 pb-6 text-center flex flex-col gap-4">
      <h1 className="text-6xl text-center font-bold font-serif">{config.public.blog.title}</h1>
      <p className="text-muted-foreground text-center text-xl">
        {config.public.blog.description}
      </p>
    </header>
  );
} 