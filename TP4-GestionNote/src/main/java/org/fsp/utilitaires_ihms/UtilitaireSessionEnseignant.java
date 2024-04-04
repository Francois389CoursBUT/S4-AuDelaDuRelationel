package org.fsp.utilitaires_ihms;

import java.util.List;

/**
 * - intitulesMatieresEnseignees(idEnseignant) : Recherche dans la BD Neo4j associée à l'application l'ensemble des matières enseignées par l'enseignant specifié et renvoie leurs intitulés. Lève AccesBDException si problème d'accès à la BD.
 * - identifiantsEtudiantsInscrits(intitule) : Recherche dans la BD les étudiants inscrits dans la matière spécifiée et renvoie leurs identifiants. Lève AccesBDException si problème d'accès à la BD.
 * - note(identifiantEtudiant, intituleMatiere) : Recherche la note de l'éudiant spécifié dans la matière spécifiée et renvoie la note si elle existe, ou -1 sinon. Lève AccesBDException si problème d'accès à la BD. 
 * - modifierNote(id,intitule,nouvelleNote) : Remplace la note de l'étudiant spécifié dans la matière specifiée par nouvelleNote. Ne vérifie pas si nouvelleValeur est valide (>=0 et <=20). Lève AccesBDException si problème d'accès à la BD.
 */
public class UtilitaireSessionEnseignant {
    public static List<String> intitulesMatieresEnseignees(String idEnseignant) throws AccesBDException {
        return null;
    }

    public static List<String> identifiantsEtudiantsInscrits (String intitule) throws AccesBDException {
        return null;
    }

    public static float note(String identifiantEtudiant, String intituleMatiere) throws AccesBDException {
        return 0;
    }

    public static void modifierNote(String identifiantEtudiant, String intituleMatiere, float nouvelleNote) throws AccesBDException {
    }

}
