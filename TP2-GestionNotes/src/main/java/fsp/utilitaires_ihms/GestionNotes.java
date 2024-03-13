package fsp.utilitaires_ihms;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fsp.donnees.Enseignant;
import fsp.donnees.Etudiant;
import fsp.donnees.Matiere;
import fsp.donnees.Utilisateur;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class GestionNotes {

    private static List<Utilisateur> utilisateurs = new ArrayList();
    private static ArrayList<Matiere> matieres = new ArrayList();

    /**
     * Effectue les traitements suivants :
     * 1. Utilise ConnexionBD pour obtenir une session de connexion à la base de données.
     * 2. Recherche tous les documents représentant les enseignants avec les matières qui leur sont
     * associés, les traduit en document Json puis en objets instances de la classe Enseignant avant
     * de les mémoriser dans la liste des utilisateurEnregistres.
     * 3. Recherche tous les documents représentant les étudiants avec les notes qui leur sont
     * associés, les traduit en document Json ensuite en objets instances de la classe Etudiant
     * avant de les mémoriser dans la liste des utilisateurEnregistres.
     * Lève AccesBDException si la session ne peut pas être ouverte ou si la recherche dans la BD
     * échoue.
     */
    public static void chargerDonnees() throws Exception {
        // TODO
        // On récupère la base de données
        MongoDatabase mongoDb = ConnexionBD.getMongoBd();

        // On récupère la collection des enseignants
        MongoCollection<Document> tableEnseignant = mongoDb.getCollection("enseignants");
        Gson gson = new Gson();
        for (Document enseignantDoc : tableEnseignant.find()) {
            Enseignant enseignantGenere = gson.fromJson(enseignantDoc.toJson(), Enseignant.class);
            utilisateurs.add(enseignantGenere);
        }

        // On récupère la collection des étudiants
        MongoCollection<Document> tableEtudiant = mongoDb.getCollection("etudiants");
        for (Document etudiantDoc : tableEtudiant.find()) {
            Etudiant etudiantGenere = gson.fromJson(etudiantDoc.toJson(), Etudiant.class);
            utilisateurs.add(etudiantGenere);
        }

        // On récupère la collection des matières
        MongoCollection<Document> tableMatiere = mongoDb.getCollection("matieres");
        for (Document matiereDoc : tableMatiere.find()) {
            Matiere matiereGenere = gson.fromJson(matiereDoc.toJson(), Matiere.class);
            matieres.add(matiereGenere);
        }

        System.out.println("Données chargées");
        System.out.println(utilisateurs);

    }

    /**
     * Recherche dans la liste utilisateursEnregistres un utilisateur possédant l'identifiant et
     * le mot de passe spécifés et renvoie l'utilisateur correspondant s'il en existe.
     * Lève IdentificationException sinon.
     */
    public static Utilisateur connecter(String idutilisateur, String motDePasse)
            throws IdentificationException {
        for (Utilisateur u : utilisateurs) {
            if (u.getId().equals(idutilisateur)
                    & u.getMotDePasse().equals(motDePasse))
                return u;
        }
        throw new IdentificationException();
    }

    /**
     * Recherche et renvoie les matières enseignées par un enseigan en exploitant la liste des ids
     * des matières enseignées et la liste des objets instances de la classe Matiere mémorisés dans
     * l'attribut matieres.
     */
    public static ArrayList<Matiere> matieresEnseignees(Enseignant enseignant) {
        ArrayList<Matiere> result = new ArrayList();
        ArrayList<String> idsMatieresEnseignees = enseignant.getIdsMatieresEnseignees();
        for (Matiere matiere : matieres) {
            if (idsMatieresEnseignees.contains(matiere.getId()))
                result.add(matiere);
        }
        return result;
    }

    /**
     * Rechercher les objets Etudiants inscrits dans une matière en exploitant les ids des étudiants
     * inscrits dans la matière et les objets instances de la classe Etudiant mémorisés dans
     * l'attribut utilisateurs.
     */
    public static ArrayList<Etudiant> etudiantsInscrits(Matiere matiere) {
        ArrayList<Etudiant> result = new ArrayList();
        ArrayList<String> idsEtudiantsInscrits
                = matiere.getIdsEtudiantsInscrits();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant
                    && idsEtudiantsInscrits.contains(utilisateur.getId()))
                result.add((Etudiant) utilisateur);
        }
        return result;
    }

    /**
     * Ajoute une note à un étudiant dans une matière où il n'a aucune note. La note est enregistrée
     * dans la base de donnée et dans l'objet Etudiant  correspondant à l'id spécifié.
     * Ne fais rien si valeur non comprise en 0 et 20 ou si l'étudiant a déjà une note dans la
     * matière. Lève une Exception sans rien ajouter si problème d'accès à la base de données.
     */
    public static void ajouterNote(Etudiant etudiant, String idMatiere,
                                   float valeur) throws Exception {
        if (0 <= valeur && valeur <= 20) {
            MongoCollection<Document> tableEtudiant = ConnexionBD.getMongoBd().getCollection("etudiants");
            Bson filtre = eq("id", etudiant.getId());

            // AJouter une note avec cette requete
            // Documen de mise à jour {$set:{notes:[{"matiere":"Mathématiques", "valeur":15}]}})
            Document note = new Document().append("matiere", idMatiere).append("valeur", valeur);
            Document update = new Document().append("$push", new Document().append("notes", note));

            tableEtudiant.updateOne(filtre, update);

            etudiant.ajouterNote(idMatiere, valeur);
        }
    }

    /**
     * Modifie la note d'un étudiant dans une matière où il a déjà une note. Remplace dans la base
     * de données et dans l'objet Etudiant correspondant à l'id spécifié la note dans la matière
     * spécifiée par nouvelleValeur. Ne fait rien si la nouvelle valeur n'est pas comprise en 0 et 20
     * ou si l'étudiant n'a pas déjà une note dans la matière.
     * Lève une Exception sans rien modifier si problème d'accès à la base de données.
     */
    public static void modifierNote(Etudiant etudiant, String idMatiere,
                                    float nouvelleNote) throws Exception {
        if (0 <= nouvelleNote && nouvelleNote <= 20) {
            MongoCollection<Document> tableEtudiant = ConnexionBD.getMongoBd().getCollection("etudiants");
            Document etudiantDoc = tableEtudiant.find(new Document("id", etudiant.getId())).first();
            if (etudiantDoc != null) {
                List<Document> notes = (List<Document>) etudiantDoc.get("notes");
                if (notes != null) {
                    for (Document note : notes) {
                        if (note.getString("idMatiere").equals(idMatiere)) {
                            note.put("valeur", nouvelleNote);
                            tableEtudiant.updateOne(new Document("id", etudiant.getId()), new Document("$set", new Document("notes", notes)));
                        }
                    }
                }
            }
        }
    }

    /**
     * Supprime la note d'un étudiant dans une matière. Supprime la note dans la base de données et
     * dans l'objet Etudiant correspondant à l'id spécifié. Ne fait rien si l'étudiant n'a pas de
     * note dans cette matière ou s'il n'a aucune note.
     * Lève une Exception sans rien supprimer si problème d'accès à la base de données.
     */
    public static void supprimerNote(Etudiant etudiant, String idMatiere)
            throws Exception {
        MongoCollection<Document> tableEtudiant = ConnexionBD.getMongoBd().getCollection("etudiants");
        Document etudiantDoc = tableEtudiant.find(new Document("id", etudiant.getId())).first();
        if (etudiantDoc != null) {
            List<Document> notes = (List<Document>) etudiantDoc.get("notes");
            if (notes != null) {
                for (Document note : notes) {
                    if (note.getString("idMatiere").equals(idMatiere)) {
                        notes.remove(note);
                        tableEtudiant.updateOne(new Document("id", etudiant.getId()), new Document("$set", new Document("notes", notes)));
                    }
                }
            }
        }
    }
}
