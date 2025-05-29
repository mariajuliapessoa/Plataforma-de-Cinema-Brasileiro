import { Sanitizer } from "@/lib/utils";

interface PostHeaderProps {
  title: string;
  date: string;
}

export default function PostHeader({ title, date }: PostHeaderProps) {
  return (
    <header className="mb-8">
      <h1 className="text-4xl font-bold mb-2">{title}</h1>
      <time className="text-muted-foreground block">{Sanitizer.formatDate(date)}</time>
    </header>
  );
} 