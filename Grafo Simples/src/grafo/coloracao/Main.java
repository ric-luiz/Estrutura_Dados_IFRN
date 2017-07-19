package grafo.coloracao;

import java.io.IOException;

/**
 *
 * @author ric_l
 */
public class Main {

    public static void main(String[] args) throws IOException {
        grafo1();
        grafo2();
    }

    public static void grafo1() throws IOException {
        PreencherGrafoColoracao l = new PreencherGrafoColoracao();
        l.preencherMatriz();
        l.construirGrafo();
        
        l.printgrafo(true, "grafo1","neato");
//
//        //imprimir matriz
//        for (int i = 0; i < l.linha; i++) {
//            for (int j = 0; j < l.coluna; j++) {
//                System.out.print(l.matriz[i][j] + " ");
//            }
//            System.out.println(" ");
//        }
//
//        System.out.println("");
//
//        int contador = 0;
//        for (int i = 0; i < l.linha; i++) {
//            for (int j = 0; j < l.coluna; j++) {
//                System.out.print(l.g.vertices.get(contador).cor.getCor() + " ");
//                contador++;
//            }
//            System.out.println(" ");
//        }
//
//        //Imprimir total de arestas de cada vertice
//        contador = 0;
//        for (int i = 0; i < l.linha; i++) {
//            for (int j = 0; j < l.coluna; j++) {
//                System.out.print(l.g.vertices.get(contador).adj.size() + " ");
//                contador++;
//            }
//            System.out.println(" ");
//        }
//
//        System.out.println("");        
    }

    public static void grafo2() throws IOException {
        PreencherGrafoColoracao l = new PreencherGrafoColoracao();
        l.preencherMtatrizManual();
        l.construirGrafo();
        
        l.printgrafo(true, "grafo2","circo");
    }

}
