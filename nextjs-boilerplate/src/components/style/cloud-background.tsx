import React, { useRef, useEffect } from 'react';

// Função para interpolar entre duas cores em formato #RRGGBB ou rgb(...).
// Mix de corA e corB no fator t (0 a 1).
function interpolateColor(corA: string, corB: string, t: number): string {
  // Remove possíveis formatos 'rgb(...)' e converte para #RRGGBB se precisar,
  // mas aqui vou assumir sempre #RRGGBB para simplificar.
  // Converte hex em r, g, b
  const aR = parseInt(corA.slice(1, 3), 16);
  const aG = parseInt(corA.slice(3, 5), 16);
  const aB = parseInt(corA.slice(5, 7), 16);

  const bR = parseInt(corB.slice(1, 3), 16);
  const bG = parseInt(corB.slice(3, 5), 16);
  const bB = parseInt(corB.slice(5, 7), 16);

  // Interpola
  const r = Math.round(aR + (bR - aR) * t);
  const g = Math.round(aG + (bG - aG) * t);
  const b = Math.round(aB + (bB - aB) * t);

  // Volta para string hex
  return `rgb(${r}, ${g}, ${b})`;
}

// ------------- PARTE DE PERLIN NOISE -------------
// Vamos criar um Perlin Noise simples em 2D para gerar a textura de nuvens.
// Existem bibliotecas prontas (por ex. 'perlin-noise'), mas aqui vamos de base.

// Função auxiliar: gradiente aleatório em 2D
function randomGradient(): [number, number] {
  const angle = Math.random() * 2 * Math.PI;
  return [Math.cos(angle), Math.sin(angle)];
}

// Função "dot product" entre dois vetores 2D
function dot(ax: number, ay: number, bx: number, by: number) {
  return ax * bx + ay * by;
}

// Função "fade" usada no Perlin (uma polinomial 6t^5 - 15t^4 + 10t^3)
function fade(t: number): number {
  return t * t * t * (t * (t * 6 - 15) + 10);
}

// Classe que gera e armazena gradientes para Perlin Noise
class Perlin {
  private grad: [number, number][][];
  private width: number;
  private height: number;

  constructor(width: number, height: number) {
    this.width = width;
    this.height = height;
    this.grad = [];

    // Gera gradientes aleatórios para cada ponto da grid
    for (let y = 0; y <= height; y++) {
      this.grad[y] = [];
      for (let x = 0; x <= width; x++) {
        this.grad[y][x] = randomGradient();
      }
    }
  }

  // Retorna valor de Perlin Noise em (x, y),
  // onde x e y podem ser float, mas as gradientes estão numa grid de int.
  public get(x: number, y: number): number {
    // Pega as coordenadas inteiras
    const x0 = Math.floor(x);
    const x1 = x0 + 1;
    const y0 = Math.floor(y);
    const y1 = y0 + 1;

    // Distâncias dentro do quadrado
    const dx0 = x - x0;
    const dy0 = y - y0;
    const dx1 = dx0 - 1;
    const dy1 = dy0 - 1;

    // Gradientes correspondentes
    const g00 = this.grad[y0]?.[x0] || [0, 0];
    const g01 = this.grad[y1]?.[x0] || [0, 0];
    const g10 = this.grad[y0]?.[x1] || [0, 0];
    const g11 = this.grad[y1]?.[x1] || [0, 0];

    // Produtos escalares
    const n00 = dot(g00[0], g00[1], dx0, dy0);
    const n01 = dot(g01[0], g01[1], dx0, dy1);
    const n10 = dot(g10[0], g10[1], dx1, dy0);
    const n11 = dot(g11[0], g11[1], dx1, dy1);

    // Aplica fade
    const u = fade(dx0);
    const v = fade(dy0);

    // Interpola os valores
    const nx0 = n00 + (n10 - n00) * u;
    const nx1 = n01 + (n11 - n01) * u;
    const n = nx0 + (nx1 - nx0) * v;

    return n;
  }
}

// Função que gera um array 2D [width x height] com valores 0..1 de Perlin Noise
// com múltiplas oitavas (para ter mais "detalhe").
function generatePerlinNoise2D(
  width: number,
  height: number,
  scale = 50,
  octaves = 4,
  persistence = 0.5,
  lacunarity = 2.0
): number[][] {
  // Cria array de ruído final
  const noiseData: number[][] = new Array(height).fill(null).map(() => new Array(width).fill(0));

  let amplitude = 1;
  let frequency = 1;
  let maxPossibleValue = 0; // para normalizar depois

  // Para cada oitava, criamos um Perlin gerando uma grid
  for (let o = 0; o < octaves; o++) {
    const p = new Perlin(Math.ceil((width * frequency) / scale), Math.ceil((height * frequency) / scale));

    for (let y = 0; y < height; y++) {
      for (let x = 0; x < width; x++) {
        const sampleX = (x * frequency) / scale;
        const sampleY = (y * frequency) / scale;
        noiseData[y][x] += p.get(sampleX, sampleY) * amplitude;
      }
    }

    maxPossibleValue += amplitude;
    amplitude *= persistence;
    frequency *= lacunarity;
  }

  // Normaliza para valores entre 0..1
  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      noiseData[y][x] = (noiseData[y][x] + maxPossibleValue) / (2 * maxPossibleValue);
      // +maxPossibleValue / 2 => shifting, pois Perlin pode gerar -1..+1
      // agora tudo deve estar entre 0..1
    }
  }

  return noiseData;
}

// --------------------------------------------------

const CloudBackground: React.FC = () => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // Ajusta tamanho do canvas para a tela inteira
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    // Gera o noise base
    // Ajuste "scale" e "octaves" para ficar mais ou menos detalhado,
    // e "persistence" para controlar a suavidade.
    const noiseData = generatePerlinNoise2D(
      canvas.width,
      canvas.height,
      200, // scale
      3,   // octaves
      0.5, // persistence
      2.5  // lacunarity
    );

    // Desenha de fato no canvas
    // Aqui, mapeamos valores do noise (0..1) para cores de um degradê claro (quase branco) até azul clarinho.
    // Ajuste conforme seu gosto.
    for (let y = 0; y < canvas.height; y++) {
      for (let x = 0; x < canvas.width; x++) {
        const t = noiseData[y][x];

        // Escolha de cores (de cor1 para cor2)
        // Você pode trocar as cores pelos tons exatos da sua imagem.
        const cor1 = '#ffffff'; // branco
        const cor2 = '#cfe2f3'; // azul clarinho, p.ex. #cfe2f3 ou #e0f2fe
        const color = interpolateColor(cor1, cor2, t);

        ctx.fillStyle = color;
        ctx.fillRect(x, y, 1, 1);
      }
    }

    // Se quiser um leve "blur" ou "soften", podemos desenhar outro layer semitransparente por cima,
    // ou aplicar mais transformações. Mas isso já dá um bom efeito de "nuvem" suave.
  }, []);

  return (
    <canvas
      ref={canvasRef}
      // Tailwind classes: posicionado atrás de tudo, full screen
      className="
        fixed top-0 left-0
        w-screen h-screen
        -z-10  // para ficar atrás do conteúdo principal
      "
    />
  );
};

export default CloudBackground;
