package fsp.donnees;

public abstract class Utilisateur  {
    
    private String nom;   
    private String prenom;
    private String _id;
    private String motDePasse;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getId() {
        return _id;
    }

    public String getMotDePasse() {
        return motDePasse;
    }    
}
