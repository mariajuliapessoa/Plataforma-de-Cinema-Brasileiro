export const posts = [
    {
      id: "1",
      user: {
        name: "Alex Johnson",
        username: "alexj",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      content:
        "Just finished building my new portfolio website using Next.js and Tailwind CSS. It's amazing how quickly you can build beautiful UIs these days!",
      likes: 24,
      comments: 5,
      reposts: 2,
      timestamp: new Date(Date.now() - 1000 * 60 * 30), // 30 minutes ago
    },
    {
      id: "2",
      user: {
        name: "Samantha Lee",
        username: "samlee",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      content:
        "Excited to announce that I'll be speaking at the upcoming React Conference about Server Components and the future of React!",
      image: "/placeholder.svg?height=400&width=600",
      likes: 152,
      comments: 23,
      reposts: 41,
      timestamp: new Date(Date.now() - 1000 * 60 * 60 * 2), // 2 hours ago
    },
    {
      id: "3",
      user: {
        name: "Marcus Chen",
        username: "mchen",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      content:
        "Just discovered this amazing VS Code extension that has completely transformed my coding workflow. Check it out: https://marketplace.visualstudio.com",
      likes: 87,
      comments: 14,
      reposts: 9,
      timestamp: new Date(Date.now() - 1000 * 60 * 60 * 5), // 5 hours ago
    },
    {
      id: "4",
      user: {
        name: "Priya Patel",
        username: "priyap",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      content:
        "Today's office view! Working remotely from the mountains this week. 🏔️\n\nSometimes a change of scenery is all you need to boost productivity.",
      image: "/placeholder.svg?height=400&width=600",
      likes: 215,
      comments: 32,
      reposts: 18,
      timestamp: new Date(Date.now() - 1000 * 60 * 60 * 8), // 8 hours ago
    },
    {
      id: "5",
      user: {
        name: "Jordan Taylor",
        username: "jtaylor",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      content:
        "Just released v2.0 of my open-source library! New features include:\n\n- Improved performance\n- TypeScript support\n- Better documentation\n\nCheck it out on GitHub and let me know what you think!",
      likes: 132,
      comments: 28,
      reposts: 45,
      timestamp: new Date(Date.now() - 1000 * 60 * 60 * 24), // 1 day ago
    },
  ]
  