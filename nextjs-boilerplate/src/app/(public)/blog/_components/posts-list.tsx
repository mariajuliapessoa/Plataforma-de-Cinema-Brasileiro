import { AnimatedGroup } from "@/components/motion-primitives/animated-group";
import PostCard from "./post-card";

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

interface PostsListProps {
  posts: Post[];
}

export default function PostsList({ posts }: PostsListProps) {
  if (posts.length === 0) {
    return null;
  }

  const [featuredPost, ...otherPosts] = posts;

  return (
    <div className="w-full flex items-center justify-center py-20">
      <div className="w-full xl:max-w-5xl flex flex-col gap-6">
        <AnimatedGroup preset="scale" className="w-full">
          <PostCard
            key={featuredPost.slug}
            slug={featuredPost.slug}
            title={featuredPost.frontMatter.title}
            date={featuredPost.frontMatter.date}
            description={featuredPost.frontMatter.description}
            image={featuredPost.frontMatter.image}
            featured={true}
          />
        </AnimatedGroup>

        {otherPosts.length > 0 && (
          <AnimatedGroup preset="scale" className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {otherPosts.map((post) => (
              <PostCard
                key={post.slug}
                slug={post.slug}
                title={post.frontMatter.title}
                date={post.frontMatter.date}
                description={post.frontMatter.description}
                image={post.frontMatter.image}
              />
            ))}
          </AnimatedGroup>
        )}
      </div>

    </div>
  );
} 