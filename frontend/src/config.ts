import {
  CreditCardIcon,
  InfoIcon,
  HelpCircleIcon,
  HomeIcon,
  FilmIcon,
  StarIcon,
  UsersIcon,
  BookmarkIcon,
  ThumbsUpIcon,
  TicketIcon,
} from "lucide-react";

export const config = {
  project: {
    name: "Bracine",
    description:
      "Sua plataforma de streaming dedicada ao cinema brasileiro, com acesso gratuito aos melhores filmes nacionais",
    engagement: [
      {
        title: "Explorador",
        description:
          "Descubra o melhor do cinema brasileiro com acesso ilimitado ao nosso catálogo",
        features: [
          "Acesso a todos os filmes",
          "Recomendações personalizadas",
          "Participação na comunidade",
        ],
      },
      {
        title: "Crítico",
        description:
          "Participe ativamente da comunidade cinematográfica brasileira avaliando e comentando filmes",
        features: [
          "Todas as vantagens do Explorador",
          "Publicação de avaliações detalhadas",
          "Participação em discussões exclusivas",
        ],
        recommended: true,
      },
      {
        title: "Cinéfilo",
        description:
          "Torne-se um verdadeiro conhecedor do cinema nacional com acesso a conteúdos exclusivos",
        features: [
          "Todas as vantagens do Crítico",
          "Notificações sobre lançamentos",
          "Acesso a documentários exclusivos sobre a produção nacional",
        ],
      },
    ],
  },
  public: {
    navigation: {
      footerSections: [
        {
          title: "Navegação",
          links: [
            {
              label: "Início",
              href: "/",
            },
            {
              label: "Catálogo",
              href: "/catalog",
            },
            {
              label: "Destaques",
              href: "/featured",
            },
            {
              label: "Diretores",
              href: "/directors",
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
          title: "Suporte",
          links: [
            {
              label: "Central de ajuda",
              href: "/support",
            },
            {
              label: "Perguntas frequentes",
              href: "/faq",
            },
            {
              label: "Contato",
              href: "/contact",
            },
          ],
        },
        {
          title: "Redes sociais",
          links: [
            {
              label: "Instagram",
              href: "https://instagram.com/bracine",
            },
            {
              label: "Twitter",
              href: "https://twitter.com/bracine",
            },
            {
              label: "YouTube",
              href: "https://youtube.com/bracine",
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
          icon: FilmIcon,
          label: "Catálogo",
          href: "/catalog",
        },
        {
          icon: StarIcon,
          label: "Destaques",
          href: "/featured",
        },
        {
          icon: InfoIcon,
          label: "Sobre",
          href: "/about",
        },
        {
          icon: HelpCircleIcon,
          label: "Comunidade",
          href: "/community",
        },
      ],
    },
    hero: {
      title: "Descubra o melhor do cinema brasileiro",
      description:
        "Acesso gratuito a filmes nacionais de todos os gêneros e épocas, desde clássicos até produções contemporâneas",
      image: {
        src: "https://images.pexels.com/photos/7991579/pexels-photo-7991579.jpeg",
        alt: "Cinema brasileiro em destaque",
        text: "Seu portal para a rica cultura cinematográfica brasileira.",
      },
      button: {
        label: "Explorar filmes",
        href: "/catalog",
      },
    },
    faq: {
      title: "Perguntas frequentes",
      description:
        "Tire suas dúvidas sobre o Bracine e como aproveitar ao máximo nossa plataforma",
      items: [
        {
          question: "O Bracine é realmente gratuito?",
          answer:
            "Sim! O Bracine é uma plataforma totalmente gratuita dedicada à divulgação e valorização do cinema brasileiro.",
        },
        {
          question: "Como posso avaliar os filmes que assisti?",
          answer:
            "Após criar sua conta, você pode deixar avaliações e comentários em todos os filmes disponíveis na plataforma.",
        },
        {
          question: "Com qual frequência novos filmes são adicionados?",
          answer:
            "Atualizamos nosso catálogo semanalmente com novas produções nacionais, desde clássicos restaurados até lançamentos recentes.",
        },
        {
          question: "Posso assistir aos filmes em dispositivos móveis?",
          answer:
            "Sim, o Bracine é responsivo e funciona em qualquer dispositivo com acesso à internet, incluindo smartphones, tablets, computadores e smart TVs.",
        },
        {
          question: "Como posso sugerir um filme para o catálogo?",
          answer:
            "Você pode enviar sugestões através da seção 'Contato' ou participar ativamente na comunidade onde realizamos enquetes periódicas.",
        },
      ],
    },
    bento: {
      title: "Explore o universo do cinema brasileiro",
      description:
        "Descubra filmes de todos os gêneros, épocas e regiões do Brasil em nossa plataforma.",
      items: [
        {
          title: "Cinema Novo",
          description:
            "Explore o movimento que revolucionou o cinema brasileiro nos anos 60 e 70",
          href: "/category/cinema-novo",
          image: "https://images.pexels.com/photos/7234213/pexels-photo-7234213.jpeg",
        },
        {
          title: "Documentários Nacionais",
          description:
            "Conheça a realidade brasileira através de documentários premiados",
          href: "/category/documentarios",
          image: "https://images.pexels.com/photos/3945313/pexels-photo-3945313.jpeg",
        },
        {
          title: "Comédias Brasileiras",
          description:
            "Divirta-se com as melhores comédias produzidas no Brasil",
          href: "/category/comedias",
          image: "https://images.pexels.com/photos/7991438/pexels-photo-7991438.jpeg",
        },
        {
          title: "Cinema Contemporâneo",
          description:
            "Descubra as novas vozes e tendências do cinema nacional atual",
          href: "/category/contemporaneo",
          image: "https://images.pexels.com/photos/7991160/pexels-photo-7991160.jpeg",
        },
      ],
    },
    cta: {
      title: "Junte-se à comunidade Bracine",
      description: "Participe de discussões, avalie filmes e compartilhe suas opiniões sobre o cinema brasileiro",
      button: {
        label: "Criar conta gratuita",
        href: "/register",
      },
    },
    blog: {
      title: "Notícias e Artigos",
      description: "Fique por dentro das novidades do cinema brasileiro e análises exclusivas",
    },
    pricing: {
      title: "Formas de Participação",
      description: "Escolha como você deseja participar da comunidade Bracine",
    },
    hiw: {
      title: "Como funciona o Bracine?",
      description: "Descubra como aproveitar ao máximo a nossa plataforma de cinema brasileiro",
      items: [
        {
          title: "Explore o catálogo",
          description:
            "Navegue por nossa extensa coleção de filmes brasileiros organizados por gênero, diretor, época e região",
          image: "https://images.pexels.com/photos/7991579/pexels-photo-7991579.jpeg",
        },
        {
          title: "Assista e avalie",
          description:
            "Assista aos filmes gratuitamente e compartilhe sua opinião através de avaliações e comentários",
          image: "https://images.pexels.com/photos/7991631/pexels-photo-7991631.jpeg",
        },
        {
          title: "Participe da comunidade",
          description: "Interaja com outros amantes do cinema brasileiro em discussões e eventos online",
          image: "https://images.pexels.com/photos/8107191/pexels-photo-8107191.jpeg",
        },
      ],
    },
    login: {
      title: "Bem-vindo de volta ao Bracine!",
      description: "Entre na sua conta para continuar explorando o melhor do cinema brasileiro",
    },
    register: {
      title: "Junte-se ao Bracine",
      description: "Crie sua conta gratuita e tenha acesso ao melhor do cinema brasileiro",
    },
  },
  protected: {
    navigation: {
      navbarLinks: [
        {
          icon: FilmIcon,
          label: "Filmes",
          href: "/explore",
        },
        {
          icon: StarIcon,
          label: "Comunidade",
          href: "/explore/comunidade",
        },
        {
          icon: BookmarkIcon,
          label: "Minha Lista",
          href: "/explore/lista",
        },
        {
          icon: ThumbsUpIcon,
          label: "Desafios",
          href: "/explore/desafios",
        },
      ],
    },
  },
};