/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.coloracao;

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
    
    public class Vertice {
        public String nome;
        public Cor cor;
        public List<Aresta> adj;

        Vertice(Cor cor, char nome) {
            this.nome = ""+nome;
            this.cor = cor;
            this.adj = new ArrayList<Aresta>();
        }
        
        public boolean getColorido(){
            return cor.getCor() != null;
        }

        public void addAdj(Aresta e) {
            adj.add(e);
        }
        
        public void remAdj(Aresta e){
            this.adj.remove(e);
        }
    }

    public class Aresta {
        public Vertice origem;
        public Vertice destino;
        public Object peso;

        Aresta(Vertice origem, Vertice destino, Object peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public boolean equals(Object o) {
            Aresta a = (Aresta)o;                        
            if(this.origem == a.origem &&
               this.destino == a.destino &&
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

    public List<Vertice> vertices;
    public List<Aresta> arestas;

    public Grafo() {
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
    }

    public Vertice addVertice(Cor cor,char nome) {
        Vertice v = new Vertice(cor,nome);
        vertices.add(v);
        return v;
    }
    
    //Estou adicionando como se fosse um grafo nao direcionado
    public Aresta addAresta(Vertice origem, Vertice destino, Object peso) {
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
    
}
