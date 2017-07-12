/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.lista.adjc;

/**
 *
 * @author ric_l e Robinson
 */
public class Grafo {

    public static class Aresta {

        private int v1, v2, peso;

        public Aresta(int v1, int v2, int peso) {
            this.v1 = v1;
            this.v2 = v2;
            this.peso = peso;
        }

        public int peso() {
            return this.peso;
        }

        public int v1() {
            return this.v1;
        }

        public int v2() {
            return this.v2;
        }
    }

    private static class Celula {

        int vertice, peso;

        public Celula(int v, int p) {
            this.vertice = v;
            this.peso = p;
        }

        public boolean equals(Object obj) {
            Celula item = (Celula) obj;
            return (this.vertice == item.vertice && this.peso == item.peso);//se for o mesmo vertice e o mesmo peso
        }
    }
    
    private Lista adj[];
    private int numVertices;
    private int numeroArestas;

    public Grafo(int numVertices) {
        this.adj = new Lista[numVertices];;
        this.numVertices = numVertices;
        for (int i = 0; i < this.numVertices; i++) {
            this.adj[i] = new Lista();
        }
    }

    public void inserirVertice(){
        this.numVertices++;        
        Lista Novaadj[] = new Lista[numVertices];
        Novaadj[numVertices-1] = new Lista(); // o novo elemento vertice recebe a lista
        
        for(int i=0;i<this.adj.length;i++){
            Novaadj[i] = this.adj[i];
        }
        
       this.adj =  Novaadj; // inserimos a noca matriz com um vertice a mais
    }
    
    public void removerVertice(int v) throws Exception{
        
        Aresta a = primeiroListaAdj(v);        
        while(a != null){ // removemos as arestas de todo mundo relacionado a v            
            retiraAresta(a.v2, v, a.peso);
            a = proxAdj(v);
        }        
        
        reorganizarIndeces(v); //vai reorganizar os index
        
        this.adj[v] = null;
        this.numVertices--;
        
        Lista Novaadj[] = new Lista[numVertices];
        
        int j = 0;
        for(int i=0;i<this.adj.length;i++){                                    
            if(this.adj[i] == null){                
                continue;
            }             
                
            Novaadj[j] = this.adj[i];                        
            j++;
        }
        
        this.adj =  Novaadj;
    }
    
    public void insereAresta(int v1, int v2, int peso) {        
        if(this.adj[v1] == null)
            System.out.println("Este elemento não existe nessa matriz ou foi removido");            
        else{
            numeroArestas++;
            Celula item = new Celula(v2, peso);
            this.adj[v1].insere(item);
        }        
    }

    public boolean existeAresta(int v1, int v2, int peso) {
        if(this.adj[v1] == null){
            System.out.println("Este elemento não existe nessa matriz ou foi removido");  
            return false;
        }else{
            Celula item = new Celula(v2, peso);
            return (this.adj[v1].pesquisa(item) != null);
        }                
    }

    public boolean listaAdjVazia(int v) {        
        if(this.adj[v] == null){
            System.out.println("Este elemento não existe nessa matriz ou foi removido");  
            return false;
        }
        
        return this.adj[v].vazia();
    }

    public Aresta primeiroListaAdj(int v) {
        
        if(this.adj[v] == null){
            System.out.println("Este elemento não existe nessa matriz ou foi removido");  
            return null;
        }
        
        //retorna a primeira aresta do vertice v
        Celula item = (Celula) this.adj[v].primeiro();
        return item != null ? new Aresta(v, item.vertice, item.peso) : null;
    }

    public Aresta proxAdj(int v) {
        
        if(this.adj[v] == null){
            System.out.println("Este elemento não existe nessa matriz ou foi removido");  
            return null;
        }
        
        //retorna a proxima aresta que v participa
        Celula item = (Celula) this.adj[v].proximo();
        return item != null ? new Aresta(v, item.vertice, item.peso) : null;
    }

    public Aresta retiraAresta(int v1, int v2, int peso) throws Exception {
        
        if(this.adj[v1] == null){
            System.out.println("Este elemento não existe nessa matriz ou foi removido");  
            return null;
        }
        
        numeroArestas--;
        Celula chave = new Celula(v2, peso);
        Celula item = (Celula) this.adj[v1].retira(chave);
        return item != null ? new Aresta(v1, v2, item.peso) : null;
    }

    private void reorganizarIndeces(int v){
        for (int i = v; i < this.numVertices; i++) {
            Celula item = (Celula) this.adj[i].primeiro();
            
            while (item != null) {
                item.vertice--;
                item = (Celula) this.adj[i].proximo();
            }
            
        }
    }
    
    public int grauVertice(int v){
    
        Aresta a = primeiroListaAdj(v);
        int grau = 1;
        
        if(a != null){
            while(proxAdj(v) != null){
                grau++;
            }
            
            return grau; //retorna o total de arestas incidentes
        }
        
        return 0; //caso nao exista arestas
    }
    
    public boolean temCaminhoEuleriano(){
        int soma=0,f=0;
        
        while(soma <= 2 && f < this.numVertices){            
            
            if(grauVertice(f) % 2 == 1){ //impar
                soma++;
            }
            
            f++;
        }
        
        if(soma > 2)
            return false;
        else
            return true;
        
    }
    
    public void imprime() {
        System.out.println("------------------------------------------------------");
        for (int i = 0; i < this.numVertices; i++) {
            
            if(this.adj[i] == null)
                continue;
            
            System.out.println("Vertice " + i + ":");
            Celula item = (Celula) this.adj[i].primeiro();
            while (item != null) {
                System.out.println("  " + item.vertice + " (" + item.peso + ")");
                item = (Celula) this.adj[i].proximo();
            }
        }
        System.out.println("------------------------------------------------------");
    }

    public int numVertices() {
        return this.numVertices;
    }
    
}
