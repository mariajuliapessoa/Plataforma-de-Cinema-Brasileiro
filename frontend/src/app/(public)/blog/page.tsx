import fs from "fs";
import path from "path";
import matter from "gray-matter";
import PostsList from "./_components/posts-list";
import BlogHeader from "./_components/blog-header";

interface Post {
  slug: string;
  frontMatter: {
    title: string;
    date: string;
    description: string;
    image?: string;
    [key: string]: unknown;
  };
}

async function getAllPosts() {
  const postsDirectory = path.join(process.cwd(), "src/posts");
  const filenames = fs.readdirSync(postsDirectory);

  const posts = filenames.map((filename) => {
    const slug = filename.replace(/\.mdx?$/, "");
    const fullPath = path.join(postsDirectory, filename);
    const fileContents = fs.readFileSync(fullPath, "utf-8");
    const { data } = matter(fileContents);

    return {
      slug,
      frontMatter: data as Post["frontMatter"],
    };
  });

  return posts.sort((a, b) => {
    const dateA = new Date(a.frontMatter.date);
    const dateB = new Date(b.frontMatter.date);
    return dateB.getTime() - dateA.getTime();
  });
}

export default async function Blog() {
  const posts = await getAllPosts();

  return (
    <main>
      <BlogHeader />
      {posts.length > 0 ? (
        <PostsList posts={posts} />
      ) : (
        <div className="text-center py-12">
          <p className="text-gray-500">Nenhum post encontrado.</p>
        </div>
      )}
    </main>
  );
}
