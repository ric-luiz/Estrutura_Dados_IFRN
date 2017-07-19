package grafo.coloracao;

/**
 *
 * @author ric_l
 */
public class Cor {
    private String cor;
    private String corFonte;

    public Cor(String cor, String corFonte) {
        this.cor = cor;
        this.corFonte = corFonte;
    }

    public Cor() {
    }        
    
    public String getCor() {
        return cor;
    }

    public void setCor(String c) {
        cor = c;
    }

    public String getCorFonte() {
        return corFonte;
    }

    public void setCorFonte(String c) {
        corFonte = c;
    }
}
