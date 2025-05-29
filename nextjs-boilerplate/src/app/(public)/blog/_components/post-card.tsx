import Link from "next/link";
import Image from "next/image";
import { Sanitizer } from "@/lib/utils";

interface PostCardProps {
  slug: string;
  title: string;
  date: string;
  description: string;
  image?: string;
  featured?: boolean;
}

export default function PostCard({ slug, title, date, description, image, featured }: PostCardProps) {
  return (
    <Link href={`/blog/${slug}`}>
      <article className={`hover:bg-muted duration-200 flex flex-col border rounded-md p-6 gap-4 h-fit ${featured ? 'md:flex-row' : ''}`}>
        {!image && (
          <div className={`relative rounded-md border overflow-hidden ${featured ? 'h-60 md:h-80 md:w-1/2' : 'h-48'} w-full`}>
            <Image src={"/bg.jpg"} alt={title} fill className="object-cover" />
          </div>
        )}
        {image && (
          <div className={`relative rounded-md border overflow-hidden ${featured ? 'h-60 md:h-80 md:w-1/2' : 'h-48'} w-full`}>
            <Image src={image} alt={title} fill className="object-cover" />
          </div>
        )}
        <div className={`${featured ? 'md:w-1/2 md:pl-6 flex flex-col justify-start md:justify-start h-full' : ''}`}>
          <h2 className={`${featured ? 'text-3xl' : 'text-2xl'} font-bold transition-colors truncate`}>{title}</h2>
          <p className={`text-muted-foreground ${featured ? '' : 'truncate'} mt-2`}>{description}</p>
          <time className="text-sm text-muted-foreground mt-3 block">{Sanitizer.formatDate(date)}</time>
        </div>
      </article>
    </Link>
  );
} 