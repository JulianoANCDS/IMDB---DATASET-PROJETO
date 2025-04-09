package ed;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CatalogoFilmes catalogo = new CatalogoFilmes();
        System.out.println("Carregando dados do IMDb...");
        catalogo.carregarDados("title.basics.tsv", "title.ratings.tsv");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== SISTEMA DE FILMES IMDb ===");
            System.out.println("1. Buscar filme por título");
            System.out.println("2. Ver filmes melhor avaliados");
            System.out.println("3. Comparar algoritmos de busca");
            System.out.println("4. Comparar algoritmos de ordenação");
            System.out.println("5. Comparar estruturas de dados");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    buscarFilme(catalogo, scanner);
                    break;
                case 2:
                    recomendarFilmes(catalogo, scanner);
                    break;
                case 3:
                    compararAlgoritmosBusca(catalogo, scanner);
                    break;
                case 4:
                    catalogo.compararAlgoritmosOrdenacao();
                    break;
                case 5:
                    catalogo.compararEstruturas();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void buscarFilme(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nDigite o título do filme: ");
        String titulo = scanner.nextLine();
        
        System.out.println("\nSelecione o método de busca:");
        System.out.println("1. Pesquisa Sequencial");
        System.out.println("2. Pesquisa Binária");
        System.out.println("3. Árvore Binária de Busca (BST)");
        System.out.println("4. Árvore AVL");
        System.out.print("Opção: ");
        int metodo = scanner.nextInt();
        scanner.nextLine();
        
        long inicio = System.currentTimeMillis();
        List<Filme> filmes = null;
        int comparacoes = 0;
        
        switch (metodo) {
            case 1: 
                filmes = catalogo.buscarPesquisaSequencial(titulo);
                comparacoes = Busca.getComparacoes();
                break;
            case 2: 
                filmes = catalogo.buscarPesquisaBinaria(titulo);
                comparacoes = Busca.getComparacoes();
                break;
            case 3: 
                filmes = catalogo.buscarBST(titulo);
                comparacoes = catalogo.getArvoreTitulos().getComparacoes();
                break;
            case 4: 
                filmes = catalogo.buscarAVL(titulo);
                comparacoes = catalogo.getArvoreAVL().getComparacoes();
                break;
            default: 
                System.out.println("Método inválido!");
                return;
        }
        
        long tempo = System.currentTimeMillis() - inicio;
        
        System.out.println("\nResultados para '" + titulo + "':");
        System.out.println("Tempo de busca: " + tempo + "ms");
        System.out.println("Comparações realizadas: " + comparacoes);
        
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado.");
        } else {
            System.out.println("\nFilmes encontrados (" + filmes.size() + "):");
            for (int i = 0; i < filmes.size(); i++) {
                System.out.println((i + 1) + ". " + filmes.get(i));
            }
        }
    }

    private static void recomendarFilmes(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nQuantos filmes você quer ver? ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        
        List<Filme> recomendados = catalogo.recomendarTopFilmes(quantidade);
        
        System.out.println("\nTop " + quantidade + " filmes com pelo menos 20.000 votos:");
        for (int i = 0; i < recomendados.size(); i++) {
            System.out.println((i + 1) + ". " + recomendados.get(i));
        }
    }

    private static void compararAlgoritmosBusca(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nDigite um título para comparar algoritmos de busca: ");
        String titulo = scanner.nextLine();
        
        catalogo.compararAlgoritmosBusca(titulo);
    }
}