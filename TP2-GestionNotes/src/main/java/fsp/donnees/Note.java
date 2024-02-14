package fsp.donnees;

public class Note {    
    private String idMatiere;
    private float valeur;    
    public Note(String idMatiere, float valeur) {
        this.valeur = valeur;
        this.idMatiere = idMatiere;
    }

    public String getIdMatiere() {
        return idMatiere;
    }

    public float getValeur() {
        return valeur;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }
    
}
