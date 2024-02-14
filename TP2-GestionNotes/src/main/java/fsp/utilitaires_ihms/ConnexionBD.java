package fsp.utilitaires_ihms;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Rôle de la classe : Elle permet d'établir une connexion avec la base de donnée associée à
 * l'application. C'est une adaptation du pattern Singleton qui établit une connexion avec le serveur
 * MongoDb et crée un objet MongoDatabase qu'on peut obtenir par getMongoBd().
 * - IP_SERVEUR : adresse IP du serveur MongoDb
 * - PORT_SSERVEUR_MONGODB : port d'écoute du serveur MongoDb.
 * - BD_APPLICATION : nom de la base de données MongoDB associée à l'application.
 */
public class ConnexionBD {
    
    private final static String IP_SERVEUR_MONGODB = "localhost";
    private final static int PORT_SERVEUR_MONGODB = 27017 ;
    private final static String BD_APPLICATION ="BdGestionNotes";
    private static MongoDatabase mongoDb;
    
    /**
     * Renvoie la base de donnée mémorisée dans l'atribut mongoDb si ce dernier est <> null. 
     * Sinon, tente d'établir une connexion au serveur MongoDB en utilisant IP_SERVEUR_MONGODB
     * et PORT_SERVEUR_MONGODB et de récupérer la base de donnée BD_APPLICATION.
     * Si aucune erreur de connexion ne se produit, mémorise la base de données dans l'attribut
     * mongoDb et la renvoie comme résultat. Lève une ConnexionBDException sinon.
     */
    static MongoDatabase getMongoBd()throws ConnexionBDException {
        // TODO
        if (mongoDb == null) {
            try {
                MongoClient client = new MongoClient(IP_SERVEUR_MONGODB, PORT_SERVEUR_MONGODB);
                mongoDb = client.getDatabase(BD_APPLICATION);
            } catch (Exception e) {
                throw new ConnexionBDException("Impossible de se connecter à la base de données.");
            }
        }
        return mongoDb;
    }
    
}
