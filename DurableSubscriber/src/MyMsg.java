import java.io.Serializable;

public class MyMsg implements Serializable {
    private static final long serialVersionUID = -7670265615867065220L;
    private String texte;
    
    public String getTexte() {
        return texte;
    }
    
    public void setTexte(String texte) {
        this.texte = texte;
    } 
}