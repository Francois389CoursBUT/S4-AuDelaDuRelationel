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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilisateur{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", _id='").append(_id).append('\'');
        sb.append(", motDePasse='").append(motDePasse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
