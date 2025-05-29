import { PostCard } from "./post-card"
import { posts } from "../../data/posts"

export function Feed() {
  return (
    <div className="space-y-6">
      {posts.map((post) => (
        <PostCard key={post.id} post={post} />
      ))}
    </div>
  )
}
