import type { Movie } from "../models/movie"

export const featuredMovie: Movie = {
  id: "1",
  title: "Bacurau",
  description:
    "Num futuro próximo, Bacurau, uma pequena cidade no sertão brasileiro, lamenta a perda de sua matriarca, Carmelita, que viveu até os 94 anos. Dias depois, os moradores percebem que a comunidade não está mais no mapa.",
  posterUrl: "https://upload.wikimedia.org/wikipedia/pt/6/67/Bacurau_%28filme%29.jpeg",
  backdropUrl: "/placeholder.svg?height=1080&width=1920",
  rating: 8.7,
  year: 2019,
  duration: "2h 11min",
  genres: ["Drama", "Mistério", "Faroeste"],
  director: "Kleber Mendonça Filho, Juliano Dornelles",
}

export const newReleases: Movie[] = [
  {
    id: "2",
    title: "Marte Um",
    description:
      "Uma família negra de classe média baixa na periferia de Belo Horizonte luta para realizar seus sonhos enquanto o Brasil enfrenta a eleição presidencial de 2018.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.6,
    year: 2022,
    duration: "1h 55min",
    genres: ["Drama"],
    director: "Gabriel Martins",
  },
  {
    id: "3",
    title: "Medida Provisória",
    description:
      "Em um futuro distópico, o governo brasileiro decreta uma medida provisória que obriga os cidadãos negros a migrarem para a África na tentativa de retornar às suas origens.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 6.8,
    year: 2022,
    duration: "1h 34min",
    genres: ["Drama", "Ficção Científica"],
    director: "Lázaro Ramos",
  },
  {
    id: "4",
    title: "Paloma",
    description:
      "Paloma, uma mulher trans, sonha em se casar na igreja com seu noivo. Enfrentando o preconceito da instituição religiosa, ela luta pelo direito de realizar seu sonho.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.2,
    year: 2022,
    duration: "1h 21min",
    genres: ["Drama"],
    director: "Marcelo Gomes",
  },
  {
    id: "5",
    title: "Carvão",
    description:
      "Uma família que vive da produção de carvão vegetal no interior do Brasil aceita esconder um homem desconhecido em troca de dinheiro, mas a situação sai do controle.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 6.9,
    year: 2022,
    duration: "1h 47min",
    genres: ["Drama", "Suspense"],
    director: "Carolina Markowicz",
  },
  {
    id: "6",
    title: "A Viagem de Pedro",
    description:
      "Em 1831, D. Pedro I abdica do trono brasileiro e retorna a Portugal para lutar pelo trono de sua filha. Durante a travessia, ele reflete sobre sua vida e legado.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.0,
    year: 2022,
    duration: "1h 38min",
    genres: ["Drama Histórico"],
    director: "Laís Bodanzky",
  },
  {
    id: "7",
    title: "Deserto Particular",
    description:
      "Daniel, um policial suspenso, viaja do Paraná até a Bahia em busca de Sara, uma mulher por quem se apaixonou virtualmente.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.8,
    year: 2021,
    duration: "2h",
    genres: ["Drama", "Romance"],
    director: "Aly Muritiba",
  },
]

export const brazilianClassics: Movie[] = [
  {
    id: "8",
    title: "Central do Brasil",
    description:
      "Dora, uma ex-professora que escreve cartas para analfabetos na estação Central do Brasil, ajuda um menino a encontrar seu pai após a morte de sua mãe.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.5,
    year: 1998,
    duration: "1h 53min",
    genres: ["Drama"],
    director: "Walter Salles",
  },
  {
    id: "9",
    title: "Cidade de Deus",
    description:
      "Buscapé é um jovem pobre, negro, crescido na Cidade de Deus, violenta favela carioca. Ele cresce em meio à violência e ao tráfico de drogas, mas consegue escapar graças ao seu talento como fotógrafo.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.9,
    year: 2002,
    duration: "2h 10min",
    genres: ["Crime", "Drama"],
    director: "Fernando Meirelles, Kátia Lund",
  },
  {
    id: "10",
    title: "O Auto da Compadecida",
    description:
      "As aventuras de João Grilo e Chicó, dois nordestinos pobres que vivem de golpes para sobreviver, mas que, pela intercessão da Nossa Senhora, recebem uma segunda chance.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.8,
    year: 2000,
    duration: "1h 44min",
    genres: ["Comédia", "Aventura"],
    director: "Guel Arraes",
  },
  {
    id: "11",
    title: "Deus e o Diabo na Terra do Sol",
    description:
      "No sertão brasileiro, um vaqueiro mata seu patrão e foge com a esposa, seguindo um beato fanático e depois um cangaceiro, em busca de uma vida melhor.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.4,
    year: 1964,
    duration: "2h",
    genres: ["Drama", "Faroeste"],
    director: "Glauber Rocha",
  },
  {
    id: "12",
    title: "Pixote: A Lei do Mais Fraco",
    description:
      "Um menino de rua de São Paulo é enviado a um reformatório juvenil, onde sofre abusos. Após fugir, ele tenta sobreviver nas ruas através do crime.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.2,
    year: 1981,
    duration: "2h 8min",
    genres: ["Crime", "Drama"],
    director: "Hector Babenco",
  },
]

export const documentaries: Movie[] = [
  {
    id: "13",
    title: "Democracia em Vertigem",
    description:
      "A cineasta Petra Costa testemunha a ascensão e queda de Dilma Rousseff e o subsequente impeachment que expôs as profundas divisões políticas no Brasil.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.3,
    year: 2019,
    duration: "2h 1min",
    genres: ["Documentário", "Política"],
    director: "Petra Costa",
  },
  {
    id: "14",
    title: "Estou Me Guardando Para Quando O Carnaval Chegar",
    description:
      "Um olhar sobre Toritama, a capital do jeans no agreste pernambucano, onde os trabalhadores são seus próprios patrões, mas só param de trabalhar durante o carnaval.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.1,
    year: 2019,
    duration: "1h 26min",
    genres: ["Documentário"],
    director: "Marcelo Gomes",
  },
  {
    id: "15",
    title: "Edifício Master",
    description:
      "O documentário retrata o cotidiano dos moradores de um prédio em Copacabana, no Rio de Janeiro, revelando histórias íntimas e diversas.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.0,
    year: 2002,
    duration: "1h 50min",
    genres: ["Documentário"],
    director: "Eduardo Coutinho",
  },
  {
    id: "16",
    title: "Cabra Marcado para Morrer",
    description:
      "O filme retoma, 17 anos depois, a história da filmagem interrompida pela ditadura militar sobre o líder camponês João Pedro Teixeira, assassinado em 1962.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.7,
    year: 1984,
    duration: "1h 59min",
    genres: ["Documentário"],
    director: "Eduardo Coutinho",
  },
]

export const awardWinning: Movie[] = [
  {
    id: "17",
    title: "Que Horas Ela Volta?",
    description:
      "Val é uma empregada doméstica que mora na casa de seus patrões em São Paulo. A chegada de sua filha Jéssica causa tensão na casa e faz com que ela questione seu papel.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.8,
    year: 2015,
    duration: "1h 52min",
    genres: ["Drama", "Comédia"],
    director: "Anna Muylaert",
  },
  {
    id: "18",
    title: "Aquarius",
    description:
      "Clara, uma crítica musical aposentada de 65 anos, é a última moradora de um prédio antigo e luta contra uma construtora que quer demolir o edifício.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.9,
    year: 2016,
    duration: "2h 26min",
    genres: ["Drama"],
    director: "Kleber Mendonça Filho",
  },
  {
    id: "19",
    title: "O Som ao Redor",
    description:
      "A vida em uma rua de classe média em Recife muda com a chegada de uma milícia que oferece proteção aos moradores.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 7.7,
    year: 2012,
    duration: "2h 11min",
    genres: ["Drama", "Suspense"],
    director: "Kleber Mendonça Filho",
  },
  {
    id: "20",
    title: "Tropa de Elite",
    description:
      "O capitão Nascimento, do BOPE (Batalhão de Operações Policiais Especiais), precisa encontrar um substituto para seu posto enquanto tenta sobreviver às pressões de sua vida pessoal e profissional.",
    posterUrl: "/placeholder.svg?height=450&width=300",
    backdropUrl: "/placeholder.svg?height=1080&width=1920",
    rating: 8.6,
    year: 2007,
    duration: "1h 55min",
    genres: ["Ação", "Crime", "Drama"],
    director: "José Padilha",
  },
]
