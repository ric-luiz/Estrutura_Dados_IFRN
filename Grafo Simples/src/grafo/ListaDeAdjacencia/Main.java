/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.ListaDeAdjacencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ric_l
 */
public class Main {

    static int linha;
    static int coluna;
    static char[][] matriz;

    public static void main(String args[]) throws FileNotFoundException, IOException, Exception {
        Labirinto l = new Labirinto();

        //imprimir matriz
        for (int i = 0; i < l.linha; i++) {
            for (int j = 0; j < l.coluna; j++) {
                System.out.print(l.matriz[i][j] + " ");
            }
            System.out.println(" ");
        }

        System.out.println("");

        //Imprimir matriz pesos
        int contador = 0;                    
        
//        A partir daqui é a logica para encontrar o menor caminho        
        ArrayList<List<Integer>> caminhoEncontradosEstrela = new ArrayList<>();
                
        //vai pegar todas as saidas e calcular os caminhos para elas
        for(int j = 0; j < l.encontrarIndexVerticeNoLabirinto('3').size(); j++){
            caminhoEncontradosEstrela.add(l.listaPosicoesIndexVertices(l.g.pathAEstrela(l.encontrarIndexVerticeNoLabirinto('2').get(0), l.encontrarIndexVerticeNoLabirinto('3').get(j), l.linha, l.coluna)));
        }                        
        
        List<Integer> caminhoMenorEstrela = l.menorCustoDosCaminhos(caminhoEncontradosEstrela);                
        if(caminhoMenorEstrela == null){
            throw new Exception("Não existe saida para o labirinto!");
        }        
        
        System.out.print("O menor caminho entros as rotas: ");
        for (int i = 0; i < caminhoMenorEstrela.size(); i++) {
            System.out.print(caminhoMenorEstrela.get(i) + " ");
        }
        
        System.out.println("");
        System.out.println("");
                
        //Imprimir matriz
        System.out.println("Algoritmo de busca A* ");
        System.out.println("");
        contador = 0;
        for (int i = 0; i < l.linha; i++) {
            for (int j = 0; j < l.coluna; j++) {
                
                if(estaNaLista(caminhoMenorEstrela,contador)){
                    System.out.print("x ");
                } else {
                    System.out.print(l.g.vertices.get(contador).nome + " ");
                }
                
                contador++;
            }
            System.out.println(" ");
        }

        System.out.println("");
        System.out.println("----------------------------------------------------");
        System.out.println("");
        
        System.out.println("Algoritmo Dijkstra normal");
        System.out.println("");
        //A partir daqui é a logica para encontrar o menor caminho        
        ArrayList<List<Integer>> caminhoEncontrados = new ArrayList<>();
                
        //vai pegar todas as saidas e calcular os caminhos para elas
        for(int j = 0; j < l.encontrarIndexVerticeNoLabirinto('3').size(); j++){
            caminhoEncontrados.add(l.g.pathDijkstra(l.encontrarIndexVerticeNoLabirinto('2').get(0), l.encontrarIndexVerticeNoLabirinto('3').get(j)));
        }                                
        
        List<Integer> caminhoMenor = l.menorCustoDosCaminhos(caminhoEncontrados);                
        if(caminhoMenor == null){
            throw new Exception("Não existe saida para o labirinto!");
        }
        
        System.out.print("O menor caminho entros as rotas: ");
        for (int i = 0; i < caminhoMenor.size(); i++) {
            System.out.print(caminhoMenor.get(i) + " ");
        }
        
        System.out.println("");
        System.out.println("");
        
        //Imprimir matriz
        contador = 0;
        for (int i = 0; i < l.linha; i++) {
            for (int j = 0; j < l.coluna; j++) {
                
                if(estaNaLista(caminhoMenor,contador)){
                    System.out.print("x ");
                } else {
                    System.out.print(l.g.vertices.get(contador).nome + " ");
                }
                
                contador++;
            }
            System.out.println(" ");
        }

        System.out.println("");

    }

    public static boolean estaNaLista(List<Integer> caminhos, int vertice) {
        for (Integer caminhoVertices : caminhos) {
            if (caminhoVertices == vertice) {
                return true;
            }
        }
        return false;
    }    

}
