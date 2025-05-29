import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion";
import { config } from "@/config";

export default function FAQ() {
  return (
    <section className="w-full gap-4 flex flex-col">
      <FAQHeader title={config.public.faq.title} />
      <Accordion type="single" collapsible className="w-full">
        {config.public.faq.items.map((faq, index) => (
          <AccordionItem value={index.toString()} key={index}>
            <AccordionTrigger className="text-lg font-semibold">{faq.question}</AccordionTrigger>
            <AccordionContent className="text-base text-muted-foreground">
              {faq.answer}
            </AccordionContent>
          </AccordionItem>
        ))}
      </Accordion>
    </section>
  );
}

const FAQHeader = ({ title }: { title: string }) => {
  return (
    <h1 className="text-4xl font-bold">{title}</h1>
  );
}
