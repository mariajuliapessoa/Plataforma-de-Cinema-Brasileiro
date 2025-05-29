import { MDXRemote } from "next-mdx-remote/rsc";

interface MdxRendererProps {
  content: string;
}

const components = {
  h1: (props: React.HTMLProps<HTMLHeadingElement>) => <h1 className="text-2xl font-bold mb-4 mt-8" {...props} />,
  h2: (props: React.HTMLProps<HTMLHeadingElement>) => <h2 className="text-xl font-semibold mb-3 mt-6" {...props} />,
  h3: (props: React.HTMLProps<HTMLHeadingElement>) => <h3 className="text-lg font-medium mb-2 mt-4" {...props} />,
  p: (props: React.HTMLProps<HTMLParagraphElement>) => <p className="mb-4" {...props} />,
  ul: (props: React.HTMLProps<HTMLUListElement>) => <ul className="list-disc pl-6 mb-4" {...props} />,
  ol: (props: React.OlHTMLAttributes<HTMLOListElement>) => <ol className="list-decimal pl-6 mb-4" {...props} />,
  li: (props: React.HTMLProps<HTMLLIElement>) => <li className="mb-1" {...props} />,
  a: (props: React.HTMLProps<HTMLAnchorElement>) => <a className="text-primary hover:underline" {...props} />,
  blockquote: (props: React.HTMLProps<HTMLQuoteElement>) => <blockquote className="border-l-4 border-muted-foreground pl-4 italic my-4" {...props} />,
  code: (props: React.HTMLProps<HTMLElement>) => <code className="bg-muted rounded px-1 py-0.5" {...props} />,
  pre: (props: React.HTMLProps<HTMLPreElement>) => <pre className="bg-muted p-4 rounded overflow-x-auto my-4" {...props} />
};

export default function MdxRenderer({ content }: MdxRendererProps) {
  return (
    <div className="prose max-w-none">
      <MDXRemote source={content} components={components} />
    </div>
  );
} 