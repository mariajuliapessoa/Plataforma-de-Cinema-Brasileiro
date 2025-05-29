"use client";

import { AnimatedGroup } from "@/components/motion-primitives/animated-group";
import { Switch } from "@/components/ui/switch";
import { config } from "@/config";
import { cn } from "@/lib/utils";
import { useState } from "react";
import { CheckIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import Line from "@/components/style/line";
import { AnimatedNumber } from "@/components/motion-primitives/animated-number";

export default function PricingCards() {
  const [yearly, setYearly] = useState(false);

  return (
    <section className="w-full flex items-center justify-center py-20">
      <div className="w-full xl:max-w-5xl flex flex-col gap-4">
        <div className="flex items-center justify-center gap-2">
          <p className="text-sm text-muted-foreground">Mensal</p>
          <Switch checked={yearly} onCheckedChange={setYearly} />
          <p className="text-sm text-muted-foreground">Anual</p>
        </div>
        <AnimatedGroup preset="scale" className=" grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {config.project.prices.map((price) => (
            <PricingCard key={price.title} title={price.title} description={price.description} price={yearly ? price.yearlyPrice : price.monthlyPrice} isYearly={yearly} features={price.features} recommended={price.recommended} />
          ))}
        </AnimatedGroup>
      </div>
    </section>
  );
}

interface PricingCardProps {
  title: string;
  description: string;
  price: number;
  isYearly: boolean;
  features: string[];
  recommended?: boolean;
}

const PricingCard = ({ title, description, price, isYearly, features, recommended }: PricingCardProps) => {
  return (
    <div className={cn("p-4 rounded-lg flex flex-col border gap-4 min-h-96", recommended && "border-primary")}>
      <div className="flex flex-col gap-2">
        <h1 className="text-lg font-bold">{title}</h1>
        <p className="text-sm text-muted-foreground">{description}</p>
      </div>
      <div className="flex items-center justify-between">
        <p className="text-3xl font-light">
          R$
          <AnimatedNumber
            className='inline-flex items-center '
            springOptions={{
              bounce: 0,
              duration: 1000,
            }}
            value={price}
          />,00
          <span className="font-normal text-sm text-muted-foreground">/{isYearly ? "ano" : "mês"}</span>
        </p>
      </div>
      <Line style="dashed" />
      <ul className="flex flex-col gap-1">
        {features.map((feature) => (
          <li className="flex items-center gap-2" key={feature}>
            <CheckIcon className="w-4 h-4" />
            {feature}
          </li>
        ))}
      </ul>
      <Button size="lg" className="w-full mt-auto">Contratar</Button>
    </div>
  );
};