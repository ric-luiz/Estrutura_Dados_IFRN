/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.ListaDeAdjacencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ric_l
 */
public class Grafo {
    
    public static final int INDEFINIDO = -1;
    
    public class Vertice {
        char nome;
        List<Aresta> adj;

        Vertice(char nome) {
            this.nome = nome;
            this.adj = new ArrayList<Aresta>();
        }

        void addAdj(Aresta e) {
            adj.add(e);
        }
        
        void remAdj(Aresta e){
            this.adj.remove(e);
        }
    }

    public class Aresta {
        Vertice origem;
        Vertice destino;
        Object peso;

        Aresta(Vertice origem, Vertice destino, Object peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public boolean equals(Object o) {
            Aresta a = (Aresta)o;                        
            if(this.origem.nome == a.origem.nome &&
               this.destino.nome == a.destino.nome &&
               this.peso.equals(a.peso)
               ){
                return true;
            }            
            return false;
        }

        public boolean verticesPertencemAEssaAresta(Vertice origem,Vertice destino){
            return this.origem.equals(origem) && this.destino.equals(destino);
        }
        
        public Vertice getOrigem() {
            return origem;
        }

        public void setOrigem(Vertice origem) {
            this.origem = origem;
        }

        public Vertice getDestino() {
            return destino;
        }

        public void setDestino(Vertice destino) {
            this.destino = destino;
        }

        public Object getPeso() {
            return peso;
        }

        public void setPeso(Object peso) {
            this.peso = peso;
        }
        
        
    }

    List<Vertice> vertices;
    List<Aresta> arestas;

    public Grafo() {
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
    }

    Vertice addVertice(char nome) {
        Vertice v = new Vertice(nome);
        vertices.add(v);
        return v;
    }
    
    //Estou adicionando como se fosse um grafo nao direcionado
    Aresta addAresta(Vertice origem, Vertice destino, Object peso) {
        //como o grafo é nao direcionado adicionamos 2 arestas, uma para ir e outra para voltar
        Aresta e = new Aresta(origem, destino,peso);
//        Aresta b = new Aresta(destino, origem,peso);
        
        //mesma coisa com vertices
        origem.addAdj(e);
//        destino.addAdj(b);
        
        arestas.add(e);
//        arestas.add(b);
        return e;
    }

    public String toString() {
        String r = "";
        for (Vertice u : vertices) {
            r += u.nome + " -> ";
            for (Aresta e : u.adj) {
                Vertice v = e.destino;
                r += v.nome + ", ";
            }
            r += "\n";
        }
        return r;
    }
    
    public int numeroVertices(){
        return this.vertices.size();
    }
    
    public int numeroArestas(){
        return this.arestas.size();
    }
    
    public int grauVertice(Vertice v){
        return v.adj.size();
    }
    
    public boolean existeAresta(Aresta a){
        for(int i=0; i < arestas.size(); i++){
            if(arestas.get(i).equals(a)){
                return true;
            }
        }
        
        return false;        
    }       
    
    public Aresta pesquisarAresta(Aresta a){
        for(int i=0; i < arestas.size(); i++){
            if(arestas.get(i).equals(a)){
                return arestas.get(i);
            }
        }
        
        return null;
    }
    
    public void imprimirComoMatriz(){
        for(int i=0; i < vertices.size();i++){
            for(int j=0; j < vertices.size();j++){                
                System.out.print(verificarLigacaoEntreVeretices(vertices.get(i),vertices.get(j))+" ");                
            }
            System.out.println("");
        }
    }
    
    public Object verificarLigacaoEntreVeretices(Vertice v1,Vertice v2){                        
        for(Aresta e : v1.adj){
            if(e.destino.equals(v2)){
                return e.peso;
            }
        }
        
        return -100;
    }
    
    //estou removendo como um grafo nao direcionado
    public void removerAresta(Aresta a){
        //pesquiso 2 vezes para retirar a aresta de ida e volta (já que é um grafo nao orientado)        
        Aresta a1 = pesquisarAresta(new Aresta(a.origem, a.destino, a.peso));
        Aresta a2 = pesquisarAresta(new Aresta(a.destino, a.origem, a.peso));
                
        if(a1 == null){
            System.out.println("Aresta não existe");
            return;
        }
        
        a1.origem.remAdj(a1);
        a2.origem.remAdj(a2);        
        
        this.arestas.remove(a1);
        this.arestas.remove(a2);                
    }
    
    public void removerVertice(Vertice v){
        System.out.println(v.adj.size());
        for(Aresta a: v.adj){
            a.destino.remAdj(new Aresta(a.destino,a.origem,a.peso));            
        }
        this.vertices.remove(v);
    }
    
    public boolean temCaminhoEuleriano(){
        int soma=0,f=0;
        
        while(soma <= 2 && f < this.numeroVertices()){
            if(this.grauVertice(this.vertices.get(f)) % 2 == 1){
                soma++;
            }
            f++;
        }
        
        if(soma > 2)
            return false;
        else
            return true;
    }
    
    /*############################## Dijkstra ################################################################*/
    
    //este metodo retorna uma lista com a posição dos vertices para chegar no destino a partir da origem
    public List<Integer> pathDijkstra(int origemVertice, int destinoVertice){
        
        //Arrays para armazenar os custos e antecessores 
        int custo[] = new int[this.vertices.size()];
        int anterior[] = new int[this.vertices.size()];
        Set<Integer> naoVisitado = new HashSet<>();
        
        //o primeiro no, o node inicial, tem o custo 0 por padrão
        custo[origemVertice] = 0;
        
        //Inicia os valores para começar a busca
        for(int v=0; v < this.vertices.size(); v++){
            if(v != origemVertice){ //o vertice inicial ja definimos como sendo custo 0
                custo[v] = Integer.MAX_VALUE; //é como se fosse infito (pois ainda não inserimos os pesos)
            }
            
            anterior[v] = INDEFINIDO; //Deixamos a array para verificar vertices anteriores como indefinido
            naoVisitado.add(v); //adicionamos todos os vertices do grafo na lista de não visitados
        }
        
        //Fazendo a busca com o algoritmo de Dijkstra
        while(!naoVisitado.isEmpty()){
            int maisProximo = maisProximo(custo, naoVisitado);
            naoVisitado.remove(maisProximo);
            
            //Percorre a lista de todos os vizinhos daquele node para descobrir os custos
            for(Aresta arestasParaOutroVertices : this.vertices.get(maisProximo).adj){
                int custoTotal = custo[maisProximo] + (int)arestasParaOutroVertices.peso;
                int indexDestino = this.vertices.indexOf(arestasParaOutroVertices.destino);
                
                //Seta os custos para ir ate aquele node vizinho
                if(custoTotal < custo[indexDestino]){
                    custo[indexDestino] = custoTotal; 
                    anterior[indexDestino] = maisProximo;
                }
                
            }
            
            //encontrou o node de destino?
            if(maisProximo == destinoVertice){
                return fazerPathList(anterior, maisProximo);
            }
            
        }
        
        return Collections.emptyList(); //Caso nao encontre um caminho vai retornar uma lista vazia
    }        
    
    //Vai pegar o vertice mais proximo que ainda nao foi visitado
    private int maisProximo(int[] custos, Set<Integer> naoVisitado){
        double minDist = Integer.MAX_VALUE;
        int minIndex = 0;
        
        for(Integer i : naoVisitado){
            if(custos[i] < minDist){
                minDist = custos[i];
                minIndex = i;
            }
        }
        
        return minIndex; //Devolve o vertice que ainda não foi visitado e é o mais proximo 
    }        
    
    private List<Integer> fazerPathList(int[] anteriores, int u){
        List<Integer> path = new ArrayList<>();
        path.add(u);
        while(anteriores[u] != INDEFINIDO){
            path.add(anteriores[u]);
            u = anteriores[u];
        }
        
        Collections.reverse(path);
        return path;
    }
    
    /*#######################################################################################################*/
    
    public List<Vertice> pathAEstrela(int origemVertice, int destinoVertice, int linhas, int colunas){
        //Contem o caminho encontrado
        ArrayList<Vertice> pathEncontrado = new ArrayList<>();
        ArrayList<Aresta> arestasEncontradas = new ArrayList<>();
        ArrayList<Vertice> visitados = new ArrayList<>();
        
        Vertice origem = this.vertices.get(origemVertice);
        Vertice destino = this.vertices.get(destinoVertice);                        
        
        pathEncontrado.add(origem); //A origem ja faz parte do caminho       
        
        //Pega a posição do vertice de destino na matriz
        int finalX = destinoVertice % colunas;
        int finalY = destinoVertice / linhas;
        
        int f = Integer.MAX_VALUE;
        Vertice comMenorCusto = null;
        Aresta arestaMenorCusto = null;
                
        while(!origem.equals(destino) && visitados.size() != this.vertices.size()){            
            f = Integer.MAX_VALUE;
            
            for(Aresta e : origem.adj){                                                                
                
                if(e.destino.nome == '1' || visitados.indexOf(e.destino) >= 0){
                    continue;
                }
                
                int posicaoVertice = this.vertices.indexOf(e.destino);                                
                
                int x = posicaoVertice % colunas;
                int y = posicaoVertice / linhas;                
                int pesosAteChegarAqui = ((int)e.peso);                                
                
                for(Aresta aresta: arestasEncontradas){
                    pesosAteChegarAqui += (int)aresta.peso;                    
                }
                                                
                int h = pesosAteChegarAqui * (Math.abs(x - finalX) + Math.abs(y - finalY));
                int euristica = pesosAteChegarAqui + h;
                                
                
                if(euristica <= f){
                    comMenorCusto = e.destino;
                    arestaMenorCusto = e;                    
                    f = euristica;
                }
                
            }
            
            if(comMenorCusto == null){
                System.out.println("Com menorCusto esta null");
            }
            
            if(arestaMenorCusto == null){
                System.out.println("Com menorCusto, arestas esta null");
            }
            
            if(comMenorCusto != null)
                pathEncontrado.add(comMenorCusto);
            
            if(arestaMenorCusto != null)
                arestasEncontradas.add(arestaMenorCusto);
            
            visitados.add(origem);
            origem = comMenorCusto;            
        }
        
        return pathEncontrado; //Caso nao encontre um caminho vai retornar uma lista vazia
    }
    
    public static void main(String[] args) {
        Grafo g = new Grafo();
        Vertice s = g.addVertice('s');
        Vertice t = g.addVertice('t');
        Vertice y = g.addVertice('y');
                
        Aresta st = g.addAresta(s, t,10);
        Aresta sy = g.addAresta(s, y,15);
        Aresta ty = g.addAresta(t, y,5);
        
        System.out.println(g);                
        g.imprimirComoMatriz();
        
        System.out.println("");
        
//        g.removerAresta(ty);
//        g.removerAresta(st);
        //g.removerVertice(y);
//        System.out.println(g);        
        System.out.println(g.temCaminhoEuleriano());
        g.imprimirComoMatriz();
    }
}
