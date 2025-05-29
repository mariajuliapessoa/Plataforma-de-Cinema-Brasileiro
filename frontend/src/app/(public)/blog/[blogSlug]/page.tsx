import fs from "fs";
import path from "path";
import matter from "gray-matter";
import MdxRenderer from "./_components/mdx-renderer";
import PostHeader from "./_components/post-header";

interface FrontMatter {
  title: string;
  date: string;
  [key: string]: unknown;
}

export async function generateStaticParams() {
  const postsDirectory = path.join(process.cwd(), "src/posts");
  const filenames = fs.readdirSync(postsDirectory);

  return filenames.map((filename) => ({
    blogSlug: filename.replace(/\.mdx?$/, ""),
  }));
}

async function getPostBySlug(slug: string) {
  const postFilePath = path.join(process.cwd(), "src/posts", `${slug}.mdx`);
  const source = fs.readFileSync(postFilePath, "utf-8");

  const { content, data } = matter(source);
  return {
    content,
    frontMatter: data as FrontMatter,
  };
}

export default async function BlogPost({ params }: { params: Promise<{ blogSlug: string }> }) {
  const { blogSlug } = await params;
  const { content, frontMatter } = await getPostBySlug(blogSlug);

  return (
    <main>
      <article className="max-w-5xl mx-auto py-8 px-4">
        <PostHeader title={frontMatter.title} date={frontMatter.date} />
        <MdxRenderer content={content} />
      </article>
    </main>
  );
}
