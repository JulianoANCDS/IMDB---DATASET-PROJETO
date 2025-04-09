package ed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArvoreAVL {
    private class No {
        Filme filme;
        No esquerda, direita;
        int altura;

        No(Filme filme) {
            this.filme = filme;
            this.altura = 1;
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
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balance = getBalance(no);

        if (balance > 1 && filme.getTitulo().compareToIgnoreCase(no.esquerda.filme.getTitulo()) < 0) {
            return rotacionarDireita(no);
        }

        if (balance < -1 && filme.getTitulo().compareToIgnoreCase(no.direita.filme.getTitulo()) > 0) {
            return rotacionarEsquerda(no);
        }

        if (balance > 1 && filme.getTitulo().compareToIgnoreCase(no.esquerda.filme.getTitulo()) > 0) {
            no.esquerda = rotacionarEsquerda(no.esquerda);
            return rotacionarDireita(no);
        }

        if (balance < -1 && filme.getTitulo().compareToIgnoreCase(no.direita.filme.getTitulo()) < 0) {
            no.direita = rotacionarDireita(no.direita);
            return rotacionarEsquerda(no);
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

    private int altura(No no) {
        return no == null ? 0 : no.altura;
    }

    private int getBalance(No no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private No rotacionarDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacionarEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public int altura() {
        return altura(raiz);
    }
}