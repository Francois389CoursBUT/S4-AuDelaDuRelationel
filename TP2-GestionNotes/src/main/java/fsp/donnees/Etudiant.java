package fsp.donnees;

import java.util.ArrayList;

public class Etudiant extends Utilisateur  {
        
    private ArrayList<Note> notes = new ArrayList();
    
    /**
     * Recherche et renvoie la note de l'étudiant dans la matière passée en paramètre.
     * Renvoie -1 si l'étudiant n'a pas de note dans cette matière ou n'a aucune note. 
    */
    public float note(String idMatiere) {
        for(Note note: notes) {
          if (note.getIdMatiere().equals(idMatiere)) 
              return note.getValeur(); // Cas étudiant avec une note dans la matière
        }
        return -1; // Cas étudiant sans note dans la matière ou aucune note
    }
  
    /**
     * Modifie la note de l'étudiant dans la matière indiquée
     * en paramètre avec la valeur indiquée en paramètre.
     * Ne fait rien si la nouvelle valeur n'est pas comprise en 0 et 20 
     * ou si l'étudiant n'a pas déjà une note dans la matière.
    */
    public void modifierNote(String idMatiere, float nouvelleNote) {
        if (nouvelleNote>=0 && nouvelleNote <=20) {
            for(Note note : notes) {
                if (note.getIdMatiere().equals(idMatiere)) {
                        note.setValeur(nouvelleNote);
                        break;
                }
            }
        }
    }
    
   /**
     * Ajoute la note de l'étudiant dans la matière spécifiée avec la valeur spécifiée.
     * Ne fait rien si la valeur n'est pas comprise en 0 et 20.
    */
    public void ajouterNote(String idMatiere, float valeur) {
        if(valeur>=0 && valeur <=20 
            && this.note(idMatiere) == -1) { // Pas de note dans la matière
            notes.add(new Note(idMatiere,valeur ));    
        }
    }
    
    /**
     * Supprime la note de l'étudiant dans la matière spécifiée.
     * Ne fait rien si l'étudiant n'a pas de note dans cette matière ou s'il
     * n'a aucune note.
     */
    public void supprimerNote(String idMatiere) {
        if(notes != null) 
            for(Note note: notes) 
                if (note.getIdMatiere().equals(idMatiere)) {
                    notes.remove(note);
                    break;
                }
    }
    
    /**
     * Renvoie l'ensemble des notes de l'étudiant dans les matières suivies sous la
     * forme d'une chaîne de caractères. 
     * Forme de la chaîne renvoyée : id de la matière atière, suivi de la note si elle
     * attribuée ou d'une châine vide sinon, suivie d'un retour à la ligne sauf pour la
     * dernière note.
    */
    public String notesToString() { 
        String result ="";
        for(Note note : notes) {
            if (note.getValeur() != -1) result += note.getIdMatiere()+ " : "
                    + note.getValeur() + "\n";
            else result += note.getIdMatiere()+ " : \n";
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", notes=" + notesToString() +
                '}';
    }
}
