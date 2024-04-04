package org.fsp.utilitaires_ihms;

import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * - NOM_VERROU_DONNEES : constante contenant le nom du fichier verrou.
 * - utilisateursEnregistres : liste des utilisateurs enregistrés dans la BD neo4j associée à l'application.
 * - initialiserDonnees() : Utilise ConnexionBD pour obtenir une session de connexion à la BD Neo4 puis recherche tous les noeuds de label Utilisateur et les mémorise dans l'attribut utilisateurEnregistres. Lève AccesBDException si la session ne peut pas être ouverte ou si la recherche dans la BD échoue.
 * - connecterUtilisateur(id,pass) : Recherche dans la liste des utilisateurs un utilisateur possédant l'identifiant et le mot de passe spécifés et renvoie le noeud correspondant s'il en existe. Lève IdentificationException sinon.
 * - verrouilerDonners() : pose un verrou en créant un fichier de nom NOM_VERROU_DONNEES.
 * - verrouDonneesFerme() : renvoie true si le verrou est posé, false sinon.
 * - deverrouillerDonnees() : lève le verrou en supprimant le fichier NOM_VERROU_DONNEES.
 */
public class UtilitaireGestionNotes  {
    private static String NOM_VERROU_DONNEES;

    private static List<org.neo4j.driver.types.Node> utilisateursEnregistres = new ArrayList<>();

    /**
     * Utilise ConnexionBD pour obtenir une session de connexion à la BD Neo4
     * puis recherche tous les noeuds de label Utilisateur
     * et les mémorise dans l'attribut utilisateurEnregistres.
     * Lève AccesBDException si la session ne peut pas être ouverte
     * ou si la recherche dans la BD échoue.
     * @throws AccesBDException si la session ne peut pas être ouverte
     */
    public static void initialiserDonnees() throws AccesBDException {

        Session session = ConnexionBD.getSession();
        Query query = new Query("MATCH (n:Utilisateur) RETURN n");
        Result result = session.run(query);

        for (Record record : result.list()) {
            utilisateursEnregistres.add(record.get("n").asNode());
        }

    }

    /**
     * Recherche dans la liste des utilisateurs
     * un utilisateur possédant l'identifiant et le mot de passe spécifés
     * et renvoie le noeud correspondant s'il en existe.
     * Lève IdentificationException sinon.
     * @param identifiant L'identifiant de l'utilisateur
     * @param motDePasse Le mot de passe de l'utilisateur
     * @return Le noeud correspondant s'il en existe
     * @throws IdentificationException  sinon
     */
    public static Node connecterUtilisateur(String identifiant, String motDePasse) throws IdentificationException {
        org.neo4j.driver.types.Node resultat = null;

        for (org.neo4j.driver.types.Node node : utilisateursEnregistres) {
            if (node.get("identifiant").asString().equals(identifiant) &&
                    node.get("motDePasse").asString().equals(motDePasse)) {
                resultat = node;
            }
        }

        if (resultat == null) {
            throw new IdentificationException();
        }

        return resultat;
    }

    public static void verrouillerDonnees() throws IOException {
        NOM_VERROU_DONNEES = "verrou";
        File file = new File(NOM_VERROU_DONNEES);
        file.createNewFile();
    }

    public static boolean verrouDonneesFerme() throws IOException {
        File file = new File(NOM_VERROU_DONNEES);
        return file.exists();
    }

    public static void deVerrouillerDonnees() throws IOException {
        File file = new File(NOM_VERROU_DONNEES);
        file.delete();
    }

}
