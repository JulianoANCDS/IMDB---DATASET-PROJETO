package ed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Busca {
    private static int comparacoes;

    public static List<Filme> pesquisaSequencial(List<Filme> filmes, String titulo) {
        comparacoes = 0;
        List<Filme> resultados = new ArrayList<>();
        
        for (Filme filme : filmes) {
            comparacoes++;
            if (filme.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(filme);
            }
        }
        
        // Ordena resultados por número de votos (mais importante primeiro)
        if (resultados.size() > 1) {
            Collections.sort(resultados, new Comparator<Filme>() {
                @Override
                public int compare(Filme f1, Filme f2) {
                    return Integer.compare(f2.getVotos(), f1.getVotos());
                }
            });
        }
        
        return resultados;
    }

    public static List<Filme> pesquisaBinaria(List<Filme> filmes, String titulo) {
        comparacoes = 0;
        int esquerda = 0;
        int direita = filmes.size() - 1;
        List<Filme> resultados = new ArrayList<>();

        while (esquerda <= direita) {
            comparacoes++;
            int meio = esquerda + (direita - esquerda) / 2;
            int comparacao = titulo.compareToIgnoreCase(filmes.get(meio).getTitulo());

            if (comparacao == 0) {
                // Encontrou um filme com título exato, agora verifica duplicatas
                resultados.add(filmes.get(meio));
                
                // Verifica à esquerda
                int i = meio - 1;
                while (i >= 0 && filmes.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                    resultados.add(filmes.get(i));
                    i--;
                }
                
                // Verifica à direita
                i = meio + 1;
                while (i < filmes.size() && filmes.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                    resultados.add(filmes.get(i));
                    i++;
                }
                
                break;
            } else if (comparacao < 0) {
                direita = meio - 1;
            } else {
                esquerda = meio + 1;
            }
        }
        
        // Ordena resultados por número de votos (mais importante primeiro)
        if (resultados.size() > 1) {
            Collections.sort(resultados, new Comparator<Filme>() {
                @Override
                public int compare(Filme f1, Filme f2) {
                    return Integer.compare(f2.getVotos(), f1.getVotos());
                }
            });
        }
        
        return resultados;
    }

    public static int getComparacoes() {
        return comparacoes;
    }
}