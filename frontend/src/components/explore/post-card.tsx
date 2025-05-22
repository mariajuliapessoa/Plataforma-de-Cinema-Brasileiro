import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardFooter, CardHeader } from "@/components/ui/card"
import { formatDistanceToNow } from "@/lib/utils"
import { Heart, MessageCircle, Repeat2, Share2 } from "lucide-react"

interface Post {
  id: string
  user: {
    name: string
    username: string
    avatar: string
  }
  content: string
  image?: string
  likes: number
  comments: number
  reposts: number
  timestamp: Date
}

interface PostCardProps {
  post: Post
}

export function PostCard({ post }: PostCardProps) {
  return (
    <Card>
      <CardHeader className="flex flex-row items-start gap-4 space-y-0 pb-2">
        <Avatar className="h-10 w-10">
          <AvatarImage src={post.user.avatar || "/placeholder.svg"} alt={post.user.name} />
          <AvatarFallback>{post.user.name.charAt(0)}</AvatarFallback>
        </Avatar>
        <div className="grid gap-1">
          <div className="flex items-center gap-2">
            <span className="font-semibold">{post.user.name}</span>
            <span className="text-muted-foreground">@{post.user.username}</span>
            <span className="text-muted-foreground">·</span>
            <span className="text-muted-foreground">{formatDistanceToNow(post.timestamp)}</span>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <p className="whitespace-pre-line">{post.content}</p>
        {post.image && (
          <div className="mt-3 overflow-hidden rounded-lg">
            <img src={post.image || "/placeholder.svg"} alt="Post attachment" className="w-full object-cover" />
          </div>
        )}
      </CardContent>
      <CardFooter className="border-t pt-4">
        <div className="flex justify-between w-full">
          <Button variant="ghost" size="sm" className="gap-1">
            <MessageCircle className="h-4 w-4" />
            <span>{post.comments}</span>
          </Button>
          <Button variant="ghost" size="sm" className="gap-1">
            <Repeat2 className="h-4 w-4" />
            <span>{post.reposts}</span>
          </Button>
          <Button variant="ghost" size="sm" className="gap-1">
            <Heart className="h-4 w-4" />
            <span>{post.likes}</span>
          </Button>
          <Button variant="ghost" size="sm">
            <Share2 className="h-4 w-4" />
          </Button>
        </div>
      </CardFooter>
    </Card>
  )
}
