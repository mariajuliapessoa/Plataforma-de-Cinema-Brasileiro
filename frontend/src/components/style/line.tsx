import { cn } from "@/lib/utils";

interface LineProps {
  style: "dotted" | "dashed" | "solid";
  className?: string;
}

export default function Line({ style, className }: LineProps) {
  return (
    <figure
      className={cn(
        "w-full h-px border-t border-border",
        {
          "border-dotted": style === "dotted",
          "border-dashed": style === "dashed",
          "border-solid": style === "solid",
        },
        className
      )}
    />
  );
}
