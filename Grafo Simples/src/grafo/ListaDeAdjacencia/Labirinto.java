/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.ListaDeAdjacencia;

import grafo.ListaDeAdjacencia.Grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ric_l
 */
public class Labirinto {
    int linha;
    int coluna;
    char[][] matriz;
    Grafo g;    
    
    public Labirinto() throws IOException {
        preencherLabirinto();
    }
    
    public ArrayList<Integer> encontrarIndexVerticeNoLabirinto(char nome){
        ArrayList<Integer> posicaoVerticeEncontrado = new ArrayList<>();
        
        for(int i=0; i < g.vertices.size(); i++){
            if(g.vertices.get(i).nome == nome){
                posicaoVerticeEncontrado.add(i);
            }
        }
        
        return posicaoVerticeEncontrado;
    }
    
    public List<Integer> menorCustoDosCaminhos(List<List<Integer>> caminhosEncontrados){
        List<Integer> menorCaminho = null;
        Integer custoTotal = Integer.MAX_VALUE;
        Integer calculadorCusto = 0;
        
        for(List<Integer> caminho : caminhosEncontrados){            
            calculadorCusto = 0;           
            
            for(int i=0; i<caminho.size()-1; i++){
                
                if(g.vertices.get(caminho.get(i)).nome == '1'){
                    calculadorCusto = -1; // informo que um dos caminhos passa pela parede e é um caminho invalido
                    break;
                }
                
                calculadorCusto += (int)g.verificarLigacaoEntreVeretices(g.vertices.get(caminho.get(i)), g.vertices.get(caminho.get(i+1)));                
            }
              
            if(calculadorCusto < 0){ //caso o caminho avaliado seja um caminho invalido
                continue; //passamos para o proximo caminho da lista
            }
            
            //Verificamos se o caminho sendo averiguado é menor do que o ja existente
            if(calculadorCusto < custoTotal){                
                menorCaminho = caminho;
                custoTotal = calculadorCusto;            
            }
                        
        }
        
        return menorCaminho;
    }
    
    public ArrayList<Integer> listaPosicoesIndexVertices(List<Vertice> vertices){
        ArrayList<Integer> inteiros = new ArrayList<>();
        
        for(Vertice v: vertices){
            inteiros.add(g.vertices.indexOf(v));
        }
        
        return inteiros;
    }
    
    private void preencherLabirinto() throws IOException{
        g = new Grafo();
                
        String arquivo = lerArquivo();        
        this.matriz = new char [this.linha][this.coluna];
        int contarCaracteres = 0;
        
        for(int i = 0; i < this.linha; i++){
            for(int j = 0; j < this.coluna; j++){
                this.matriz[i][j] = arquivo.charAt(contarCaracteres);
                g.addVertice(this.matriz[i][j]);
                contarCaracteres++;
            }
        }                 
        
//        for(Grafo.Vertice v : g.vertices){
//            System.out.print(v.nome+" ");
//        }
        
        contarCaracteres = 0;        
        for(int i = 0; i < this.linha; i++){
            for(int j = 0; j < this.coluna; j++){               
                preencherArestasLabirinto(contarCaracteres,i,j);                
                contarCaracteres++;
            }
        }
        
        System.out.println("");        
    }
    
    private void preencherArestasLabirinto(int contador, int i, int j){
        
//        System.out.print(g.vertices.get(contador).nome);
        
        //Se tem alguem na esquerda
        if( j-1 >= 0){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-1), verificarPeso(g.vertices.get(contador).nome) );            
        }
        
        //se tem alguem na direita
        if(j+1 < this.coluna){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador+1), verificarPeso(g.vertices.get(contador).nome) );
        }
    
        //se tem alguem em cima
        if(i-1 >= 0){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), verificarPeso(g.vertices.get(contador).nome) );
        }
        
        //se tem alguem em baixo
        if(i+1 < this.linha){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador+this.coluna), verificarPeso(g.vertices.get(contador).nome) );
        }
    }
    
    private int verificarPeso(char posicao){
        
//        System.out.print(posicao+" ");
        if(posicao == '1'){
            return 100;
        }
        
        if(posicao == '0' || posicao == '2' || posicao == '3'){
            return 1;
        }
        
        System.out.println("Elemento nao identificado. Nao é possivel atrivuir valores");
        return 0;
    }
    
    private String lerArquivo() throws FileNotFoundException, IOException{
        File f = new File("./mapa2.txt");       
        BufferedReader br = new BufferedReader(new FileReader(f));
        String linhaLeitura;
        String resultado = "";
        
        int linha=0;
        int coluna=0;
        
        while(br.ready()){            
            linhaLeitura = br.readLine();
            resultado += linhaLeitura;
            
            linhaLeitura = linhaLeitura.replaceAll(" ", "");
            coluna = linhaLeitura.length();                                    
            linha++;
        }        
        br.close();
        
        this.linha = linha;
        this.coluna = coluna;
                        
        return resultado.replaceAll(" ", "");                        
    }
    
}
