import { AnimatedGroup } from "@/components/motion-primitives/animated-group";
import { config } from "@/config";
import Image from "next/image";

export default function Bento() {
  return (
    <section className="w-full gap-8 flex flex-col">
      <BentoHeader title={config.public.bento.title} description={config.public.bento.description} />
      <AnimatedGroup preset="scale" className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {config.public.bento.items.map((item, i) => (
          <BentoCard key={i} {...item} />
        ))}
      </AnimatedGroup>
    </section>
  );
}

interface RecursoItem {
  title: string;
  description: string;
  image?: string;
}

const BentoCard = ({ title, description, image }: RecursoItem) => {
  return (
    <div className="rounded-xl border p-6 flex flex-col h-full">
      {image && (
        <div className="mb-4 relative w-full aspect-video rounded-lg overflow-hidden">
          <Image
            src={image}
            alt={title}
            fill
            className="object-cover"
          />
        </div>
      )}
      <div>
        <h3 className="text-xl font-bold mb-2">{title}</h3>
        <p className="text-muted-foreground">{description}</p>
      </div>
    </div>
  )
}

const BentoHeader = ({ title, description }: { title: string, description: string }) => {
  return (
    <div className="flex flex-col gap-2">
      <h2 className="text-4xl font-bold">{title}</h2>
      <p className="text-muted-foreground text-lg">{description}</p>
    </div>
  );
}