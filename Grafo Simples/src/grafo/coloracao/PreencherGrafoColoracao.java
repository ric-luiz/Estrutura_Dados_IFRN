package grafo.coloracao;

import grafo.coloracao.Grafo.Aresta;
import grafo.coloracao.Grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ric_l
 */
public class PreencherGrafoColoracao {
    int linha;
    int coluna;
    char[][] matriz;
    Grafo g;
    ArrayList<Cor> cores;
    
    public PreencherGrafoColoracao() throws IOException {
//        preencherMatriz();
//        cores = new ArrayList<>();
//        populaCores(); 
//        colorir_vertices();  
    }
    
    public void construirGrafo(){
        cores = new ArrayList<>();
        populaCores(); 
        colorir_vertices(); 
    }
    
    private void populaCores(){
        cores.clear();
        cores.add(new Cor("#ff0000","#ffffff"));
        cores.add(new Cor("#ff00ff","#000000"));
        cores.add(new Cor("#0000ff","#ffffff"));    
        cores.add(new Cor("#00ffff","#000000"));    
//        cores.add(new Cor("#00ff00","#000000"));  
//        cores.add(new Cor("#ffff00","#000000"));
//        cores.add(new Cor("#7f0000","#ffffff"));  
//        cores.add(new Cor("#7f007f","#ffffff"));
//        cores.add(new Cor("#00007f","#ffffff"));
//        cores.add(new Cor("#007f7f","#ffffff"));        
//        cores.add(new Cor("#007f00","#ffffff"));
//        cores.add(new Cor("#827f00","#000000"));
//        cores.add(new Cor("#000000","#ffffff"));
//        cores.add(new Cor("#333333","#ffffff"));
//        cores.add(new Cor("#4c4c4c","#ffffff"));
//        cores.add(new Cor("#b2b2b2","#000000"));  
//        cores.add(new Cor("#ffffff","#000000"));        
    }
    
    private void colorir_vertices(){
        Cor c = this.cores.get(0);        
        g.vertices.get(0).cor = c;
        
        int contadorCor = 0;
        
        for(int n = 1; n < g.vertices.size(); n++){ //percorrer a lista de todos os vertices
            boolean ok = true;
            
            for(int k=0; k <= contadorCor; k++){ //percorrer todas as cores ja colocadas
                
                for(Aresta e : g.vertices.get(n).adj){ //para cada vertice adjacente aquele vertice em questão
                    Vertice adj = e.destino;
                    if (adj.getColorido()) {
                        if (adj.cor.equals(this.cores.get(k))) {
                            //ja tem um vertice com essa cor
                            ok = false;
                            break;
                        }
                    }                    
                }
                
                if(ok){
                    //achamos uma cor que nenhum vértice possui
                    g.vertices.get(n).cor = this.cores.get(k);
                    break;
                }
                
            }
            
            if(!ok){
                //todas as cores atuais são usadas pelos vértices adjacentes
                contadorCor += 1;
                
                 if (contadorCor >= this.cores.size()) {
                    for (int ncor = 0; ncor < this.cores.size(); ncor++) {
                        boolean podeUsarCor = true;
                        
                        for (Aresta e : g.vertices.get(n).adj) {
                            if (e.destino.getColorido()) {
                                if (e.destino.cor.equals(this.cores.get(ncor))) {
                                    podeUsarCor = false;
                                    break;
                                }
                            }
                        }

                        if (podeUsarCor) {                            
                            contadorCor = ncor;
                            break;
                        }
                    }
                }
                 
                 
                g.vertices.get(n).cor = this.cores.get(contadorCor);
            }
            
        }
        
    }
    
    //Coloca cores pros vertices
    private void Colore_Vertice(Vertice vk) {
        Cor c;
        c = null;
        
        if(!vk.getColorido()){ //nao tem cor
            for(Cor cc : cores) {
                boolean podeUsarCor;
                podeUsarCor = true;
                for(Aresta e: vk.adj) {
                    if(e.destino.getColorido()){
                        if(e.destino.cor.equals(cc)){
                            podeUsarCor = false;
                            break;
                        }
                    }
                }
                
                if(podeUsarCor){
                    c = cc;                    
                    break;
                }
            }
            
            vk.cor = c;
            for(Aresta a : vk.adj) {
                Colore_Vertice(a.destino);
            }
        }
    }
    
    public void preencherMatriz() throws IOException {
        g = new Grafo();
                
        String arquivo = lerArquivo();        
        this.matriz = new char [this.linha][this.coluna]; // so serve para sabermos o tamanho da matriz no arquivo
        int contarCaracteres = 0;
        
        for(int i = 0; i < this.linha; i++){
            for(int j = 0; j < this.coluna; j++){                
                this.matriz[i][j] = arquivo.charAt(contarCaracteres);
                g.addVertice(new Cor(),this.matriz[i][j]);
                contarCaracteres++;
            }
        }                 
        
        contarCaracteres = 0;        
        for(int i = 0; i < this.linha; i++){
            for(int j = 0; j < this.coluna; j++){               
                preencherArestasLabirinto(contarCaracteres,i,j);                
                contarCaracteres++;
            }
        }                            
    }
    
    public void preencherMtatrizManual(){        
        g = new Grafo();
        
        g.addVertice(new Cor(),'A');
        g.addVertice(new Cor(),'B');
        g.addVertice(new Cor(),'C');
        g.addVertice(new Cor(),'D');
        g.addVertice(new Cor(),'E');        
        g.addVertice(new Cor(),'F');        
        
        //A - B
        g.addAresta(g.vertices.get(0), g.vertices.get(1), 1);
        g.addAresta(g.vertices.get(1), g.vertices.get(0), 1);                
        
        //A - C
        g.addAresta(g.vertices.get(2), g.vertices.get(0), 1);
        g.addAresta(g.vertices.get(0), g.vertices.get(2), 1);
        
        //B - E
        g.addAresta(g.vertices.get(1), g.vertices.get(4), 1);
        g.addAresta(g.vertices.get(4), g.vertices.get(1), 1);
        
        //C - D
        g.addAresta(g.vertices.get(2), g.vertices.get(3), 1);
        g.addAresta(g.vertices.get(3), g.vertices.get(2), 1);
        
        //D - F
        g.addAresta(g.vertices.get(3), g.vertices.get(5), 1);
        g.addAresta(g.vertices.get(5), g.vertices.get(3), 1);
        
        //E - F
        g.addAresta(g.vertices.get(4), g.vertices.get(5), 1);
        g.addAresta(g.vertices.get(5), g.vertices.get(4), 1);
        
        //A - E
        g.addAresta(g.vertices.get(0), g.vertices.get(4), 1);
        g.addAresta(g.vertices.get(4), g.vertices.get(0), 1);
        
        //A - D
        g.addAresta(g.vertices.get(0), g.vertices.get(3), 1);
        g.addAresta(g.vertices.get(3), g.vertices.get(0), 1);
        
        //C - B
        g.addAresta(g.vertices.get(2), g.vertices.get(1), 1);
        g.addAresta(g.vertices.get(1), g.vertices.get(2), 1);
        
        //C - F
        g.addAresta(g.vertices.get(2), g.vertices.get(5), 1);
        g.addAresta(g.vertices.get(5), g.vertices.get(2), 1);                
        
        //D - E
        g.addAresta(g.vertices.get(3), g.vertices.get(4), 1);
        g.addAresta(g.vertices.get(4), g.vertices.get(3), 1);
        
        //B - F
        g.addAresta(g.vertices.get(1), g.vertices.get(5), 1);
        g.addAresta(g.vertices.get(5), g.vertices.get(1), 1);
    }
    
    private void preencherArestasLabirinto(int contador, int i, int j){
        
        //Se tem alguem na esquerda
        if( j-1 >= 0){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-1), 1 );            
        }
        
        //se tem alguem na direita
        if(j+1 < this.coluna){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador+1), 1 );
        }
    
        //se tem alguem em cima
        if(i-1 >= 0){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), 1);
        }
        
        //se tem alguem em baixo
        if(i+1 < this.linha){
            g.addAresta(g.vertices.get(contador), g.vertices.get(contador+this.coluna), 1 );
        }
//        
//        //superior esquerdo
//        if(i-1 >= 0 && j-1 >= 0){
//            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), 1);
//        }
//        
//        //superior direito
//        if(i-1 >= 0 && j+1 < this.coluna){
//            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), 1);
//        }
//        
//        //inferior esquerdo
//        if(i+1 < this.linha && j-1 >= 0){
//            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), 1);
//        }
//        
////        //Inferior direito
////        if(i+1 < this.linha && j+1 < this.coluna){ 
////            g.addAresta(g.vertices.get(contador), g.vertices.get(contador-this.coluna), 1);
////        }
    }
    
    private String lerArquivo() throws FileNotFoundException, IOException{
        File f = new File("./mapaColoracao.txt");       
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
    
    public void printgrafo(boolean colorido,String nome, String tipo) {
        String saida;
        if(colorido){
            saida = nome+"colorido.gif";
        } else {
            saida = nome+"original.gif";
        }
        
        // instancia o objeto para print do graf
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        
        for(Vertice v : g.vertices) {
            if(colorido) {
                gv.addln(v.nome + " [ color=\"" + v.cor.getCor() + "\", fontcolor=\""+v.cor.getCorFonte()+"\", style=filled];" );
            } else {
                gv.addln(v.nome + ";");
            }
        }
        
        for (Aresta aa : g.arestas) {
            gv.addln(aa.origem.nome + " -> " + aa.destino.nome);
        }
        
        gv.addln(gv.end_graph());
        
        System.out.println(gv.getDotSource());
        
        File out = new File(saida);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "gif", tipo), out);
        
    }
}
