'use client'

import { config } from "@/config";
import { useState } from "react";
import { motion } from "motion/react";
import Image from "next/image";

export default function HIW() {
  const [active, setActive] = useState(0);

  const handleActive = (index: number) => {
    setActive(index);
  }

  return (
    <section className="w-full gap-8 flex flex-col">
      <HIWHeader title={config.public.hiw.title} description={config.public.hiw.description} />
      <div className="flex flex-col md:flex-row gap-6">
        <div className="flex flex-col gap-4 flex-1">
          {config.public.hiw.items.map((item, index) => (
            <HIWItem key={index} {...item} isActive={active === index} handleActive={handleActive} index={index} />
          ))}
        </div>
        <div className="relative rounded-xl overflow-hidden aspect-square flex-1 min-h-[250px]">
          <Image
            src={config.public.hiw.items[active].image || "/bg.jpg"}
            alt={config.public.hiw.items[active].title}
            fill
            className="object-cover transition-all duration-300"
          />
        </div>
      </div>
    </section>
  );
}

interface HIWItem {
  title: string;
  description: string;
  isActive: boolean;
  handleActive: (index: number) => void;
  index: number;
}

const HIWItem = ({ title, description, isActive, handleActive, index }: HIWItem) => {
  return (
    <div
      onClick={() => handleActive(index)}
      className={`flex items-start gap-2 p-6 rounded-xl hover:cursor-pointer w-full ${!isActive ? "" : "border bg-secondary"}`}
    >
      <div className="flex items-center justify-center text-primary shrink-0">
        <h1 className="text-2xl font-bold">0{index + 1}.</h1>
      </div>
      <div className={`flex flex-col justify-start items-start w-full overflow-hidden ${!isActive ? "" : "gap-2"}`}>
        <h3 className="text-2xl font-bold">{title}</h3>
        <motion.div
          initial={{ height: 0, opacity: 0 }}
          animate={{
            height: isActive ? "auto" : 0,
            opacity: isActive ? 1 : 0,
            y: isActive ? 0 : 10
          }}
          transition={{ duration: 0.3 }}
          className="w-full overflow-hidden"
        >
          <p className="text-muted-foreground text-lg">{description}</p>
        </motion.div>
      </div>
    </div>
  );
}

const HIWHeader = ({ title, description }: { title: string, description: string }) => {
  return (
    <div className="flex flex-col gap-2">
      <h3 className="text-4xl font-bold">{title}</h3>
      <p className="text-muted-foreground text-lg">{description}</p>
    </div>
  );
}