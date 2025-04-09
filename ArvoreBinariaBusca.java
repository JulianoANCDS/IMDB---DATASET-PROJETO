package ed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArvoreBinariaBusca {
    private class No {
        Filme filme;
        No esquerda, direita;

        No(Filme filme) {
            this.filme = filme;
        }
    }

    private No raiz;
    private int comparacoes;

    public void inserir(Filme filme) {
        raiz = inserir(raiz, filme);
    }

    private No inserir(No no, Filme filme) {
        if (no == null) {
            return new No(filme);
        }

        int cmp = filme.getTitulo().compareToIgnoreCase(no.filme.getTitulo());
        if (cmp < 0) {
            no.esquerda = inserir(no.esquerda, filme);
        } else if (cmp > 0) {
            no.direita = inserir(no.direita, filme);
        } else {
            // Se o título já existe, insere à direita
            no.direita = inserir(no.direita, filme);
        }

        return no;
    }

    public List<Filme> buscar(String titulo) {
        comparacoes = 0;
        List<Filme> resultados = new ArrayList<>();
        buscar(raiz, titulo, resultados);
        
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

    private void buscar(No no, String titulo, List<Filme> resultados) {
        if (no == null) {
            return;
        }

        comparacoes++;
        int cmp = titulo.compareToIgnoreCase(no.filme.getTitulo());
        if (cmp == 0) {
            resultados.add(no.filme);
            // Continua procurando à direita por possíveis duplicatas
            buscar(no.direita, titulo, resultados);
        } else if (cmp < 0) {
            buscar(no.esquerda, titulo, resultados);
        } else {
            buscar(no.direita, titulo, resultados);
        }
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }
}