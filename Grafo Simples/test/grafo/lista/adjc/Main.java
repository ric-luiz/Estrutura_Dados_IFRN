/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.lista.adjc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author ric_l
 */
public class Main {

    static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));

    public static Grafo.Aresta lerAresta() throws Exception {
        System.out.println("Aresta:");
        System.out.print("  V1:");
        int v1 = Integer.parseInt(in.readLine());
        System.out.print("  V2:");
        int v2 = Integer.parseInt(in.readLine());
        System.out.print("  Peso:");
        int peso = Integer.parseInt(in.readLine());
        return new Grafo.Aresta(v1, v2, peso);
    }

    public static void main(String[] args) throws Exception {
        System.out.print("No. vertices:");
        int nVertices = Integer.parseInt(in.readLine());
        System.out.print("No. arestas:");
        int nArestas = Integer.parseInt(in.readLine());
        Grafo grafo = new Grafo(nVertices);
        for (int i = 0; i < nArestas; i++) {
            Grafo.Aresta a = lerAresta();
            //ja que nao é direcionado fazemos a ida e volta
            grafo.insereAresta(a.v1(), a.v2(), a.peso()); 
            grafo.insereAresta(a.v2(), a.v1(), a.peso());
        }
           
        grafo.imprime();
        in.readLine();
        
        System.out.println("tem caminho euleriano? "+grafo.temCaminhoEuleriano());        
        in.readLine();
        
        System.out.print("Deseja verificar o grau de um vertice? 0 = nao, 1 sim");
        int opc = Integer.parseInt(in.readLine());        
        if(opc != 0){
            System.out.print("Informe o numero do vertice: ");
            opc = Integer.parseInt(in.readLine());
            System.out.println("O Grau do vertice eh: "+grafo.grauVertice(opc));
        }                
        in.readLine();
        
        System.out.print("Deseja inserir um novo vertice? 0 = nao, 1 sim");
        int opcao = Integer.parseInt(in.readLine());
        if(opcao != 0){
            grafo.inserirVertice();
            grafo.imprime();
            in.readLine();
        }                    
        
        System.out.print("Deseja remove um vertice? 0 = nao, 1 sim");
        int opcao2 = Integer.parseInt(in.readLine());
        if(opcao2 != 0){
            System.out.print("Digite o numero do vertice: ");
            int vertice = Integer.parseInt(in.readLine());
            grafo.removerVertice(vertice);
            grafo.imprime();
            in.readLine();  
        }                                
        
        System.out.print("Lista adjacentes de: ");
        int v1 = Integer.parseInt(in.readLine());
        if (!grafo.listaAdjVazia(v1)) {
            Grafo.Aresta adj = grafo.primeiroListaAdj(v1);
            while (adj != null) {
                System.out.println("  " + adj.v2() + " (" + adj.peso() + ")");
                adj = grafo.proxAdj(v1);
            }
            System.out.println();
            in.readLine();
        }

        System.out.println("Retira aresta: ");
        Grafo.Aresta a = lerAresta();
        if (grafo.existeAresta(a.v1(), a.v2(), a.peso())) {
            grafo.retiraAresta(a.v1(), a.v2(),a.peso());
            grafo.retiraAresta(a.v2(), a.v1(),a.peso());
        } else {
            System.out.println("Aresta nao existe");
        }
        
        grafo.imprime();
        in.readLine();
        
        System.out.print("Existe aresta: ");
        a = lerAresta();
        if (grafo.existeAresta(a.v1(), a.v2(), a.peso())) {
            System.out.println("  Sim");
        } else {
            System.out.println("  Nao");
        }
    }
}
