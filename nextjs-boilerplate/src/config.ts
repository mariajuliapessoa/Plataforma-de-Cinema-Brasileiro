import {
  CreditCardIcon,
  InfoIcon,
  HelpCircleIcon,
  HomeIcon,
} from "lucide-react";

export const config = {
  project: {
    name: "Campanhas",
    description:
      "Plataforma para você criar e gerenciar suas campanhas com segurança e praticidade",
    prices: [
      {
        title: "Plano Básico",
        description:
          "Plano ideal para quem deseja criar e gerenciar campanhas com segurança e praticidade",
        monthlyPrice: 10,
        yearlyPrice: 100,
        features: [
          "100 campanhas",
          "1000000 de visualizações",
          "1000000 de cliques",
        ],
      },
      {
        title: "Plano Pro",
        description:
          "Plano ideal para quem deseja criar e gerenciar campanhas com segurança e praticidade",
        monthlyPrice: 20,
        yearlyPrice: 200,
        features: [
          "1000 campanhas",
          "1000000 de visualizações",
          "1000000 de cliques",
        ],
        recommended: true,
      },
      {
        title: "Plano Enterprise",
        description:
          "Plano ideal para quem deseja criar e gerenciar campanhas com segurança e praticidade",
        monthlyPrice: 30,
        yearlyPrice: 300,
        features: [
          "10000 campanhas",
          "10000000 de visualizações",
          "10000000 de cliques",
        ],
      },
    ],
  },
  public: {
    navigation: {
      navbarLinks: [
        {
          icon: CreditCardIcon,
          label: "Preços",
          href: "/pricing",
        },
        {
          icon: InfoIcon,
          label: "Sobre",
          href: "/about",
        },
        {
          icon: HelpCircleIcon,
          label: "Comunidade",
          href: "/blog",
        },
      ],
      footerSections: [
        {
          title: "Páginas",
          links: [
            {
              label: "Início",
              href: "/",
            },
            {
              label: "Preços  ",
              href: "/pricing",
            },
            {
              label: "Sobre",
              href: "/about",
            },
            {
              label: "Comunidade",
              href: "/blog",
            },
            {
              label: "Termos de uso",
              href: "/terms",
            },
            {
              label: "Política de privacidade",
              href: "/privacy",
            },
          ],
        },
        {
          title: "Ajuda",
          links: [
            {
              label: "Central de ajuda",
              href: "/support",
            },
            {
              label: "Dúvidas frequentes",
              href: "/faq",
            },
          ],
        },
        {
          title: "Redes sociais",
          links: [
            {
              label: "Facebook",
              href: "/facebook",
            },
            {
              label: "Instagram",
              href: "/instagram",
            },
            {
              label: "Twitter",
              href: "/twitter",
            },
          ],
        },
      ],
      drawerLinks: [
        {
          icon: HomeIcon,
          label: "Início",
          href: "/",
        },
        {
          icon: CreditCardIcon,
          label: "Preços",
          href: "/pricing",
        },
        {
          icon: InfoIcon,
          label: "Sobre",
          href: "/about",
        },
        {
          icon: HelpCircleIcon,
          label: "Comunidade",
          href: "/blog",
        },
      ],
    },
    hero: {
      title: "Love what you do and make money too",
      description:
        "Join all types of creators getting donations, memberships and sales from their fans!",
      image: {
        src: "/hero.png",
        alt: "Hero image",
        text: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quos.",
      },
      button: {
        label: "Crie sua conta agora!",
        href: "/pricing",
      },
    },
    faq: {
      title: "Perguntas frequentes",
      description:
        "Veja as perguntas frequentes sobre o sistema e como ele funciona",
      items: [
        {
          question: "Como funciona o sistema?",
          answer:
            "O sistema funciona de forma simples e intuitiva, você pode criar campanhas, gerenciar campanhas e visualizar campanhas.",
        },
        {
          question: "Como funciona o sistema?",
          answer:
            "O sistema funciona de forma simples e intuitiva, você pode criar campanhas, gerenciar campanhas e visualizar campanhas.",
        },
        {
          question: "Como funciona o sistema?",
          answer:
            "O sistema funciona de forma simples e intuitiva, você pode criar campanhas, gerenciar campanhas e visualizar campanhas.",
        },
        {
          question: "Como funciona o sistema?",
          answer:
            "O sistema funciona de forma simples e intuitiva, você pode criar campanhas, gerenciar campanhas e visualizar campanhas.",
        },
        {
          question: "Como funciona o sistema?",
          answer:
            "O sistema funciona de forma simples e intuitiva, você pode criar campanhas, gerenciar campanhas e visualizar campanhas.",
        },
      ],
    },
    bento: {
      title: "Build the perfect customer-facing AI agent",
      description:
        "Chatbase gives you all the tools you need to train your perfect AI agent and connect it to your systems.",
      items: [
        {
          title: "Como criar uma campanha",
          description:
            "Aprenda a criar uma campanha de forma simples e intuitiva",
          href: "/blog/como-criar-uma-campanha",
          image: "/ocean.png",
        },
        {
          title: "Como criar uma campanha",
          description:
            "Aprenda a criar uma campanha de forma simples e intuitiva",
          href: "/blog/como-criar-uma-campanha",
          image: "/ocean.png",
        },
        {
          title: "Como criar uma campanha",
          description:
            "Aprenda a criar uma campanha de forma simples e intuitiva",
          href: "/blog/como-criar-uma-campanha",
          image: "/ocean.png",
        },
        {
          title: "Como criar uma campanha",
          description:
            "Aprenda a criar uma campanha de forma simples e intuitiva",
          href: "/blog/como-criar-uma-campanha",
          image: "/ocean.png",
        },
      ],
    },
    cta: {
      title: "Crie sua campanha agora",
      description: "Crie sua campanha de forma simples e intuitiva",
      button: {
        label: "Criar campanha",
        href: "/pricing",
      },
    },
    blog: {
      title: "Últimas atualizações",
      description: "Veja as últimas notícias e dicas do nossa comunidade",
    },
    pricing: {
      title: "Planos",
      description: "Veja os planos disponíveis para você",
    },
    hiw: {
      title: "Como funciona o sistema?",
      description: "Veja como funciona o sistema e como ele funciona",
      items: [
        {
          title: "Build & deploy your agent",
          description:
            "Train an agent on your business data, configure the actions it can take, then deploy it for your customers.",
          image: "/bg.jpg",
        },
        {
          title: "Agent solves your customers' problems ",
          description:
            "The agent will answer questions and access external systems to gather data and take actions.",
          image: "/hero.png",
        },
        {
          title: "Refine & optimize",
          description: "This ensures your agent is improving over time.",
          image: "/ocean.png",
        },
      ],
    },
    login: {
      title: "Bem-vindo de volta!",
      description: "Insira suas informações abaixo para entrar na sua conta",
    },
    register: {
      title: "Crie sua conta",
      description: "Insira suas informações abaixo para criar uma conta",
    },
  },
};
